package daw.ka.informejtycy.particle.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public class StinkAreaParticleEffect implements ParticleOptions {
    private final ParticleType<StinkAreaParticleEffect> type;
    private final float power;

    public static MapCodec<StinkAreaParticleEffect> createCodec(ParticleType<StinkAreaParticleEffect> type) {
        return Codec.FLOAT
                .xmap(power -> new StinkAreaParticleEffect(type, power), effect -> effect.power)
                .optionalFieldOf("power", new StinkAreaParticleEffect(type, 1.0F));
    }

    public static StreamCodec<? super ByteBuf, StinkAreaParticleEffect> createPacketCodec(ParticleType<StinkAreaParticleEffect> type) {
        return ByteBufCodecs.FLOAT.map(power -> new StinkAreaParticleEffect(type, power), effect -> effect.power);
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
