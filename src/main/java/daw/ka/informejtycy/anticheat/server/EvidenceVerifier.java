package daw.ka.informejtycy.anticheat.server;

import com.google.gson.Gson;
import daw.ka.informejtycy.anticheat.Attestation;
import daw.ka.informejtycy.anticheat.Envelope;
import daw.ka.informejtycy.anticheat.Evidence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class EvidenceVerifier {
    private static final Gson GSON = new Gson();

    private static final Set<String> SYNTHETIC_MODS = Set.of("minecraft", "java");

    private final String serverModVersion;
    private final String serverJarHash;

    public EvidenceVerifier(String serverModVersion, String serverJarHash) {
        this.serverModVersion = serverModVersion;
        this.serverJarHash = serverJarHash;
    }

    public Verdict verify(HandshakeSession session, Envelope envelope, long latencyMs) {
        List<String> reasons = new ArrayList<>();

        if (envelope.body == null || envelope.mac == null) {
            return new Verdict(Verdict.Status.TAMPERED, List.of("malformed-envelope"), null);
        }

        Evidence evidence;
        try {
            evidence = GSON.fromJson(envelope.body, Evidence.class);
        } catch (Exception e) {
            return new Verdict(Verdict.Status.TAMPERED, List.of("malformed-report"), null);
        }

        if (evidence == null) {
            return new Verdict(Verdict.Status.TAMPERED, List.of("empty-report"), null);
        }

        boolean authenticated = Attestation.constantTimeEquals(
                envelope.mac, Attestation.authenticate(session.expectedKey(), envelope.body));

        if (!authenticated) {
            if (evidence.modVersion != null && !evidence.modVersion.equals(serverModVersion)) {
                reasons.add("version-mismatch:" + evidence.modVersion + "!=" + serverModVersion);
            } else {
                reasons.add("attestation-failed");
            }
            reasons.addAll(explainAttestationFailure(session, evidence));
        }

        if (!session.nonceString().equals(envelope.nonce) || !session.nonceString().equals(evidence.nonce)) {
            reasons.add("nonce-mismatch");
            authenticated = false;
        }

        if (AnticheatConfig.DATA.maxLatencyMs > 0L && latencyMs > AnticheatConfig.DATA.maxLatencyMs) {
            reasons.add("slow-response:" + latencyMs + "ms");
        }

        if (AnticheatConfig.DATA.maxCollectMs > 0L && evidence.collectMs > AnticheatConfig.DATA.maxCollectMs) {
            reasons.add("slow-probe:" + evidence.collectMs + "ms");
        }

        reasons.addAll(checkTamperSignals(evidence));
        reasons.addAll(checkModListConsistency(evidence));
        reasons.addAll(checkMixinConfigs(evidence));
        reasons.addAll(checkJvmFlags(evidence));
        reasons.addAll(checkSelfJar(evidence));

        List<String> forbidden = checkPolicy(evidence);
        reasons.addAll(forbidden);

        Verdict.Status status;
        if (!forbidden.isEmpty()) {
            status = Verdict.Status.FORBIDDEN;
        } else if (!authenticated || hasInjection(evidence)) {
            status = Verdict.Status.TAMPERED;
        } else if (!reasons.isEmpty()) {
            status = Verdict.Status.SUSPICIOUS;
        } else {
            status = Verdict.Status.OK;
        }

        return new Verdict(status, reasons, evidence);
    }

    private List<String> explainAttestationFailure(HandshakeSession session, Evidence evidence) {
        List<String> reasons = new ArrayList<>();

        if (evidence.selfOrigin != null && !evidence.selfOrigin.contains(":jar")) {
            reasons.add("client-not-running-a-jar:" + evidence.selfOrigin);
        }

        Map<String, String> claimed = new HashMap<>();
        if (evidence.resourceDigests != null) {
            for (String entry : evidence.resourceDigests) {
                int split = entry.lastIndexOf('=');
                if (split > 0) {
                    claimed.put(entry.substring(0, split), entry.substring(split + 1));
                }
            }
        }

        List<String> measured = session.measuredResources();
        int differing = 0;
        String firstDifference = null;

        for (String resource : measured) {
            String mine;
            try {
                mine = Attestation.shortDigest(Attestation.readJarResource(resource));
            } catch (Exception e) {
                mine = "unreadable";
            }

            String theirs = claimed.getOrDefault(resource, "absent");
            if (!mine.equals(theirs)) {
                differing++;
                if (firstDifference == null) {
                    firstDifference = resource + " client=" + theirs + " server=" + mine;
                }
            }
        }

        reasons.add("measurement-differs:" + differing + "/" + measured.size());
        if (firstDifference != null) {
            reasons.add("first-difference:" + firstDifference);
        }
        return reasons;
    }

    private static boolean hasInjection(Evidence evidence) {
        return evidence.tamper != null && evidence.tamper.stream()
                .anyMatch(flag -> flag.startsWith("injected-") || flag.startsWith("mixin-target"));
    }

    private static List<String> checkTamperSignals(Evidence evidence) {
        List<String> reasons = new ArrayList<>();
        if (evidence.tamper != null) {
            for (String flag : evidence.tamper) {
                reasons.add("self-check:" + flag);
            }
        }
        return reasons;
    }

    private static List<String> checkModListConsistency(Evidence evidence) {
        List<String> reasons = new ArrayList<>();
        if (evidence.mods == null) {
            reasons.add("no-mod-list");
            return reasons;
        }

        Set<String> reportedIds = new HashSet<>();
        Set<String> reportedHashes = new HashSet<>();
        for (Evidence.ModEntry mod : evidence.mods) {
            if (mod.id != null) {
                reportedIds.add(mod.id);
            }
            if (mod.hash != null) {
                reportedHashes.add(mod.hash);
            }
        }

        if (evidence.classpathIds != null) {
            for (String id : evidence.classpathIds) {
                if (!reportedIds.contains(id) && !SYNTHETIC_MODS.contains(id)) {
                    reasons.add("hidden-from-loader:" + id);
                }
            }
        }

        if (evidence.modsDir != null) {
            for (String entry : evidence.modsDir) {
                int separator = entry.lastIndexOf(':');
                if (separator < 0) {
                    continue;
                }

                String name = entry.substring(0, separator);
                String hash = entry.substring(separator + 1);
                if (!"skipped".equals(hash) && !"null".equals(hash) && !reportedHashes.contains(hash)) {
                    reasons.add("unlisted-jar:" + name);
                }
            }
        }

        return reasons;
    }

    private static List<String> checkMixinConfigs(Evidence evidence) {
        List<String> reasons = new ArrayList<>();
        if (evidence.mixinConfigs == null) {
            return reasons;
        }

        Set<String> known = new HashSet<>(AnticheatConfig.DATA.trustedMixinOwners);
        if (evidence.mods != null) {
            for (Evidence.ModEntry mod : evidence.mods) {
                if (mod.id != null) {
                    known.add(mod.id);
                }
            }
        }

        for (String config : evidence.mixinConfigs) {
            String owner = config.contains(".") ? config.substring(0, config.indexOf('.')) : config;
            if (!known.contains(owner)) {
                reasons.add("orphan-mixin:" + config);
            }
        }
        return reasons;
    }

    private static List<String> checkJvmFlags(Evidence evidence) {
        List<String> reasons = new ArrayList<>();
        if (evidence.jvmFlags != null) {
            for (String flag : evidence.jvmFlags) {
                reasons.add("jvm-flag:" + flag);
            }
        }
        return reasons;
    }

    private List<String> checkSelfJar(Evidence evidence) {
        List<String> reasons = new ArrayList<>();
        if (serverJarHash != null && evidence.selfJar != null && !serverJarHash.equals(evidence.selfJar)) {
            reasons.add("different-mod-jar:client=" + shortHash(evidence.selfJar)
                    + " server=" + shortHash(serverJarHash));
        }
        return reasons;
    }

    private static String shortHash(String hash) {
        return hash == null ? "none" : hash.substring(0, Math.min(12, hash.length()));
    }

    private static List<String> checkPolicy(Evidence evidence) {
        List<String> reasons = new ArrayList<>();
        if (evidence.mods == null) {
            return reasons;
        }

        AnticheatConfig.ConfigData config = AnticheatConfig.DATA;
        for (Evidence.ModEntry mod : evidence.mods) {
            if (mod.id == null) {
                continue;
            }

            if (config.blockedIds.contains(mod.id)) {
                reasons.add("blocked-mod:" + mod.id);
                continue;
            }
            if (mod.hash != null && config.blockedHashes.contains(mod.hash)) {
                reasons.add("blocked-hash:" + mod.id);
                continue;
            }
            if (config.whitelistMode && !isAllowed(config, mod)) {
                reasons.add("not-whitelisted:" + mod.id);
            }
        }
        return reasons;
    }

    private static boolean isAllowed(AnticheatConfig.ConfigData config, Evidence.ModEntry mod) {
        if (config.allowedIds.contains(mod.id)) {
            return true;
        }

        String expected = config.allowedHashes.get(mod.id);
        return expected != null && expected.equals(mod.hash);
    }
}
