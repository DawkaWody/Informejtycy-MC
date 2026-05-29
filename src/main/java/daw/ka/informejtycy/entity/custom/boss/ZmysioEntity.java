package daw.ka.informejtycy.entity.custom.boss;

import daw.ka.informejtycy.entity.custom.projectile.MilkProjectileEntity;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.particle.CustomParticles;
import daw.ka.informejtycy.particle.effect.StinkAreaParticleEffect;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ZmysioEntity extends HostileEntity implements RangedAttackMob {
    private static final TrackedData<Integer> TRACKED_ENTITY_ID = DataTracker.registerData(ZmysioEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> INVUL_TIMER = DataTracker.registerData(ZmysioEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> ARMORED = DataTracker.registerData(ZmysioEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static final int MAX_HEALTH = 530;
    public static final int ATTACK_DAMAGE = 13;
    public static final int ARMOR = 4;
    private static final int ON_SUMMONED_INVUL_TIMER = 10;
    private static final int ATTACK_INTERVAL_SECONDS = 10;
    private static final float MILK_ATTACK_RANGE = 15.0f;
    private static final float MILK_PROJECTILE_SPEED = 1.5f;
    private static final float SPIN_ATTACK_RADIUS = 5.0f;
    private static final float SPIN_ATTACK_DAMAGE = 8.0f;
    private static final float SPIN_ATTACK_KNOCKBACK = 1.2f;
    private static final float GAS_ATTACK_CHANCE = 0.8f;
    private static final float GAS_ATTACK_RADIUS = 9.5f;
    private static final int GAS_ATTACK_DURATION = 90;

    private static final byte STATUS_SHOOT_RIGHT = 67;
    private static final byte STATUS_SHOOT_LEFT = 68;
    private static final byte STATUS_SPIN_ATTACK = 69;
    private static final byte STATUS_GAS_ATTACK = 70;
    private static final byte STATUS_DEATH = 71;

    private static final TargetPredicate.EntityPredicate CAN_ATTACK_PREDICTATE = (entity, world) -> entity.isPlayer();
    private final ZmysioProjectileScheduler projectileScheduler = new ZmysioProjectileScheduler(this.getEntityWorld());
    private final ServerBossBar bossBar = (ServerBossBar) new ServerBossBar(this.getDisplayName(), BossBar.Color.PINK, BossBar.Style.PROGRESS).setDarkenSky(true);
    private AreaEffectCloudEntity stinkCloud;

    private int currentPhase = 0;
    private boolean summoned = false;
    private boolean isSpinning = false;

    public final AnimationState spawnAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootRightAnimationState = new AnimationState();
    public final AnimationState shootLeftAnimationState = new AnimationState();
    public final AnimationState spinAnimationState = new AnimationState();
    public final AnimationState gasAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();

    private static final int idleAnimationDuration = 20;
    private static final int spinAnimationDuration = 10;
    private static final int deathAnimationDuration = 35;
    private static final int shootRightAnimationOffset = 17;
    private static final int shootLeftAnimationOffset = 14;

    private long idleAnimationTimer = -1;
    private long deathAnimationTimer = -1;
    private long spinningTimer;
    private long cloudRemoveTick;
    private long attackCooldown;
    private UUID stinkCloudId;
    private boolean killed = false;
    private DamageSource killer;

    public ZmysioEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPersistent();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new AttackGoal(this));
        this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0, ATTACK_INTERVAL_SECONDS, MILK_ATTACK_RANGE));
        this.goalSelector.add(3, new LookAtEntityGoal(this, LivingEntity.class, 8.0F));
        this.goalSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, 0, false, false, CAN_ATTACK_PREDICTATE));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 0, false, false, CAN_ATTACK_PREDICTATE));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TRACKED_ENTITY_ID, 0);
        builder.add(INVUL_TIMER, 0);
        builder.add(ARMORED, false);
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putInt("Invul", this.getInvulnerableTimer());
        view.putBoolean("Summoned", this.summoned);
        if (this.stinkCloud != null && this.stinkCloud.isAlive()) {
            view.putString("CloudId", this.stinkCloud.getUuid().toString());
            view.putLong("CloudRemove", Math.max(0, this.cloudRemoveTick - this.age));
        }
        else {
            view.remove("CloudId");
            view.remove("CloudRemove");
        }
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.setInvulTimer(view.getInt("Invul", 0));
        this.summoned = view.getBoolean("Summoned", false);
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }

        this.stinkCloudId = null;
        this.cloudRemoveTick = 0;
        String cloudIdStr = view.getString("CloudId", null);
        long remove = view.getLong("CloudRemove", 0);
        if (cloudIdStr != null && remove > 0) {
            this.stinkCloudId = UUID.fromString(cloudIdStr);
            this.cloudRemoveTick = this.age + remove;
        }
    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    protected void mobTick(ServerWorld world) {
        if (this.killed) {
            this.deathAnimationTimer--;
            if (this.deathAnimationTimer <= 0) {
                die(world);
            }
            return;
        }

        if (this.getInvulnerableTimer() > 0) {
            int i = this.getInvulnerableTimer() - 1;
            float progress = 1.0F - (i / (float) ON_SUMMONED_INVUL_TIMER);
            this.bossBar.setPercent(progress);
            if (i <= 0) {
                world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.0F, false, World.ExplosionSourceType.MOB);
            }

            this.setInvulTimer(i);
            if (this.age % 10 == 0) {
                this.heal(this.getMaxHealth() / (2 * ON_SUMMONED_INVUL_TIMER));
            }
        }
        else {
            super.mobTick(world);

            if (isSpinning) {
                applySpinDamage();
                spinningTimer--;
                if (spinningTimer <= 0) {
                    isSpinning = false;
                }
            }

            cloudTick(world);

            int e = this.getTrackedEntityId();
            if (e > 0 && this.age >= this.attackCooldown) {
                this.attackCooldown = this.age + (ATTACK_INTERVAL_SECONDS - 1 + this.random.nextInt(2)) * 20L;
                LivingEntity entity = (LivingEntity) this.getEntityWorld().getEntityById(e);
                if (this.canTarget(entity) && this.canSee(entity) && entity instanceof PlayerEntity target) {
                    ArrayList<AttackType> avaiableAttacks = new ArrayList<>();
                    if (!isInAttackRange(target) && this.squaredDistanceTo(target) < MILK_ATTACK_RANGE * MILK_ATTACK_RANGE)
                        avaiableAttacks.add(AttackType.MILK_PROJECTILE);
                    if (this.random.nextFloat() <= GAS_ATTACK_CHANCE &&
                            this.getBoundingBox().expand(GAS_ATTACK_RADIUS).contains(target.getEntityPos()))
                        avaiableAttacks.add(AttackType.GAS);
                    if (this.currentPhase > 0 && this.getBoundingBox().expand(SPIN_ATTACK_RADIUS).contains(target.getEntityPos())) {
                        avaiableAttacks.add(AttackType.SPIN);
                        avaiableAttacks.add(AttackType.SPIN);
                    }

                    int s = avaiableAttacks.size();
                    AttackType attackType = s > 0 ? avaiableAttacks.get(this.random.nextInt(s)) : null;

                    switch (attackType) {
                        case MILK_PROJECTILE -> shootAt(target);
                        case SPIN -> spin();
                        case GAS -> gas(target);
                        case null -> {}
                    }
                }
                else {
                    this.setTrackedEntityId(0);
                }
            }
            else {
                PlayerEntity target = world.getClosestEntity(
                        PlayerEntity.class,
                        TargetPredicate.DEFAULT,
                        this,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        this.getBoundingBox().expand(MILK_ATTACK_RANGE)
                );
                this.setTrackedEntityId(target != null ? target.getId() : 0);
            }

            this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void tick() {
        super.tick();
        projectileScheduler.tick();

        if (!summoned) onSummoned();

        if (this.getEntityWorld().isClient()) {
            if (this.idleAnimationTimer <= 0) {
                this.idleAnimationTimer = idleAnimationDuration;
                this.idleAnimationState.start(this.age);
            }
            else {
                this.idleAnimationTimer--;
            }
        }
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (this.getInvulnerableTimer() > 0 || this.killed)
            return false;
        if (source.getSource() instanceof AreaEffectCloudEntity cloud && cloud.getParticleType() instanceof StinkAreaParticleEffect)
            return false;
        if (source.isOf(DamageTypes.MACE_SMASH)) {
            this.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1.0F, 1.0F);
            return false;
        }
        if (this.currentPhase > 0 && source.isOf(DamageTypes.ARROW))
            return false;
        if (this.getHealth() <= this.getMaxHealth() / 2) {
            this.currentPhase = 1;
            this.setArmored();
        }
        if (this.getHealth() - amount <= 0 && !killed) {
            this.killer = source;
            this.initiateDeath();
            return false;
        }
        return super.damage(world, source, amount);
    }

    @Override
    protected void dropLoot(ServerWorld world, DamageSource source, boolean causedByPlayer) {
        super.dropLoot(world, source, causedByPlayer);
        ItemStack stack = new ItemStack(CustomItems.PLUS, 3 + (this.random.nextBoolean() ? 1 : 0));
        ItemEntity drop = this.dropStack(world, stack);
        if (drop != null) {
            drop.setCovetedItem();
        }
        ExperienceOrbEntity.spawn(world, this.getEntityPos(), 200 + this.random.nextInt(50));
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.BUCKET)) {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            ItemStack milkResult = ItemUsage.exchangeStack(itemStack, player, CustomItems.ZMYSIO_MILK_BUCKET.getDefaultStack());
            player.setStackInHand(hand, milkResult);
            return ActionResult.SUCCESS;
        }
        else {
            return super.interactMob(player, hand);
        }
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);

        if (status == STATUS_SHOOT_LEFT)
            this.shootLeftAnimationState.start(this.age);
        else if (status == STATUS_SHOOT_RIGHT)
            this.shootRightAnimationState.start(this.age);
        else if (status == STATUS_SPIN_ATTACK)
            this.spinAnimationState.start(this.age);
        else if (status == STATUS_GAS_ATTACK)
            this.gasAnimationState.start(this.age);
        else if (status == STATUS_DEATH)
            this.deathAnimationState.start(this.age);
    }

    public void onSummoned() {
        this.summoned = true;
        this.setInvulTimer(ON_SUMMONED_INVUL_TIMER);
        this.bossBar.setPercent(0.0F);
        this.spawnAnimationState.start(this.age);
    }

    private void initiateDeath() {
        this.bossBar.clearPlayers();
        this.getEntityWorld().sendEntityStatus(this, STATUS_DEATH);
        this.deathAnimationTimer = deathAnimationDuration;
        this.killed = true;
    }

    private void die(ServerWorld world) {
        if (this.stinkCloud != null) this.stinkCloud.discard();
        super.damage(world, this.killer, this.getMaxHealth());
    }

    private void shootAt(LivingEntity target) {
        boolean side = this.random.nextBoolean();
        shootMilkProjectileAt(side, target);
        if (this.random.nextBetween(0, 10) % 3 == 0) {
            shootMilkProjectileAt(!side, target);
        }
    }

    private void shootMilkProjectileAt(boolean side, LivingEntity target) {
        this.shootMilkProjectileAt(side, target.getX(), target.getY() + target.getStandingEyeHeight() * 0.5, target.getZ());
    }

    private void shootMilkProjectileAt(boolean side, double targetX, double targetY, double targetZ) {
        Vec3d start = this.getSutCoords(side);
        Vec3d direction = new Vec3d(targetX, targetY, targetZ).subtract(start);
        Vec3d velocity = direction.normalize().multiply(MILK_PROJECTILE_SPEED);

        MilkProjectileEntity projectile = new MilkProjectileEntity(this.getEntityWorld(), this, start);
        projectile.setVelocity(velocity);

        this.getEntityWorld().sendEntityStatus(this, side ? STATUS_SHOOT_RIGHT : STATUS_SHOOT_LEFT);
        projectileScheduler.schedule(projectile, side ? shootRightAnimationOffset : shootLeftAnimationOffset);
    }

    private void spin() {
        isSpinning = true;
        spinningTimer = spinAnimationDuration;
        this.getEntityWorld().sendEntityStatus(this, STATUS_SPIN_ATTACK);
    }

    private void applySpinDamage() {
        Box box = this.getBoundingBox().expand(SPIN_ATTACK_RADIUS);
        List<LivingEntity> entities = this.getEntityWorld().getEntitiesByClass(LivingEntity.class, box, entity -> entity != this && this.canTarget(entity));
        for (LivingEntity entity : entities) {
            entity.damage((ServerWorld) getEntityWorld(), getEntityWorld().getDamageSources().mobAttack(this), SPIN_ATTACK_DAMAGE);
            Vec3d knockbackDir = entity.getEntityPos().subtract(this.getEntityPos()).normalize();
            entity.addVelocity(knockbackDir.x * SPIN_ATTACK_KNOCKBACK, 0.1, knockbackDir.z * SPIN_ATTACK_KNOCKBACK);
        }
    }

    private void gas(LivingEntity target) {
        if (!(this.getEntityWorld() instanceof ServerWorld serverWorld)) return;
        this.cloudRemoveTick = this.age;
        cloudTick(serverWorld);

        for (int i = 0; i < 60; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 2.0;
            double offsetY = this.random.nextDouble() * 2.0;
            double offsetZ = (this.random.nextDouble() - 0.5) * 2.0;

            double velocityX = (this.random.nextDouble() - 0.5) * 0.1;
            double velocityY = this.random.nextDouble() * 0.05;
            double velocityZ = (this.random.nextDouble() - 0.5) * 0.1;

            serverWorld.spawnParticles(CustomParticles.STINK_PARTICLE,
                    this.getX() + offsetX,
                    this.getY() + 1.0 + offsetY,
                    this.getZ() + offsetZ,
                    1,
                    velocityX, velocityY, velocityZ,
                    0.01);
        }

        this.stinkCloud = new AreaEffectCloudEntity(serverWorld, target.getX(), target.getY(), target.getZ());
        this.stinkCloud.setOwner(this);
        this.stinkCloud.setRadius(GAS_ATTACK_RADIUS);
        this.stinkCloud.setDuration(-1);
        this.stinkCloud.setParticleType(new StinkAreaParticleEffect(CustomParticles.STINK_AREA_PARTICLE, 1.0F));
        this.stinkCloud.setPotionDurationScale(0.25f);
        this.stinkCloud.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE));
        serverWorld.spawnEntity(this.stinkCloud);

        this.cloudRemoveTick = this.age + GAS_ATTACK_DURATION;
        this.getEntityWorld().sendEntityStatus(this, STATUS_GAS_ATTACK);
    }

    private Vec3d getSutCoords(boolean side) {
        double localX = side ? (13.0 - 0.83) : (-15.0 - 3);
        double localY = side ? (32.0 + 0.83) : 32.0;
        double localZ = side ? (-14.0 - 9.33) : (-14 - 2);
        localX /= 16.0;
        localY /= 16.0;
        localZ /= 16.0;

        double radians = Math.toRadians(this.getYaw() - 180);

        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        double worldX = this.getX() + (localX * cos - localZ * sin);
        double worldZ = this.getZ() + (localX * sin + localZ * cos);
        double worldY = this.getY() + localY;

        return new Vec3d(worldX, worldY, worldZ);
    }

    private void cloudTick(ServerWorld world) {
        if (this.stinkCloud == null && this.stinkCloudId != null) {
            Entity e = world.getEntity(this.stinkCloudId);
            if (e instanceof AreaEffectCloudEntity cloud) {
                this.stinkCloud = cloud;
            }
        }
        if (this.age == this.cloudRemoveTick) {
            if (this.stinkCloud != null) {
                this.stinkCloud.discard();
            }
            this.stinkCloud = null;
            this.stinkCloudId = null;
        }
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    public static DefaultAttributeContainer.Builder createZmysioAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, MAX_HEALTH)
                .add(EntityAttributes.MOVEMENT_SPEED, 0)
                .add(EntityAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE)
                .add(EntityAttributes.ARMOR, ARMOR)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    public int getInvulnerableTimer() {
        return this.dataTracker.get(INVUL_TIMER);
    }

    public void setInvulTimer(int ticks) {
        this.dataTracker.set(INVUL_TIMER, ticks);
    }

    public int getTrackedEntityId() {
        return this.dataTracker.<Integer>get(TRACKED_ENTITY_ID);
    }

    public void setTrackedEntityId(int id) {
        this.dataTracker.set(TRACKED_ENTITY_ID, id);
    }

    private void setArmored() {
        this.dataTracker.set(ARMORED, true);
    }

    public boolean isArmored() {
        return this.dataTracker.get(ARMORED);
    }

    @Override
    public boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source) {
        return false;
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortals(boolean allowVehicles) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {

    }

    @Override
    public void slowMovement(BlockState state, Vec3d multiplier) {

    }

    enum AttackType {
        MILK_PROJECTILE,
        SPIN,
        GAS
    }

    static class ZmysioProjectileScheduler {
        private final World world;
        private final HashMap<Long, MilkProjectileEntity> scheduledProjectiles = new HashMap<>();
        private long currentTick = 0;

        public ZmysioProjectileScheduler(World world) {
            this.world = world;
        }

        public void schedule(MilkProjectileEntity projectile, long delayTicks) {
            scheduledProjectiles.put(currentTick + delayTicks, projectile);
        }

        public void tick() {
            currentTick++;

            if (scheduledProjectiles.containsKey(currentTick)) {
                this.world.spawnEntity(scheduledProjectiles.get(currentTick));
                scheduledProjectiles.remove(currentTick);
            }
        }
    }
}
