package daw.ka.informejtycy.entity.custom.mob;

import daw.ka.informejtycy.item.CustomItems;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class ZarzykEntity extends Monster {
    public static final int MAX_HEALTH = 10;
    public static final int ATTACK_DAMAGE = 12;
    public static final int KNOCKBACK_STRENGTH = 2;
    public static final double FOLLOW_RANGE = 20f;
    public static final double SCALE = 0.45f;
    public static final double MOVEMENT_SPEED = 0.4f;
    public static final double ANGRY_MOVEMENT_SPEED = 0.67f;

    private static final byte STATUS_ATTACK = 116;

    private static final TargetingConditions.Selector ENTITY_IS_CHILD =
            (entity, world) -> entity.isBaby();

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();

    private static final int idleAnimationDuration = 20;
    private static final int walkAnimationDuration = 20;

    private long idleAnimationTimer = -1;
    private long walkAnimationTimer = -1;

    public ZarzykEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, ANGRY_MOVEMENT_SPEED, false));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, MOVEMENT_SPEED));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 10f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Sheep.class, true, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, 0, false, false, ENTITY_IS_CHILD));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            if (isMoving()) {
                this.idleAnimationState.stop();
                this.idleAnimationTimer = -1;

                if (this.walkAnimationTimer <= 0) {
                    this.walkAnimationTimer = walkAnimationDuration;
                    this.walkAnimationState.start(this.tickCount);
                } else {
                    this.walkAnimationTimer--;
                }
            }
            else {
                this.walkAnimationState.stop();
                this.walkAnimationTimer = -1;

                if (this.idleAnimationTimer <= 0) {
                    this.idleAnimationTimer = idleAnimationDuration;
                    this.idleAnimationState.start(this.tickCount);
                } else {
                    this.idleAnimationTimer--;
                }
            }
        }
    }

    @Override
    public void handleEntityEvent(byte status) {
        super.handleEntityEvent(status);

        if (status == STATUS_ATTACK)
            this.attackAnimationState.start(this.tickCount);
    }

    @Override
    public boolean doHurtTarget(ServerLevel world, @NonNull Entity target) {
        world.broadcastEntityEvent(this, STATUS_ATTACK);
        boolean bl = super.doHurtTarget(world, target);
        if (bl && target instanceof Player && this.random.nextBoolean()) {
            this.spawnAtLocation(world, new ItemStack(CustomItems.ZARZYK_GEL));
        }
        return bl;
    }

    @Override
    protected void dropFromLootTable(@NonNull ServerLevel world, @NonNull DamageSource damageSource, boolean causedByPlayer) {
        this.spawnAtLocation(world, new ItemStack(Items.RABBIT_FOOT));
        super.dropFromLootTable(world, damageSource, causedByPlayer);
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() >= 0.00002;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    public static AttributeSupplier.Builder createZarzykAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE)
                .add(Attributes.ATTACK_KNOCKBACK, KNOCKBACK_STRENGTH)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE)
                .add(Attributes.SCALE, SCALE)
                .add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED);
    }
}
