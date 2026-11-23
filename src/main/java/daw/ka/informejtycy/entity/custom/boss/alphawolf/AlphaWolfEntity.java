package daw.ka.informejtycy.entity.custom.boss.alphawolf;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.ChaseTargetGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.GrabThrowGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.GroundPunchGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.KickGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.MeteorRainGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.RoarGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.SlashGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.StealItemGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.SummonPackGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.ThrowCigaretteGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.ThreatTargetGoal;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.goal.ThrowRockGoal;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.sound.CustomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AlphaWolfEntity extends Monster {
	private static final EntityDataAccessor<Integer> PHASE =
			SynchedEntityData.defineId(AlphaWolfEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> SHIELDED =
			SynchedEntityData.defineId(AlphaWolfEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<ItemStack> STOLEN_ITEM =
			SynchedEntityData.defineId(AlphaWolfEntity.class, EntityDataSerializers.ITEM_STACK);

	public static final int AWAKEN_TICKS_PER_FRAME = 4;
	private static final float ATTACK_RADIUS = 25.0f;
	private static final float SITTING_ENDS_AT = 2f / 3f;
	private static final float STANDING_ENDS_AT = 1f / 3f;

	private static final Vec3 MASK_DROP_OFFSET = new Vec3(0, 0.2, 0.8);
	private static final Vec3 STOLEN_ITEM_OFFSET = new Vec3(-0.3, 0, 0.75);
	private static final int FRAME_SEARCH_RADIUS = 2;
	private static final int[] FRAME_SEARCH_HEIGHTS = {0, 1, -1, 2, -2, -3};

	private static final float MINION_SEARCH_RADIUS = 48.0f;
	private static final int MINION_CHECK_INTERVAL = 10;
	private static final int MINION_LOAD_TICKS = 40;

	private static final byte EVENT_ACTION_BASE = 70;

	private static final int VOICE_COOLDOWN = 140;
	private static final int VOICE_CHANCE = 60;

	private final ServerBossEvent bossBar = new ServerBossEvent(UUID.randomUUID(), this.getDisplayName(),
			BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_6);

	private final Map<AlphaWolfAction, AnimationState> animationStates = new EnumMap<>(AlphaWolfAction.class);
	private final Map<AlphaWolfAction, Integer> stopTicks = new EnumMap<>(AlphaWolfAction.class);
	private int phaseTicks;
	private final List<AlphaWolfShockwave> shockwaves = new ArrayList<>();
	private final AlphaWolfThreat threat = new AlphaWolfThreat();
	private DamageSource killer;
	private UUID killerId;
	private boolean carryingStolenItem;
	private int nextVoiceTick;

	public AlphaWolfEntity(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
		this.setPersistenceRequired();
		for (AlphaWolfAction action : AlphaWolfAction.values()) animationStates.put(action, new AnimationState());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeteorRainGoal(this));
		this.goalSelector.addGoal(1, new SummonPackGoal(this));
		this.goalSelector.addGoal(1, new GroundPunchGoal(this));
		this.goalSelector.addGoal(1, new RoarGoal(this));
		this.goalSelector.addGoal(2, new GrabThrowGoal(this));
		this.goalSelector.addGoal(2, new StealItemGoal(this));
		this.goalSelector.addGoal(2, new ThrowRockGoal(this));
		this.goalSelector.addGoal(2, new ThrowCigaretteGoal(this));
		this.goalSelector.addGoal(3, new KickGoal(this));
		this.goalSelector.addGoal(3, new SlashGoal(this));
		this.goalSelector.addGoal(4, new ChaseTargetGoal(this, 1.0));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, ATTACK_RADIUS));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new ThreatTargetGoal(this));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
		super.defineSynchedData(builder);
		builder.define(PHASE, AlphaWolfPhase.STATUE.ordinal());
		builder.define(SHIELDED, false);
		builder.define(STOLEN_ITEM, ItemStack.EMPTY);
	}

	@Override
	public void onSyncedDataUpdated(@NonNull EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (!PHASE.equals(accessor)) return;

		phaseTicks = 0;
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
		output.putBoolean("Shielded", isShielded());
		if (!getStolenItem().isEmpty()) output.store("StolenItem", ItemStack.CODEC, getStolenItem());
		if (getPhase() == AlphaWolfPhase.DEAD && getKillerId() != null) {
			output.putString("KillerId", getKillerId().toString());
		}
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput input) {
		super.readAdditionalSaveData(input);
		setPhase(AlphaWolfPhase.values()[input.getIntOr("Phase", 0)]);
		setShielded(input.getBooleanOr("Shielded", false));
		entityData.set(STOLEN_ITEM, input.read("StolenItem", ItemStack.CODEC).orElse(ItemStack.EMPTY));
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

		shockwaves.removeIf(shockwave -> shockwave.tick(level));
		threat.tick(tickCount);
		if (!carryingStolenItem && !getStolenItem().isEmpty()) placeStolenItem(level);
		if (isShielded() && tickCount > MINION_LOAD_TICKS && tickCount % MINION_CHECK_INTERVAL == 0 && minions().isEmpty()) {
			setShielded(false);
			playSound(SoundEvents.WOLF_ARMOR_BREAK.value(), 3.0f, 0.6f);
		}

		AlphaWolfPhase phase = getPhase();
		if (!phase.invulnerable && tickCount >= nextVoiceTick && random.nextInt(VOICE_CHANCE) == 0
				&& nearbyTargets().stream().anyMatch(this::hasLineOfSight)) {
			playSound(CustomSounds.WOLF_VOICE, 3.0f, 1.0f);
			nextVoiceTick = tickCount + VOICE_COOLDOWN;
		}

		if (phase.ticks > 0 && phaseTicks >= phase.ticks) {
			if (phase == AlphaWolfPhase.DEAD) {
				die(level);
				return;
			}
			setPhase(switch (phase) {
				case AWAKENING -> AlphaWolfPhase.SITTING;
				case RISING_1 -> AlphaWolfPhase.RISING_2;
				case RISING_2 -> AlphaWolfPhase.STANDING;
				case MASK_OFF -> {
					dropMask(level);
					yield AlphaWolfPhase.UNMASKED;
				}
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
		if ((phase.invulnerable || isShielded() || source.getEntity() == this) && !forced) return false;

		if (forced || (phase == AlphaWolfPhase.UNMASKED && getHealth() - amount <= 0)) {
			killer = source;
			initiateDeath(level);
			return false;
		}

		if (phase != AlphaWolfPhase.UNMASKED) amount = Math.min(amount, getHealth() - 1f);
		boolean hurt = super.hurtServer(level, source, amount);
		if (hurt && source.getEntity() instanceof Player player) threat.add(player, amount);

		float health = getHealth() / getMaxHealth();
		if (phase == AlphaWolfPhase.SITTING && health <= SITTING_ENDS_AT) {
			setPhase(AlphaWolfPhase.RISING_1);
			playSound(SoundEvents.RAVAGER_ROAR, 3.0f, 0.6f);
		}
		if (phase == AlphaWolfPhase.STANDING && health <= STANDING_ENDS_AT) {
			setPhase(AlphaWolfPhase.MASK_OFF);
			stopVoice(level);
			playSound(CustomSounds.WOLF_UNMASK, 3.0f, 1.0f);
		}
		return hurt;
	}

	@Override
	protected @NonNull InteractionResult mobInteract(@NonNull Player player, @NonNull InteractionHand hand) {
		if (getPhase() != AlphaWolfPhase.STATUE) return super.mobInteract(player, hand);
		if (!level().isClientSide()) {
			setPhase(AlphaWolfPhase.AWAKENING);
			playSound(SoundEvents.WARDEN_EMERGE, 3.0f, 0.8f);
		}
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

	private void initiateDeath(ServerLevel level) {
		bossBar.removeAllPlayers();
		setShielded(false);
		minions().forEach(minion -> minion.kill(level));
		setPhase(AlphaWolfPhase.DEAD);
		stopVoice(level);
		playSound(CustomSounds.WOLF_DIE, 3.0f, 1.0f);
	}

	private void stopVoice(ServerLevel level) {
		ClientboundStopSoundPacket packet = new ClientboundStopSoundPacket(CustomSounds.WOLF_VOICE.location(), getSoundSource());
		for (ServerPlayer player : level.players()) {
			if (distanceToSqr(player) <= ATTACK_RADIUS * ATTACK_RADIUS * 4) player.connection.send(packet);
		}
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
	public void remove(@NonNull RemovalReason reason) {
		if (level() instanceof ServerLevel level && reason.shouldDestroy() && !getStolenItem().isEmpty()) {
			dropStolenItem(level, getStolenItem(), position());
			setStolenItem(ItemStack.EMPTY);
		}
		super.remove(reason);
	}

	@Override
	protected void tickDeath() {
		if (!level().isClientSide()) remove(RemovalReason.KILLED);
	}

	public void playAction(AlphaWolfAction action) {
		level().broadcastEntityEvent(this, (byte) (EVENT_ACTION_BASE + action.ordinal()));
	}

	private void startAnimation(AlphaWolfAction action) {
		animationStates.get(action).start(tickCount);
		if (action.ticks > 0) stopTicks.put(action, tickCount + action.ticks);
	}

	public Vec3 releasePosition(Vec3 offset) {
		return position().add(offset.scale(getScale()).yRot(-yBodyRot * Mth.DEG_TO_RAD));
	}

	public void launch(Projectile projectile, Vec3 aim, float speed, double gravity) {
		projectile.setPos(projectile.position().subtract(0, projectile.getBbHeight() / 2, 0));
		Vec3 distance = aim.subtract(projectile.getBoundingBox().getCenter());
		double flightTicks = Math.max(distance.horizontalDistance() / speed, 1);
		projectile.setDeltaMovement(distance.x / flightTicks,
				distance.y / flightTicks + gravity * flightTicks / 2,
				distance.z / flightTicks);
		level().addFreshEntity(projectile);
	}

	public void startShockwave(int delayTicks) {
		shockwaves.add(new AlphaWolfShockwave(this, delayTicks));
	}

	public boolean isValidTarget(LivingEntity entity) {
		return entity instanceof Player && entity.isAlive() && canAttack(entity)
				&& distanceToSqr(entity) <= ATTACK_RADIUS * ATTACK_RADIUS;
	}

	public List<Player> nearbyTargets() {
		return nearbyTargets(ATTACK_RADIUS);
	}

	public List<Player> nearbyTargets(float radius) {
		return level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(radius),
				player -> isValidTarget(player) && distanceToSqr(player) <= radius * radius);
	}

	public void retarget() {
		setTarget(threat.pick(nearbyTargets(), this));
	}

	public void onAttackFinished(@Nullable LivingEntity target) {
		if (target != null) threat.attacked(target);
		retarget();
	}

	private List<AlphaWolfMinionEntity> minions() {
		return level().getEntitiesOfClass(AlphaWolfMinionEntity.class, getBoundingBox().inflate(MINION_SEARCH_RADIUS),
				minion -> minion.isAlive() && minion.isLedBy(this));
	}

	private void dropMask(ServerLevel level) {
		ItemEntity mask = spawnAtLocation(level, new ItemStack(CustomItems.ALPHA_WOLF_MASK),
				releasePosition(MASK_DROP_OFFSET).subtract(position()));
		if (mask != null) mask.setExtendedLifetime();
	}
	
	public void placeStolenItem(ServerLevel level) {
		ItemStack stolen = getStolenItem();
		if (stolen.isEmpty()) return;
		setStolenItem(ItemStack.EMPTY);

		Vec3 spot = releasePosition(STOLEN_ITEM_OFFSET);
		ItemFrame frame = findFrameSpot(level, BlockPos.containing(spot));
		if (frame == null) {
			dropStolenItem(level, stolen, spot);
			return;
		}

		ItemStack framed = stolen.split(1);
		framed.set(DataComponents.CUSTOM_NAME, Component.translatable("entity.informejtycy.alpha_wolf_boss.stolen_item"));
		frame.setItem(framed, false);
		level.addFreshEntity(frame);
		frame.playPlacementSound();
		if (!stolen.isEmpty()) dropStolenItem(level, stolen, frame.position());
	}

	private @Nullable ItemFrame findFrameSpot(ServerLevel level, BlockPos origin) {
		for (int radius = 0; radius <= FRAME_SEARCH_RADIUS; radius++) {
			for (int dx = -radius; dx <= radius; dx++) {
				for (int dz = -radius; dz <= radius; dz++) {
					if (Math.max(Math.abs(dx), Math.abs(dz)) != radius) continue;
					for (int dy : FRAME_SEARCH_HEIGHTS) {
						ItemFrame frame = new ItemFrame(level, origin.offset(dx, dy, dz), Direction.UP);
						if (frame.survives()) return frame;
					}
				}
			}
		}
		return null;
	}

	private void dropStolenItem(ServerLevel level, ItemStack stack, Vec3 at) {
		ItemEntity item = spawnAtLocation(level, stack, at.subtract(position()));
		if (item != null) item.setExtendedLifetime();
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

	public boolean isShielded() {
		return entityData.get(SHIELDED);
	}

	public void setShielded(boolean shielded) {
		entityData.set(SHIELDED, shielded);
	}

	public ItemStack getStolenItem() {
		return entityData.get(STOLEN_ITEM);
	}

	public void setStolenItem(ItemStack stack) {
		carryingStolenItem = !stack.isEmpty();
		entityData.set(STOLEN_ITEM, stack);
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
