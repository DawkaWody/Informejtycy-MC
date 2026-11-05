package daw.ka.informejtycy.entity.custom.projectile;

import daw.ka.informejtycy.entity.CustomEntities;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class RockProjectileEntity extends Projectile {
    public static final double GRAVITY = 0.03;
    private static final float DAMAGE = 14.0f;

    public RockProjectileEntity(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
    }

    public RockProjectileEntity(Level world, LivingEntity owner, Vec3 pos) {
        super(CustomEntities.ROCK_PROJECTILE, world);
        this.setOwner(owner);
        this.setPos(pos.x, pos.y, pos.z);
    }

    @Override
    protected double getDefaultGravity() {
        return GRAVITY;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 velocity = this.getDeltaMovement();
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        this.hitTargetOrDeflectSelf(hitResult);
        this.applyGravity();
        this.setPos(this.getX() + velocity.x, this.getY() + velocity.y, this.getZ() + velocity.z);
    }

    @Override
    protected boolean canHitEntity(@NonNull Entity entity) {
        return super.canHitEntity(entity) && entity != this.getOwner();
    }

    @Override
    protected void onHitEntity(@NonNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.getOwner() instanceof LivingEntity livingEntity) {
            Entity entity = entityHitResult.getEntity();
            DamageSource damageSource = this.damageSources().mobProjectile(this, livingEntity);
            if (this.level() instanceof ServerLevel serverWorld && entity.hurtServer(serverWorld, damageSource, DAMAGE)) {
                EnchantmentHelper.doPostAttackEffects(serverWorld, livingEntity, damageSource);
            }
        }
    }

    @Override
    protected void onHit(@NonNull HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState()),
                    this.getX(), this.getY() + this.getBbHeight() / 2, this.getZ(), 40, 0.4, 0.4, 0.4, 0.1);
            this.playSound(SoundEvents.STONE_BREAK, 1.5F, 0.7F);
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {

    }
}
