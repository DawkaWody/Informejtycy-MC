package daw.ka.informejtycy.client.particle;

import daw.ka.informejtycy.particle.effect.StinkAreaParticleEffect;
import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class StinkAreaParticle extends BillboardParticle {
    private static final float MIN_RED = 0.5450980F;
    private static final float MIN_GREEN = 0.81176470F;
    private static final float MIN_BLUE = 0.3647058F;
    private static final float MAX_RED = 0.3764705F;
    private static final float MAX_GREEN = 0.3294117F;
    private static final float MAX_BLUE = 0.2431372F;
    private boolean reachedGround;
    private final SpriteProvider spriteProvider;

    protected StinkAreaParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider.getFirst());

        this.velocityMultiplier = 0.96F;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.red = MathHelper.nextFloat(this.random, MIN_RED, MAX_RED);
        this.green = MathHelper.nextFloat(this.random, MIN_GREEN, MAX_GREEN);
        this.blue = MathHelper.nextFloat(this.random, MIN_BLUE, MAX_BLUE);
        this.scale *= 0.75F;
        this.maxAge = (int)(20.0 / (this.random.nextFloat() * 0.8 + 0.2));
        this.reachedGround = false;
        this.collidesWithWorld = false;
        this.spriteProvider = spriteProvider;
        this.updateSprite(spriteProvider);
    }

    @Override
    public void tick() {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.updateSprite(this.spriteProvider);
            if (this.onGround) {
                this.velocityY = 0.0;
                this.reachedGround = true;
            }

            if (this.reachedGround) {
                this.velocityY += 0.002;
            }

            this.move(this.velocityX, this.velocityY, this.velocityZ);
            if (this.y == this.lastY) {
                this.velocityX *= 1.1;
                this.velocityZ *= 1.1;
            }

            this.velocityX = this.velocityX * this.velocityMultiplier;
            this.velocityZ = this.velocityZ * this.velocityMultiplier;
            if (this.reachedGround) {
                this.velocityY = this.velocityY * this.velocityMultiplier;
            }
        }
    }

    @Override
    protected RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_OPAQUE;
    }

    @Override
    public float getSize(float tickProgress) {
        return this.scale * MathHelper.clamp((this.age + tickProgress) / this.maxAge * 32.0F, 0.0F, 1.0F);
    }

    public static class Factory implements ParticleFactory<StinkAreaParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(StinkAreaParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            StinkAreaParticle particle = new StinkAreaParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
            particle.move(parameters.getPower());
            return particle;
        }
    }
}