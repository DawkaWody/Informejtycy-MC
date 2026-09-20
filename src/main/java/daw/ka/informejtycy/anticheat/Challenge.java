package daw.ka.informejtycy.anticheat;

public class Challenge {
    public String nonce;
    public String probe;
    public long expires;
    public String signature;

    public static Challenge of(byte[] nonce, byte[] probe, long expires, String signature) {
        Challenge challenge = new Challenge();
        challenge.nonce = Attestation.encode(nonce);
        challenge.probe = Attestation.encode(probe);
        challenge.expires = expires;
        challenge.signature = signature;
        return challenge;
    }
}
