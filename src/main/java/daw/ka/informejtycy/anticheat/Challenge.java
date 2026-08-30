package daw.ka.informejtycy.anticheat;

public class Challenge {
    public String nonce;
    public String probe;

    public static Challenge of(byte[] nonce, byte[] probe) {
        Challenge challenge = new Challenge();
        challenge.nonce = Attestation.encode(nonce);
        challenge.probe = Attestation.encode(probe);
        return challenge;
    }
}
