package daw.ka.informejtycy.particle;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.particle.effect.StinkAreaParticleEffect;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;

public class CustomParticles {
    public static SimpleParticleType STINK_PARTICLE;
    public static ParticleType<StinkAreaParticleEffect> STINK_AREA_PARTICLE;

    public static void registerAll() {
        STINK_PARTICLE = InformejtycyRegistry.registerParticleType("stink", FabricParticleTypes.simple());
        STINK_AREA_PARTICLE = InformejtycyRegistry.registerParticleType("stink_area", false,
                StinkAreaParticleEffect::createCodec, StinkAreaParticleEffect::createPacketCodec);
    }
}
