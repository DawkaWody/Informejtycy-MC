package daw.ka.informejtycy.entity.custom.projectile;

import daw.ka.informejtycy.entity.CustomEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class MeteorProjectileEntity extends Projectile {
    private static final float DAMAGE = 14.0f;
    private static final float BLAST_RADIUS = 3.0f;
    private static final float BURN_SECONDS = 4.0f;
    private static final int WARNING_POINTS = 14;

    private @Nullable Vec3 landing;

    public MeteorProjectileEntity(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
    }

    public MeteorProjectileEntity(Level world, LivingEntity owner, Vec3 pos, @Nullable Vec3 landing) {
        super(CustomEntities.METEOR_PROJECTILE, world);
        this.setOwner(owner);
        this.setPos(pos.x, pos.y, pos.z);
        this.landing = landing;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.0;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 velocity = this.getDeltaMovement();
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        this.hitTargetOrDeflectSelf(hitResult);
        this.setPos(this.getX() + velocity.x, this.getY() + velocity.y, this.getZ() + velocity.z);

        if (this.level().isClientSide()) {
            double y = this.getY() + this.getBbHeight() / 2;
            this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), y, this.getZ(), 0, 0, 0);
            this.level().addParticle(ParticleTypes.FLAME, this.getX(), y, this.getZ(), 0, 0, 0);
        }
        else if (this.landing != null && this.tickCount % 2 == 0 && this.level() instanceof ServerLevel serverWorld) {
            for (int i = 0; i < WARNING_POINTS; i++) {
                float angle = Mth.TWO_PI * i / WARNING_POINTS;
                serverWorld.sendParticles(ParticleTypes.FLAME, this.landing.x + Mth.cos(angle) * BLAST_RADIUS,
                        this.landing.y + 0.1, this.landing.z + Mth.sin(angle) * BLAST_RADIUS, 1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    protected boolean canHitEntity(@NonNull Entity entity) {
        return super.canHitEntity(entity) && entity != this.getOwner();
    }

    @Override
    protected void onHit(@NonNull HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel serverWorld) {
            Vec3 center = hitResult.getLocation();
            serverWorld.sendParticles(ParticleTypes.EXPLOSION, center.x, center.y + 0.5, center.z, 2, 0.5, 0.3, 0.5, 0);
            serverWorld.sendParticles(ParticleTypes.LAVA, center.x, center.y + 0.5, center.z, 12, 0.6, 0.3, 0.6, 0);
            serverWorld.sendParticles(ParticleTypes.LARGE_SMOKE, center.x, center.y + 0.5, center.z, 16, 0.8, 0.4, 0.8, 0.03);
            this.playSound(SoundEvents.GENERIC_EXPLODE.value(), 2.0F, 0.8F);

            for (LivingEntity entity : serverWorld.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(BLAST_RADIUS),
                    entity -> entity != this.getOwner() && entity.distanceToSqr(center) <= BLAST_RADIUS * BLAST_RADIUS)) {
                float falloff = 1.0f - 0.5f * (float) (Math.sqrt(entity.distanceToSqr(center)) / BLAST_RADIUS);
                if (entity.hurtServer(serverWorld, this.damageSources().explosion(this, this.getOwner()), DAMAGE * falloff)) {
                    entity.igniteForSeconds(BURN_SECONDS);
                }
            }
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {

    }
}
