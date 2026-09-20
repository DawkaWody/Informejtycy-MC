package daw.ka.informejtycy.anticheat.server;

import net.minecraft.server.network.ServerPlayNetworkHandler;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public record HandshakeSession(ServerPlayNetworkHandler handler, String nonceString, List<String> measuredResources,
                               byte[] expectedKey, byte[] challengePacket, long issuedAtNanos,
                               AtomicInteger rejectedReplies) {
    public HandshakeSession(ServerPlayNetworkHandler handler, String nonceString, List<String> measuredResources,
                            byte[] expectedKey, byte[] challengePacket, long issuedAtNanos) {
        this(handler, nonceString, measuredResources, expectedKey, challengePacket, issuedAtNanos, new AtomicInteger());
    }

    public long latencyMs() {
        return (System.nanoTime() - issuedAtNanos) / 1_000_000L;
    }
}
