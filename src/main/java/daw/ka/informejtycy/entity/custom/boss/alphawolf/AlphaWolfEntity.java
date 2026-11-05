package daw.ka.informejtycy.entity.custom.boss.alphawolf;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.ChaseTargetGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.GroundPunchGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.SlashGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.ThrowCigaretteGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.ThrowRockGoal;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class AlphaWolfEntity extends Monster {
	private static final EntityDataAccessor<Integer> PHASE =
			SynchedEntityData.defineId(AlphaWolfEntity.class, EntityDataSerializers.INT);

	public static final int AWAKEN_TICKS_PER_FRAME = 4;
	private static final float ATTACK_RADIUS = 25.0f;
	private static final float SITTING_ENDS_AT = 2f / 3f;
	private static final float STANDING_ENDS_AT = 1f / 3f;

	private static final byte EVENT_ACTION_BASE = 70;

	private final ServerBossEvent bossBar = new ServerBossEvent(UUID.randomUUID(), this.getDisplayName(),
			BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_6);
	private final TargetingConditions.Selector CAN_ATTACK_PREDICATE = (entity, _) -> entity instanceof Player
			&& distanceToSqr(entity) <= ATTACK_RADIUS * ATTACK_RADIUS;

	private final Map<AlphaWolfAction, AnimationState> animationStates = new EnumMap<>(AlphaWolfAction.class);
	private final Map<AlphaWolfAction, Integer> stopTicks = new EnumMap<>(AlphaWolfAction.class);
	private int phaseTicks;
	private AlphaWolfShockwave shockwave;
	private DamageSource killer;
	private UUID killerId;

	public AlphaWolfEntity(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
		this.setPersistenceRequired();
		for (AlphaWolfAction action : AlphaWolfAction.values()) animationStates.put(action, new AnimationState());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new GroundPunchGoal(this));
		this.goalSelector.addGoal(2, new ThrowRockGoal(this));
		this.goalSelector.addGoal(2, new ThrowCigaretteGoal(this));
		this.goalSelector.addGoal(3, new SlashGoal(this));
		this.goalSelector.addGoal(4, new ChaseTargetGoal(this, 1.0));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, ATTACK_RADIUS));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 0, false, false, CAN_ATTACK_PREDICATE));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(PHASE, AlphaWolfPhase.STATUE.ordinal());
	}

	@Override
	public void onSyncedDataUpdated(@NonNull EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (!PHASE.equals(accessor)) return;

		phaseTicks = 0;
		// set on both sides so the scale changes on the same frame as the model
		getAttribute(Attributes.SCALE).setBaseValue(getPhase().scale);
		refreshDimensions();
		animationStates.values().forEach(AnimationState::stop);
		stopTicks.clear();
		if (getPhase() == AlphaWolfPhase.MASK_OFF) startAnimation(AlphaWolfAction.MASK_OFF);
		if (getPhase() == AlphaWolfPhase.DEAD) startAnimation(AlphaWolfAction.DEATH);
		if (!level().isClientSide()) updateControlFlags();
	}

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putInt("Phase", entityData.get(PHASE));
		if (getPhase() == AlphaWolfPhase.DEAD && getKillerId() != null) {
			output.putString("KillerId", getKillerId().toString());
		}
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput input) {
		super.readAdditionalSaveData(input);
		setPhase(AlphaWolfPhase.values()[input.getIntOr("Phase", 0)]);
		if (hasCustomName()) {
			bossBar.setName(getDisplayName());
		}

		killerId = null;
		String killerIdStr = input.getStringOr("KillerId", "");
		if (!killerIdStr.isEmpty()) killerId = UUID.fromString(killerIdStr);
	}

	@Override
	public void setCustomName(@Nullable Component name) {
		super.setCustomName(name);
		bossBar.setName(getDisplayName());
	}

	@Override
	protected void customServerAiStep(@NonNull ServerLevel level) {
		super.customServerAiStep(level);

		if (shockwave != null && shockwave.tick(level)) shockwave = null;

		AlphaWolfPhase phase = getPhase();
		if (phase.ticks > 0 && phaseTicks >= phase.ticks) {
			if (phase == AlphaWolfPhase.DEAD) {
				die(level);
				return;
			}
			setPhase(switch (phase) {
				case AWAKENING -> AlphaWolfPhase.SITTING;
				case RISING_1 -> AlphaWolfPhase.RISING_2;
				case RISING_2 -> AlphaWolfPhase.STANDING;
				case MASK_OFF -> AlphaWolfPhase.UNMASKED;
				default -> phase;
			});
		}

		bossBar.setVisible(phase != AlphaWolfPhase.STATUE);
		bossBar.setProgress(getHealth() / getMaxHealth());
	}

	@Override
	public void tick() {
		super.tick();
		phaseTicks++;

		if (level().isClientSide()) {
			// vanilla holds the last frame of a finished animation until it is stopped
			stopTicks.entrySet().removeIf(entry -> {
				if (tickCount < entry.getValue()) return false;
				animationStates.get(entry.getKey()).stop();
				return true;
			});
			boolean moving = getPhase().canAct() && getDeltaMovement().horizontalDistanceSqr() > 1.0E-6;
			animationStates.get(AlphaWolfAction.WALK).animateWhen(moving, tickCount);
		}
	}

	@Override
	public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource source, float amount) {
		AlphaWolfPhase phase = getPhase();
		if (phase == AlphaWolfPhase.DEAD) return false;

		boolean forced = source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
		if (phase.invulnerable && !forced) return false;

		if (forced || (phase == AlphaWolfPhase.UNMASKED && getHealth() - amount <= 0)) {
			killer = source;
			initiateDeath();
			return false;
		}

		if (phase != AlphaWolfPhase.UNMASKED) amount = Math.min(amount, getHealth() - 1f);
		boolean hurt = super.hurtServer(level, source, amount);

		float health = getHealth() / getMaxHealth();
		if (phase == AlphaWolfPhase.SITTING && health <= SITTING_ENDS_AT) setPhase(AlphaWolfPhase.RISING_1);
		if (phase == AlphaWolfPhase.STANDING && health <= STANDING_ENDS_AT) setPhase(AlphaWolfPhase.MASK_OFF);
		return hurt;
	}

	@Override
	protected @NonNull InteractionResult mobInteract(@NonNull Player player, @NonNull InteractionHand hand) {
		if (getPhase() != AlphaWolfPhase.STATUE) return super.mobInteract(player, hand);
		if (!level().isClientSide()) setPhase(AlphaWolfPhase.AWAKENING);
		return InteractionResult.SUCCESS;
	}

	@Override
	public void handleEntityEvent(byte event) {
		int action = event - EVENT_ACTION_BASE;
		if (action >= 0 && action < AlphaWolfAction.values().length) {
			startAnimation(AlphaWolfAction.values()[action]);
		} else {
			super.handleEntityEvent(event);
		}
	}

	private void initiateDeath() {
		bossBar.removeAllPlayers();
		setPhase(AlphaWolfPhase.DEAD);
	}

	private void die(ServerLevel level) {
		super.hurtServer(level, killer != null ? killer : loadedKiller(level), getMaxHealth());
	}

	private DamageSource loadedKiller(ServerLevel level) {
		Entity attacker = killerId != null ? level.getEntity(killerId) : null;
		if (attacker instanceof Player player) {
			return level.damageSources().playerAttack(player);
		}
		if (attacker instanceof LivingEntity living) {
			return level.damageSources().mobAttack(living);
		}
		return level.damageSources().generic();
	}

	private @Nullable UUID getKillerId() {
		if (killer != null && killer.getEntity() != null) {
			return killer.getEntity().getUUID();
		}
		return killerId;
	}

	@Override
	protected void tickDeath() {
		// the death animation already played before the killing blow
		if (!level().isClientSide()) remove(RemovalReason.KILLED);
	}

	/** Server side. Plays an attack on every client; do the gameplay effect action.hitTick ticks later. */
	public void playAction(AlphaWolfAction action) {
		level().broadcastEntityEvent(this, (byte) (EVENT_ACTION_BASE + action.ordinal()));
	}

	private void startAnimation(AlphaWolfAction action) {
		animationStates.get(action).start(tickCount);
		if (action.ticks > 0) stopTicks.put(action, tickCount + action.ticks);
	}

	/** Where a point given at scale 1 (x left, y up, z forward) is in the world right now. */
	public Vec3 releasePosition(Vec3 offset) {
		return position().add(offset.scale(getScale()).yRot(-yBodyRot * Mth.DEG_TO_RAD));
	}

	/** Centres the projectile on its position and sends it in an arc that lands on aim. */
	public void launch(Projectile projectile, Vec3 aim, float speed, double gravity) {
		projectile.setPos(projectile.position().subtract(0, projectile.getBbHeight() / 2, 0));
		Vec3 distance = aim.subtract(projectile.getBoundingBox().getCenter());
		double flightTicks = Math.max(distance.horizontalDistance() / speed, 1);
		projectile.setDeltaMovement(distance.x / flightTicks,
				distance.y / flightTicks + gravity * flightTicks / 2,
				distance.z / flightTicks);
		level().addFreshEntity(projectile);
	}

	public void startShockwave() {
		shockwave = new AlphaWolfShockwave(this);
	}

	@Override
	public void startSeenByPlayer(@NonNull ServerPlayer player) {
		super.startSeenByPlayer(player);
		if (getPhase() != AlphaWolfPhase.DEAD) {
			bossBar.addPlayer(player);
		}
	}

	@Override
	public void stopSeenByPlayer(@NonNull ServerPlayer player) {
		super.stopSeenByPlayer(player);
		bossBar.removePlayer(player);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 300.0)
				.add(Attributes.MOVEMENT_SPEED, 0.25)
				.add(Attributes.ATTACK_DAMAGE, 7.0)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
				.add(Attributes.FOLLOW_RANGE, ATTACK_RADIUS)
				.add(Attributes.SCALE, AlphaWolfPhase.STATUE.scale)
				.add(Attributes.STEP_HEIGHT, 2.0);
	}

	public AlphaWolfPhase getPhase() {
		return AlphaWolfPhase.values()[entityData.get(PHASE)];
	}

	public void setPhase(AlphaWolfPhase phase) {
		entityData.set(PHASE, phase.ordinal());
	}

	public int getPhaseTicks() {
		return phaseTicks;
	}

	public AnimationState animationState(AlphaWolfAction action) {
		return animationStates.get(action);
	}

	@Override
	protected void updateControlFlags() {
		super.updateControlFlags();
		// outside the fight stages the goals stay off, but the sitting wolf still picks targets
		AlphaWolfPhase phase = getPhase();
		goalSelector.setControlFlag(Goal.Flag.MOVE, phase.canAct());
		goalSelector.setControlFlag(Goal.Flag.JUMP, phase.canAct());
		goalSelector.setControlFlag(Goal.Flag.LOOK, phase.canAct());
		targetSelector.setControlFlag(Goal.Flag.TARGET, !phase.invulnerable);
	}

	@Override
	public @NonNull EntityDimensions getDefaultDimensions(@NonNull Pose pose) {
		AlphaWolfPhase phase = getPhase();
		return EntityDimensions.scalable(phase.width, phase.height);
	}

	@Override
	public boolean canUsePortal(boolean allowVehicles) {
		return false;
	}

	@Override
	public boolean isPushable() {
		return getPhase().canAct() && super.isPushable();
	}
}
