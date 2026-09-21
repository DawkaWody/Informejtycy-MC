package daw.ka.informejtycy.entity.custom.projectile;

import daw.ka.informejtycy.entity.CustomEntities;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

public class MilkProjectileEntity extends Projectile {
    private static final float DAMAGE = 5.0f;

    public MilkProjectileEntity(EntityType<? extends Projectile> entityType, Level world) {
        super(entityType, world);
    }

    public MilkProjectileEntity(Level world, LivingEntity owner, Vec3 pos) {
        super(CustomEntities.MILK_PROJECTILE, world);
        this.setOwner(owner);
        this.setPos(pos.x, pos.y, pos.z);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.06;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 velocity = this.getDeltaMovement();
        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        this.hitTargetOrDeflectSelf(hitResult);
        this.updateRotation();
        if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
            this.discard();
        }
        else {
            this.setDeltaMovement(velocity.scale(0.99f));
            this.applyGravity();
            this.setPos(this.getX() + velocity.x, this.getY() + velocity.y, this.getZ() + velocity.z);
        }

        if (this.level().isClientSide()) spawnParticles(velocity);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.getOwner() instanceof LivingEntity livingEntity) {
            Entity entity = entityHitResult.getEntity();
            DamageSource damageSource = this.damageSources().spit(this, livingEntity);
            if (this.level() instanceof ServerLevel serverWorld && entity.hurtServer(serverWorld, damageSource, DAMAGE)) {
                EnchantmentHelper.doPostAttackEffects(serverWorld, livingEntity, damageSource);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide()) this.discard();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        Vec3 vec3d = packet.getMovement();
        spawnParticles(vec3d);
        this.setDeltaMovement(vec3d);
    }

    private void spawnParticles(Vec3 vec3d) {
        for (int i = 0; i < 7; i++) {
            double d = 0.4 + 0.1 * i;
            this.level().addParticle(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), vec3d.x * d, vec3d.y, vec3d.z * d);
        }
    }
}
