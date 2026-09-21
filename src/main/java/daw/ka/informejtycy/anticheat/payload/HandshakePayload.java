package daw.ka.informejtycy.anticheat.payload;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.anticheat.Attestation;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import io.netty.buffer.ByteBuf;
import org.jspecify.annotations.NonNull;

public record HandshakePayload(byte[] data) implements CustomPacketPayload {
    public static final Type<HandshakePayload> ID =
            new Type<>(InformejtycyRegistry.id("handshake"));
    public static final StreamCodec<ByteBuf, HandshakePayload> CODEC =
            ByteBufCodecs.byteArray(Attestation.MAX_PACKET_BYTES).map(HandshakePayload::new, HandshakePayload::data);

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
