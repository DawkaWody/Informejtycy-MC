package daw.ka.informejtycy.anticheat.server;

import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.anticheat.Attestation;
import daw.ka.informejtycy.anticheat.ProbeSignature;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;

public final class ProbeSigningKey {
    private static final String KEY_FILENAME = "anticheat_signing_key.txt";

    private ProbeSigningKey() {
    }

    public static PrivateKey loadOrNull() {
        Path file = FabricLoader.getInstance().getConfigDir().resolve(KEY_FILENAME);
        if (!Files.exists(file)) {
            Informejtycy.LOGGER.error("[Anticheat] No signing key at {}. Clients only run signed probes, "
                    + "so handshakes will be skipped until the key is in place", file);
            return null;
        }

        try {
            PrivateKey key = ProbeSignature.privateKey(Files.readString(file, StandardCharsets.UTF_8).strip());

            byte[] testProbe = Attestation.randomBytes(16);
            if (!ProbeSignature.verify("test", 0L, testProbe, ProbeSignature.sign(key, "test", 0L, testProbe))) {
                Informejtycy.LOGGER.error("[Anticheat] The signing key in {} is not trusted by this version "
                        + "of the mod; handshakes will be skipped", file);
                return null;
            }
            return key;
        } catch (Exception e) {
            Informejtycy.LOGGER.error("[Anticheat] Could not read the signing key from {}", file, e);
            return null;
        }
    }

    // Run once to create a new key pair. Put the private key in the server's config folder and the
    // public key in ProbeSignature.TRUSTED_KEYS.
    public static void main(String[] args) throws Exception {
        KeyPair pair = KeyPairGenerator.getInstance("Ed25519").generateKeyPair();
        System.out.println("Private key (config/" + KEY_FILENAME + ", keep secret):");
        System.out.println(Attestation.encode(pair.getPrivate().getEncoded()));
        System.out.println("Public key (ProbeSignature.TRUSTED_KEYS):");
        System.out.println(Attestation.encode(pair.getPublic().getEncoded()));
    }
}
