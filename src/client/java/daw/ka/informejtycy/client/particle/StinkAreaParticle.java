package daw.ka.informejtycy.client.particle;

import daw.ka.informejtycy.particle.effect.StinkAreaParticleEffect;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class StinkAreaParticle extends SingleQuadParticle {
    private static final float MIN_RED = 0.5450980F;
    private static final float MIN_GREEN = 0.81176470F;
    private static final float MIN_BLUE = 0.3647058F;
    private static final float MAX_RED = 0.3764705F;
    private static final float MAX_GREEN = 0.3294117F;
    private static final float MAX_BLUE = 0.2431372F;
    private boolean reachedGround;
    private final SpriteSet spriteProvider;

    protected StinkAreaParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider.first());

        this.friction = 0.96F;
        this.xd = velocityX;
        this.yd = velocityY;
        this.zd = velocityZ;
        this.rCol = Mth.nextFloat(this.random, MIN_RED, MAX_RED);
        this.gCol = Mth.nextFloat(this.random, MIN_GREEN, MAX_GREEN);
        this.bCol = Mth.nextFloat(this.random, MIN_BLUE, MAX_BLUE);
        this.quadSize *= 0.75F;
        this.lifetime = (int)(20.0 / (this.random.nextFloat() * 0.8 + 0.2));
        this.reachedGround = false;
        this.hasPhysics = false;
        this.spriteProvider = spriteProvider;
        this.setSpriteFromAge(spriteProvider);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.spriteProvider);
            if (this.onGround) {
                this.yd = 0.0;
                this.reachedGround = true;
            }

            if (this.reachedGround) {
                this.yd += 0.002;
            }

            this.move(this.xd, this.yd, this.zd);
            if (this.y == this.yo) {
                this.xd *= 1.1;
                this.zd *= 1.1;
            }

            this.xd = this.xd * this.friction;
            this.zd = this.zd * this.friction;
            if (this.reachedGround) {
                this.yd = this.yd * this.friction;
            }
        }
    }

    @Override
    protected SingleQuadParticle.@NonNull Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    @Override
    public float getQuadSize(float tickProgress) {
        return this.quadSize * Mth.clamp((this.age + tickProgress) / this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    public static class Factory implements ParticleProvider<StinkAreaParticleEffect> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(StinkAreaParticleEffect parameters, @NonNull ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, @NonNull RandomSource random) {
            StinkAreaParticle particle = new StinkAreaParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
            particle.setPower(parameters.getPower());
            return particle;
        }
    }
}