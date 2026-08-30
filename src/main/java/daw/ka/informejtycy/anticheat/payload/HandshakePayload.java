package daw.ka.informejtycy.anticheat.payload;

import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.anticheat.Attestation;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import io.netty.buffer.ByteBuf;

public record HandshakePayload(byte[] data) implements CustomPayload {
    public static final Id<HandshakePayload> ID =
            new Id<>(Identifier.of(Informejtycy.MOD_ID, "handshake"));
    public static final PacketCodec<ByteBuf, HandshakePayload> CODEC =
            PacketCodecs.byteArray(Attestation.MAX_PACKET_BYTES).xmap(HandshakePayload::new, HandshakePayload::data);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
