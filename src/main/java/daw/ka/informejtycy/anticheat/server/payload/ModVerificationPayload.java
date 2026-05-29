package daw.ka.informejtycy.anticheat.server.payload;

import daw.ka.informejtycy.anticheat.server.InformejtycyAnticheatServer;
import net.minecraft.network.packet.CustomPayload;

public record ModVerificationPayload(String json) implements CustomPayload {
    public static final Id<ModVerificationPayload> ID = InformejtycyAnticheatServer.HANDSHAKE_CHANNEL;

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
