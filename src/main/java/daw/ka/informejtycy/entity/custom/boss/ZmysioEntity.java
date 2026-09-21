package daw.ka.informejtycy.entity.custom.boss;

import daw.ka.informejtycy.entity.custom.projectile.MilkProjectileEntity;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.particle.CustomParticles;
import daw.ka.informejtycy.particle.effect.StinkAreaParticleEffect;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.BossEvent;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ZmysioEntity extends Monster implements RangedAttackMob {
    private static final EntityDataAccessor<Integer> TRACKED_ENTITY_ID = SynchedEntityData.defineId(ZmysioEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INVUL_TIMER = SynchedEntityData.defineId(ZmysioEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ARMORED = SynchedEntityData.defineId(ZmysioEntity.class, EntityDataSerializers.BOOLEAN);

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
    private static final byte STATUS_SPAWN = 72;

    private static final TargetingConditions.Selector CAN_ATTACK_PREDICTATE = (entity, world) -> entity.isAlwaysTicking();
    private final ZmysioProjectileScheduler projectileScheduler = new ZmysioProjectileScheduler(this.level());
    private final ServerBossEvent bossBar = (ServerBossEvent) new ServerBossEvent(UUID.randomUUID(), this.getDisplayName(), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true);
    private AreaEffectCloud stinkCloud;

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
    private UUID killerId;

    public ZmysioEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new OcelotAttackGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, ATTACK_INTERVAL_SECONDS, MILK_ATTACK_RANGE));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, LivingEntity.class, 8.0F));
        this.goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 0, false, false, CAN_ATTACK_PREDICTATE));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 0, false, false, CAN_ATTACK_PREDICTATE));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TRACKED_ENTITY_ID, 0);
        builder.define(INVUL_TIMER, 0);
        builder.define(ARMORED, false);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput view) {
        super.addAdditionalSaveData(view);
        view.putInt("Invul", this.getInvulnerableTimer());
        view.putBoolean("Summoned", this.summoned);
        view.putInt("Phase", this.currentPhase);
        view.putBoolean("Armored", this.isArmored());
        view.putBoolean("Killed", this.killed);
        if (this.killed && this.getKillerId() != null) {
            view.putString("KillerId", this.getKillerId().toString());
        }

        UUID cloudId = this.stinkCloud != null && this.stinkCloud.isAlive() ? this.stinkCloud.getUUID() : this.stinkCloudId;
        if (cloudId != null && this.cloudRemoveTick > this.tickCount) {
            view.putString("CloudId", cloudId.toString());
            view.putLong("CloudRemove", this.cloudRemoveTick - this.tickCount);
        }
        else {
            view.discard("CloudId");
            view.discard("CloudRemove");
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput view) {
        super.readAdditionalSaveData(view);
        this.setInvulTimer(view.getIntOr("Invul", 0));
        this.summoned = view.getBooleanOr("Summoned", false);
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }

        this.currentPhase = view.getIntOr("Phase", 0);
        this.entityData.set(ARMORED, view.getBooleanOr("Armored", false));
        this.killed = view.getBooleanOr("Killed", false);
        this.killerId = null;
        if (this.killed) {
            this.deathAnimationTimer = deathAnimationDuration;
            String killerIdStr = view.getStringOr("KillerId", null);
            if (killerIdStr != null) {
                this.killerId = UUID.fromString(killerIdStr);
            }
        }

        this.stinkCloudId = null;
        this.cloudRemoveTick = 0;
        String cloudIdStr = view.getStringOr("CloudId", null);
        long remove = view.getLongOr("CloudRemove", 0);
        if (cloudIdStr != null && remove > 0) {
            this.stinkCloudId = UUID.fromString(cloudIdStr);
            this.cloudRemoveTick = this.tickCount + remove;
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    protected void customServerAiStep(ServerLevel world) {
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
            this.bossBar.setProgress(progress);
            if (i <= 0) {
                world.explode(this, this.getX(), this.getY(), this.getZ(), 2.0F, false, Level.ExplosionInteraction.MOB);
            }

            this.setInvulTimer(i);
            if (this.tickCount % 10 == 0) {
                this.heal(this.getMaxHealth() / (2 * ON_SUMMONED_INVUL_TIMER));
            }
        }
        else {
            super.customServerAiStep(world);

            if (isSpinning) {
                applySpinDamage();
                spinningTimer--;
                if (spinningTimer <= 0) {
                    isSpinning = false;
                }
            }

            cloudTick(world);

            int e = this.getTrackedEntityId();
            if (e > 0 && this.tickCount >= this.attackCooldown) {
                this.attackCooldown = this.tickCount + (ATTACK_INTERVAL_SECONDS - 1 + this.random.nextInt(2)) * 20L;
                if (this.level().getEntity(e) instanceof Player target
                        && this.canAttack(target) && this.hasLineOfSight(target)) {
                    ArrayList<AttackType> avaiableAttacks = new ArrayList<>();
                    if (!isWithinMeleeAttackRange(target) && this.distanceToSqr(target) < MILK_ATTACK_RANGE * MILK_ATTACK_RANGE)
                        avaiableAttacks.add(AttackType.MILK_PROJECTILE);
                    if (this.random.nextFloat() <= GAS_ATTACK_CHANCE &&
                            this.getBoundingBox().inflate(GAS_ATTACK_RADIUS).contains(target.position()))
                        avaiableAttacks.add(AttackType.GAS);
                    if (this.currentPhase > 0 && this.getBoundingBox().inflate(SPIN_ATTACK_RADIUS).contains(target.position())) {
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
                Player target = world.getNearestEntity(
                        Player.class,
                        TargetingConditions.DEFAULT,
                        this,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        this.getBoundingBox().inflate(MILK_ATTACK_RANGE)
                );
                this.setTrackedEntityId(target != null ? target.getId() : 0);
            }

            this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    @Override
    public void tick() {
        super.tick();
        projectileScheduler.tick();

        if (!summoned && !this.level().isClientSide()) onSummoned();

        if (this.level().isClientSide()) {
            if (this.idleAnimationTimer <= 0) {
                this.idleAnimationTimer = idleAnimationDuration;
                this.idleAnimationState.start(this.tickCount);
            }
            else {
                this.idleAnimationTimer--;
            }
        }
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        if (this.getInvulnerableTimer() > 0 || this.killed)
            return false;
        if (source.getDirectEntity() instanceof AreaEffectCloud cloud && cloud.getParticle() instanceof StinkAreaParticleEffect)
            return false;
        if (source.is(DamageTypes.MACE_SMASH)) {
            this.playSound(SoundEvents.ANVIL_PLACE, 1.0F, 1.0F);
            return false;
        }
        if (this.currentPhase > 0 && source.is(DamageTypes.ARROW))
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
        return super.hurtServer(world, source, amount);
    }

    @Override
    protected void dropFromLootTable(ServerLevel world, DamageSource source, boolean causedByPlayer) {
        super.dropFromLootTable(world, source, causedByPlayer);
        ItemStack stack = new ItemStack(CustomItems.PLUS, 3 + (this.random.nextBoolean() ? 1 : 0));
        ItemEntity drop = this.spawnAtLocation(world, stack);
        if (drop != null) {
            drop.setExtendedLifetime();
        }
        ExperienceOrb.award(world, this.position(), 200 + this.random.nextInt(50));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.BUCKET)) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack milkResult = ItemUtils.createFilledResult(itemStack, player, CustomItems.ZMYSIO_MILK_BUCKET.getDefaultInstance());
            player.setItemInHand(hand, milkResult);
            return InteractionResult.SUCCESS;
        }
        else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void handleEntityEvent(byte status) {
        super.handleEntityEvent(status);

        if (status == STATUS_SHOOT_LEFT)
            this.shootLeftAnimationState.start(this.tickCount);
        else if (status == STATUS_SHOOT_RIGHT)
            this.shootRightAnimationState.start(this.tickCount);
        else if (status == STATUS_SPIN_ATTACK)
            this.spinAnimationState.start(this.tickCount);
        else if (status == STATUS_GAS_ATTACK)
            this.gasAnimationState.start(this.tickCount);
        else if (status == STATUS_DEATH)
            this.deathAnimationState.start(this.tickCount);
        else if (status == STATUS_SPAWN)
            this.spawnAnimationState.start(this.tickCount);
    }

    public void onSummoned() {
        this.summoned = true;
        this.setInvulTimer(ON_SUMMONED_INVUL_TIMER);
        this.bossBar.setProgress(0.0F);
        this.level().broadcastEntityEvent(this, STATUS_SPAWN);
    }

    private void initiateDeath() {
        this.bossBar.removeAllPlayers();
        this.level().broadcastEntityEvent(this, STATUS_DEATH);
        this.deathAnimationTimer = deathAnimationDuration;
        this.killed = true;
    }

    private void die(ServerLevel world) {
        if (this.stinkCloud != null) this.stinkCloud.discard();
        super.hurtServer(world, this.killer != null ? this.killer : loadedKiller(world), this.getMaxHealth());
    }

    private DamageSource loadedKiller(ServerLevel world) {
        Entity attacker = this.killerId != null ? world.getEntity(this.killerId) : null;
        if (attacker instanceof Player player) {
            return world.damageSources().playerAttack(player);
        }
        if (attacker instanceof LivingEntity living) {
            return world.damageSources().mobAttack(living);
        }
        return world.damageSources().generic();
    }

    private @Nullable UUID getKillerId() {
        if (this.killer != null && this.killer.getEntity() != null) {
            return this.killer.getEntity().getUUID();
        }
        return this.killerId;
    }

    private void shootAt(LivingEntity target) {
        boolean side = this.random.nextBoolean();
        shootMilkProjectileAt(side, target);
        if (this.random.nextIntBetweenInclusive(0, 10) % 3 == 0) {
            shootMilkProjectileAt(!side, target);
        }
    }

    private void shootMilkProjectileAt(boolean side, LivingEntity target) {
        this.shootMilkProjectileAt(side, target.getX(), target.getY() + target.getEyeHeight() * 0.5, target.getZ());
    }

    private void shootMilkProjectileAt(boolean side, double targetX, double targetY, double targetZ) {
        Vec3 start = this.getSutCoords(side);
        Vec3 direction = new Vec3(targetX, targetY, targetZ).subtract(start);
        Vec3 velocity = direction.normalize().scale(MILK_PROJECTILE_SPEED);

        MilkProjectileEntity projectile = new MilkProjectileEntity(this.level(), this, start);
        projectile.setDeltaMovement(velocity);

        this.level().broadcastEntityEvent(this, side ? STATUS_SHOOT_RIGHT : STATUS_SHOOT_LEFT);
        projectileScheduler.schedule(projectile, side ? shootRightAnimationOffset : shootLeftAnimationOffset);
    }

    private void spin() {
        isSpinning = true;
        spinningTimer = spinAnimationDuration;
        this.level().broadcastEntityEvent(this, STATUS_SPIN_ATTACK);
    }

    private void applySpinDamage() {
        AABB box = this.getBoundingBox().inflate(SPIN_ATTACK_RADIUS);
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, box, entity -> entity != this && this.canAttack(entity));
        for (LivingEntity entity : entities) {
            entity.hurtServer((ServerLevel) level(), level().damageSources().mobAttack(this), SPIN_ATTACK_DAMAGE);
            Vec3 knockbackDir = entity.position().subtract(this.position()).normalize();
            entity.push(knockbackDir.x * SPIN_ATTACK_KNOCKBACK, 0.1, knockbackDir.z * SPIN_ATTACK_KNOCKBACK);
        }
    }

    private void gas(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel serverWorld)) return;
        this.cloudRemoveTick = this.tickCount;
        cloudTick(serverWorld);

        for (int i = 0; i < 60; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 2.0;
            double offsetY = this.random.nextDouble() * 2.0;
            double offsetZ = (this.random.nextDouble() - 0.5) * 2.0;

            double velocityX = (this.random.nextDouble() - 0.5) * 0.1;
            double velocityY = this.random.nextDouble() * 0.05;
            double velocityZ = (this.random.nextDouble() - 0.5) * 0.1;

            serverWorld.sendParticles(CustomParticles.STINK_PARTICLE,
                    this.getX() + offsetX,
                    this.getY() + 1.0 + offsetY,
                    this.getZ() + offsetZ,
                    1,
                    velocityX, velocityY, velocityZ,
                    0.01);
        }

        this.stinkCloud = new AreaEffectCloud(serverWorld, target.getX(), target.getY(), target.getZ());
        this.stinkCloud.setOwner(this);
        this.stinkCloud.setRadius(GAS_ATTACK_RADIUS);
        this.stinkCloud.setDuration(GAS_ATTACK_DURATION);
        this.stinkCloud.setCustomParticle(new StinkAreaParticleEffect(CustomParticles.STINK_AREA_PARTICLE, 1.0F));
        this.stinkCloud.setPotionDurationScale(0.25f);
        this.stinkCloud.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE));
        serverWorld.addFreshEntity(this.stinkCloud);

        this.cloudRemoveTick = this.tickCount + GAS_ATTACK_DURATION;
        this.level().broadcastEntityEvent(this, STATUS_GAS_ATTACK);
    }

    private Vec3 getSutCoords(boolean side) {
        double localX = side ? (13.0 - 0.83) : (-15.0 - 3);
        double localY = side ? (32.0 + 0.83) : 32.0;
        double localZ = side ? (-14.0 - 9.33) : (-14 - 2);
        localX /= 16.0;
        localY /= 16.0;
        localZ /= 16.0;

        double radians = Math.toRadians(this.getYRot() - 180);

        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        double worldX = this.getX() + (localX * cos - localZ * sin);
        double worldZ = this.getZ() + (localX * sin + localZ * cos);
        double worldY = this.getY() + localY;

        return new Vec3(worldX, worldY, worldZ);
    }

    private void cloudTick(ServerLevel world) {
        if (this.stinkCloud == null && this.stinkCloudId != null) {
            Entity e = world.getEntity(this.stinkCloudId);
            if (e instanceof AreaEffectCloud cloud) {
                this.stinkCloud = cloud;
            }
        }
        if (this.tickCount == this.cloudRemoveTick) {
            if (this.stinkCloud != null) {
                this.stinkCloud.discard();
            }
            this.stinkCloud = null;
            this.stinkCloudId = null;
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        if (this.killed) {
            player.connection.send(new ClientboundEntityEventPacket(this, STATUS_DEATH));
        }
        else {
            this.bossBar.addPlayer(player);
        }
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossBar.removePlayer(player);
    }

    public static AttributeSupplier.Builder createZmysioAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE)
                .add(Attributes.ARMOR, ARMOR)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    public int getInvulnerableTimer() {
        return this.entityData.get(INVUL_TIMER);
    }

    public void setInvulTimer(int ticks) {
        this.entityData.set(INVUL_TIMER, ticks);
    }

    public int getTrackedEntityId() {
        return this.entityData.<Integer>get(TRACKED_ENTITY_ID);
    }

    public void setTrackedEntityId(int id) {
        this.entityData.set(TRACKED_ENTITY_ID, id);
    }

    private void setArmored() {
        this.entityData.set(ARMORED, true);
    }

    public boolean isArmored() {
        return this.entityData.get(ARMORED);
    }

    @Override
    public boolean addEffect(MobEffectInstance effect, @Nullable Entity source) {
        return false;
    }

    @Override
    protected boolean canRide(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortal(boolean allowVehicles) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {

    }

    @Override
    public void makeStuckInBlock(BlockState state, Vec3 multiplier) {

    }

    enum AttackType {
        MILK_PROJECTILE,
        SPIN,
        GAS
    }

    static class ZmysioProjectileScheduler {
        private final Level world;
        private final HashMap<Long, MilkProjectileEntity> scheduledProjectiles = new HashMap<>();
        private long currentTick = 0;

        public ZmysioProjectileScheduler(Level world) {
            this.world = world;
        }

        public void schedule(MilkProjectileEntity projectile, long delayTicks) {
            scheduledProjectiles.put(currentTick + delayTicks, projectile);
        }

        public void tick() {
            currentTick++;

            if (scheduledProjectiles.containsKey(currentTick)) {
                this.world.addFreshEntity(scheduledProjectiles.get(currentTick));
                scheduledProjectiles.remove(currentTick);
            }
        }
    }
}
