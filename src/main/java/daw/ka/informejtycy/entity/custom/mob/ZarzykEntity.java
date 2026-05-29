package daw.ka.informejtycy.entity.custom.mob;

import daw.ka.informejtycy.item.CustomItems;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class ZarzykEntity extends HostileEntity {
    public static final int MAX_HEALTH = 10;
    public static final int ATTACK_DAMAGE = 12;
    public static final int KNOCKBACK_STRENGTH = 2;
    public static final double FOLLOW_RANGE = 20f;
    public static final double SCALE = 0.45f;
    public static final double MOVEMENT_SPEED = 0.4f;
    public static final double ANGRY_MOVEMENT_SPEED = 0.67f;

    private static final byte STATUS_ATTACK = 116;

    private static final TargetPredicate.EntityPredicate ENTITY_IS_CHILD =
            (entity, world) -> entity.isBaby();

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();

    private static final int idleAnimationDuration = 20;
    private static final int walkAnimationDuration = 20;

    private long idleAnimationTimer = -1;
    private long walkAnimationTimer = -1;

    public ZarzykEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPersistent();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new WanderAroundGoal(this, MOVEMENT_SPEED));
        this.goalSelector.add(5, new MeleeAttackGoal(this, ANGRY_MOVEMENT_SPEED, false));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 10f));
        this.goalSelector.add(1, new LookAroundGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, SheepEntity.class, true, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, VillagerEntity.class, 0, false, false, ENTITY_IS_CHILD));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getEntityWorld().isClient()) {
            if (isMoving()) {
                this.idleAnimationState.stop();
                this.idleAnimationTimer = -1;

                if (this.walkAnimationTimer <= 0) {
                    this.walkAnimationTimer = walkAnimationDuration;
                    this.walkAnimationState.start(this.age);
                } else {
                    this.walkAnimationTimer--;
                }
            }
            else {
                this.walkAnimationState.stop();
                this.walkAnimationTimer = -1;

                if (this.idleAnimationTimer <= 0) {
                    this.idleAnimationTimer = idleAnimationDuration;
                    this.idleAnimationState.start(this.age);
                } else {
                    this.idleAnimationTimer--;
                }
            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);

        if (status == STATUS_ATTACK)
            this.attackAnimationState.start(this.age);
    }

    @Override
    public boolean tryAttack(ServerWorld world, Entity target) {
        world.sendEntityStatus(this, STATUS_ATTACK);
        boolean bl = super.tryAttack(world, target);
        if (bl && target instanceof PlayerEntity && this.random.nextBoolean()) {
            this.dropStack(world, new ItemStack(CustomItems.ZARZYK_GEL));
        }
        return bl;
    }

    @Override
    protected void dropLoot(ServerWorld world, DamageSource damageSource, boolean causedByPlayer) {
        this.dropStack(world, new ItemStack(Items.RABBIT_FOOT));
        super.dropLoot(world, damageSource, causedByPlayer);
    }

    private boolean isMoving() {
        return this.getVelocity().horizontalLengthSquared() >= 0.00002;
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    public static DefaultAttributeContainer.Builder createZarzykAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.MAX_HEALTH, MAX_HEALTH)
                .add(EntityAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE)
                .add(EntityAttributes.ATTACK_KNOCKBACK, KNOCKBACK_STRENGTH)
                .add(EntityAttributes.FOLLOW_RANGE, FOLLOW_RANGE)
                .add(EntityAttributes.SCALE, SCALE)
                .add(EntityAttributes.MOVEMENT_SPEED, MOVEMENT_SPEED);
    }
}
