package daw.ka.informejtycy.anticheat.server;

import com.google.gson.Gson;
import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.anticheat.Attestation;
import daw.ka.informejtycy.anticheat.Challenge;
import daw.ka.informejtycy.anticheat.Envelope;
import daw.ka.informejtycy.anticheat.Evidence;
import daw.ka.informejtycy.anticheat.payload.HandshakePayload;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModOrigin;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class InformejtycyAnticheatServer implements DedicatedServerModInitializer {
    private static final long FIRST_RESEND_MS = 2000L;
    private static final long MAX_RESEND_MS = 10000L;
    private static final int NONCE_BYTES = 32;
    private static final Gson GSON = new Gson();
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final Map<UUID, HandshakeSession> sessions = new ConcurrentHashMap<>();
    private static final Map<UUID, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(
            runnable -> new Thread(runnable, "Informejtycy-Anticheat-Handshake"));

    private static List<String> measurableClasses = List.of();
    private static EvidenceVerifier verifier;
    private static ProbeFactory probeFactory;

    @Override
    public void onInitializeServer() {
        AnticheatConfig.load();

        registerHandshakePayload();
        prepareAttestation();
        registerConnectionEvents();
        registerHandshakeReceiver();
    }

    private static void registerHandshakePayload() {
        PayloadTypeRegistry.playS2C().register(HandshakePayload.ID, HandshakePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(HandshakePayload.ID, HandshakePayload.CODEC);
    }

    private static void prepareAttestation() {
        measurableClasses = discoverMeasurableClasses();
        probeFactory = ProbeFactory.createOrNull();

        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(Informejtycy.MOD_ID);
        String version = container.map(mod -> mod.getMetadata().getVersion().getFriendlyString()).orElse("unknown");
        String jarHash = null;
        if (container.isPresent() && container.get().getOrigin().getKind() == ModOrigin.Kind.PATH) {
            Path path = container.get().getOrigin().getPaths().getFirst();
            if (Files.isRegularFile(path)) {
                jarHash = Attestation.hashFile(path);
            }
        }

        verifier = new EvidenceVerifier(version, jarHash);
        Informejtycy.LOGGER.info("[Anticheat] Attesting against {} class files of informejtycy {} (jar {})",
                measurableClasses.size(), version, jarHash == null ? "not a file" : jarHash.substring(0, 12));

        if (probeFactory == null || measurableClasses.size() < Attestation.ALWAYS_MEASURED.size()) {
            Informejtycy.LOGGER.warn("[Anticheat] Probe generation is unavailable here; handshakes will be skipped");
        }
    }

    // Mixin classes are not byte stable on the client; measuring one fails every honest player.
    private static final List<String> EXCLUDED_PREFIXES = List.of(
            "daw/ka/informejtycy/mixin/",
            "daw/ka/informejtycy/client/mixin/");

    private static List<String> discoverMeasurableClasses() {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(Informejtycy.MOD_ID);
        if (container.isEmpty()) {
            return List.of();
        }

        List<String> classes = new ArrayList<>();
        for (Path root : container.get().getRootPaths()) {
            try (Stream<Path> stream = Files.walk(root)) {
                stream.filter(Files::isRegularFile)
                        .map(path -> root.relativize(path).toString().replace('\\', '/'))
                        .filter(name -> name.endsWith(".class") && !isExcluded(name))
                        .forEach(classes::add);
            } catch (Exception e) {
                Informejtycy.LOGGER.warn("[Anticheat] Could not walk {}: {}", root, e.toString());
            }
        }

        Collections.sort(classes);
        return List.copyOf(classes);
    }

    private static boolean isExcluded(String name) {
        for (String prefix : EXCLUDED_PREFIXES) {
            if (name.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private static void registerConnectionEvents() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> startHandshake(handler.player, server));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> stopHandshake(handler.player.getUuid()));
    }

    private static void registerHandshakeReceiver() {
        ServerPlayNetworking.registerGlobalReceiver(HandshakePayload.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            UUID playerId = player.getUuid();

            HandshakeSession session = sessions.get(playerId);
            if (session == null) {
                return;
            }

            Envelope envelope;
            try {
                envelope = GSON.fromJson(Attestation.decompress(payload.data()), Envelope.class);
            } catch (Exception e) {
                Informejtycy.LOGGER.warn("[Anticheat] Unreadable report from {}: {}",
                        player.getName().getString(), e.toString());
                return;
            }

            // An answer to a challenge we are no longer waiting on. Drop it; do not judge it.
            if (envelope == null || !session.nonceString().equals(envelope.nonce)) {
                return;
            }

            if (!sessions.remove(playerId, session)) {
                return;
            }

            cancelTask(playerId);
            long latencyMs = session.latencyMs();

            Verdict verdict = verifier.verify(session, envelope, latencyMs);
            report(player, verdict, latencyMs);
            enforce(player, verdict);
        });
    }

    private static void startHandshake(ServerPlayerEntity player, MinecraftServer server) {
        UUID playerId = player.getUuid();
        HandshakeSession session = createSession();
        if (session == null) {
            Informejtycy.LOGGER.warn("[Anticheat] Skipping handshake for {}: attestation is unavailable",
                    player.getName().getString());
            return;
        }

        sessions.put(playerId, session);
        scheduleSend(player, server, playerId, 0L, FIRST_RESEND_MS);
    }

    private static void scheduleSend(ServerPlayerEntity player, MinecraftServer server, UUID playerId,
                                     long delayMs, long nextGapMs) {
        ScheduledFuture<?> task = scheduler.schedule(() -> {
            HandshakeSession pending = sessions.get(playerId);
            if (pending == null) {
                return;
            }

            if (pending.latencyMs() >= AnticheatConfig.DATA.timeout) {
                stopHandshake(playerId);
                Verdict verdict = new Verdict(Verdict.Status.TIMEOUT, List.of("no-response"), null);
                server.execute(() -> {
                    report(player, verdict, pending.latencyMs());
                    enforce(player, verdict);
                });
                return;
            }

            server.execute(() -> {
                if (sessions.containsKey(playerId) && player.networkHandler != null) {
                    ServerPlayNetworking.send(player, new HandshakePayload(pending.challengePacket()));
                }
            });

            scheduleSend(player, server, playerId, nextGapMs, Math.min(nextGapMs * 2L, MAX_RESEND_MS));
        }, delayMs, TimeUnit.MILLISECONDS);

        tasks.put(playerId, task);
    }

    private static HandshakeSession createSession() {
        List<String> measure = pickMeasuredClasses();
        if (measure.isEmpty() || probeFactory == null) {
            return null;
        }

        try {
            ProbeFactory.Probe probe = probeFactory.build(measure);
            byte[] key = Attestation.hmac(probe.secret(), Attestation.expectedMeasurement(measure));

            byte[] nonce = Attestation.randomBytes(NONCE_BYTES);
            Challenge challenge = Challenge.of(nonce, probe.bytecode());
            byte[] packet = Attestation.compress(GSON.toJson(challenge));
            return new HandshakeSession(Attestation.encode(nonce), measure, key, packet, System.nanoTime());
        } catch (Exception e) {
            Informejtycy.LOGGER.error("[Anticheat] Could not build a challenge", e);
            return null;
        }
    }

    private static List<String> pickMeasuredClasses() {
        if (measurableClasses.isEmpty()) {
            return List.of();
        }

        List<String> measure = new ArrayList<>();
        for (String core : Attestation.ALWAYS_MEASURED) {
            if (measurableClasses.contains(core)) {
                measure.add(core);
            }
        }

        List<String> pool = new ArrayList<>(measurableClasses);
        pool.removeAll(measure);
        Collections.shuffle(pool, RANDOM);
        measure.addAll(pool.subList(0, Math.min(AnticheatConfig.DATA.measuredClassCount, pool.size())));

        Collections.shuffle(measure, RANDOM);
        return List.copyOf(measure);
    }

    private static void report(ServerPlayerEntity player, Verdict verdict, long latencyMs) {
        String name = player.getName().getString();
        String mods = formatModList(verdict.evidence());

        if (verdict.status() == Verdict.Status.OK) {
            Informejtycy.LOGGER.info("[Anticheat] {} verified in {}ms ({}ms probing) with mods: {}",
                    name, latencyMs, verdict.evidence().collectMs, mods);
            return;
        }

        Informejtycy.LOGGER.warn("[Anticheat] {} -> {} in {}ms: {}", name, verdict.status(), latencyMs,
                String.join(", ", verdict.reasons()));
        if (verdict.evidence() != null) {
            Informejtycy.LOGGER.warn("[Anticheat] {} reported mods: {}", name, mods);
        }
    }

    private static void enforce(ServerPlayerEntity player, Verdict verdict) {
        AnticheatConfig.ConfigData config = AnticheatConfig.DATA;
        String message = null;

        if (verdict.status() == Verdict.Status.FORBIDDEN && config.enforce) {
            message = "Wykryto niedozwolony mod: " + String.join(", ", verdict.reasons());
        } else if (verdict.status() == Verdict.Status.TAMPERED && config.requireAttestation) {
            message = "Nie udalo sie zweryfikowac moda Informejtycy. Pobierz oryginalna wersje.";
        } else if (verdict.status() == Verdict.Status.TIMEOUT && config.requireAttestation) {
            message = "Brak odpowiedzi od moda Informejtycy.";
        }

        if (message == null) {
            return;
        }

        String kickMessage = message;
        if (player.networkHandler != null) {
            player.networkHandler.disconnect(Text.literal(kickMessage));
        }
    }

    private static String formatModList(Evidence evidence) {
        if (evidence == null || evidence.mods == null) {
            return "<none>";
        }

        StringJoiner mods = new StringJoiner(", ");
        for (Evidence.ModEntry mod : evidence.mods) {
            mods.add(mod.version == null ? mod.id : mod.id + " " + mod.version);
        }
        return mods.toString();
    }

    private static void cancelTask(UUID playerId) {
        ScheduledFuture<?> task = tasks.remove(playerId);
        if (task != null) {
            task.cancel(false);
        }
    }

    private static void stopHandshake(UUID playerId) {
        sessions.remove(playerId);
        cancelTask(playerId);
    }
}
