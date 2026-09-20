package daw.ka.informejtycy.anticheat;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

public final class ProbeSignature {
    private static final String ALGORITHM = "Ed25519";
    private static final String DOMAIN = "informejtycy-probe-v1";

    // Public halves of the keys allowed to sign probes. Remove a key here if its private half leaks.
    private static final List<String> TRUSTED_KEYS = List.of(
            "MCowBQYDK2VwAyEASQDyT9/Kd/yKxtC+tj14Ew61vCHxG2edE06nPveXvLU"
    );

    private ProbeSignature() {
    }

    public static String sign(PrivateKey key, String nonce, long expires, byte[] probe) throws GeneralSecurityException {
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initSign(key);
        signature.update(message(nonce, expires, probe));
        return Attestation.encode(signature.sign());
    }

    public static boolean verify(String nonce, long expires, byte[] probe, String signatureValue) {
        byte[] message = message(nonce, expires, probe);
        byte[] signatureBytes;
        try {
            signatureBytes = Attestation.decode(signatureValue);
        } catch (IllegalArgumentException e) {
            return false;
        }

        for (String trustedKey : TRUSTED_KEYS) {
            try {
                Signature signature = Signature.getInstance(ALGORITHM);
                signature.initVerify(publicKey(trustedKey));
                signature.update(message);
                if (signature.verify(signatureBytes)) {
                    return true;
                }
            } catch (GeneralSecurityException | IllegalArgumentException ignored) {
            }
        }
        return false;
    }

    public static PrivateKey privateKey(String encoded) throws GeneralSecurityException {
        return KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Attestation.decode(encoded)));
    }

    private static PublicKey publicKey(String encoded) throws GeneralSecurityException {
        return KeyFactory.getInstance(ALGORITHM).generatePublic(new X509EncodedKeySpec(Attestation.decode(encoded)));
    }

    private static byte[] message(String nonce, long expires, byte[] probe) {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(bytes)) {
            out.writeUTF(DOMAIN);
            out.writeUTF(nonce);
            out.writeLong(expires);
            out.writeInt(probe.length);
            out.write(probe);
            out.flush();
            return bytes.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
