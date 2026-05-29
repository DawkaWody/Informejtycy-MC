package daw.ka.informejtycy.entity.custom.projectile;

import daw.ka.informejtycy.entity.CustomEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MilkProjectileEntity extends ProjectileEntity {
    private static final float DAMAGE = 5.0f;

    public MilkProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public MilkProjectileEntity(World world, LivingEntity owner, Vec3d pos) {
        super(CustomEntities.MILK_PROJECTILE, world);
        this.setOwner(owner);
        this.setPosition(pos.x, pos.y, pos.z);
    }

    @Override
    protected double getGravity() {
        return 0.06;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d velocity = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.hitOrDeflect(hitResult);
        this.updateRotation();
        if (this.getEntityWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.discard();
        }
        else {
            this.setVelocity(velocity.multiply(0.99f));
            this.applyGravity();
            this.setPosition(this.getX() + velocity.x, this.getY() + velocity.y, this.getZ() + velocity.z);
        }

        if (this.getEntityWorld().isClient()) spawnParticles(velocity);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.getOwner() instanceof LivingEntity livingEntity) {
            Entity entity = entityHitResult.getEntity();
            DamageSource damageSource = this.getDamageSources().spit(this, livingEntity);
            if (this.getEntityWorld() instanceof ServerWorld serverWorld && entity.damage(serverWorld, damageSource, DAMAGE)) {
                EnchantmentHelper.onTargetDamaged(serverWorld, livingEntity, damageSource);
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getEntityWorld().isClient()) this.discard();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        Vec3d vec3d = packet.getVelocity();
        spawnParticles(vec3d);
        this.setVelocity(vec3d);
    }

    private void spawnParticles(Vec3d vec3d) {
        for (int i = 0; i < 7; i++) {
            double d = 0.4 + 0.1 * i;
            this.getEntityWorld().addParticleClient(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), vec3d.x * d, vec3d.y, vec3d.z * d);
        }
    }
}
