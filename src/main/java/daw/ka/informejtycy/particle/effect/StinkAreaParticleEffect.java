package daw.ka.informejtycy.particle.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class StinkAreaParticleEffect implements ParticleEffect {
    private final ParticleType<StinkAreaParticleEffect> type;
    private final float power;

    public static MapCodec<StinkAreaParticleEffect> createCodec(ParticleType<StinkAreaParticleEffect> type) {
        return Codec.FLOAT
                .xmap(power -> new StinkAreaParticleEffect(type, power), effect -> effect.power)
                .optionalFieldOf("power", new StinkAreaParticleEffect(type, 1.0F));
    }

    public static PacketCodec<? super ByteBuf, StinkAreaParticleEffect> createPacketCodec(ParticleType<StinkAreaParticleEffect> type) {
        return PacketCodecs.FLOAT.xmap(power -> new StinkAreaParticleEffect(type, power), effect -> effect.power);
    }

    public StinkAreaParticleEffect(ParticleType<StinkAreaParticleEffect> type, float power) {
        this.type = type;
        this.power = power;
    }

    public float getPower() {
        return power;
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }
}
