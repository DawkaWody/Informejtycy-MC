package daw.ka.informejtycy.anticheat.server;

import java.util.List;

public record HandshakeSession(String nonceString, List<String> measuredResources,
                               byte[] expectedKey, byte[] challengePacket, long issuedAtNanos) {
    public long latencyMs() {
        return (System.nanoTime() - issuedAtNanos) / 1_000_000L;
    }
}
