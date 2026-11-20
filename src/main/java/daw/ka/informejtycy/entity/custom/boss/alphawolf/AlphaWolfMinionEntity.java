package daw.ka.informejtycy.entity.custom.boss.alphawolf;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class AlphaWolfMinionEntity extends Wolf {
	private static final DustParticleOptions MARKER = new DustParticleOptions(0xFF1010, 1.0f);
	private static final float MARKER_HEIGHT = 0.6f;
	private static final float MARKER_SPREAD = 0.15f;

	private @Nullable EntityReference<AlphaWolfEntity> leader;

	public AlphaWolfMinionEntity(EntityType<? extends Wolf> entityType, Level level) {
		super(entityType, level);
		this.setPersistenceRequired();
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.removeAllGoals(goal -> goal instanceof PanicGoal);
		this.targetSelector.removeAllGoals(_ -> true);
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
	}

	@Override
	protected void addAdditionalSaveData(@NonNull ValueOutput output) {
		super.addAdditionalSaveData(output);
		EntityReference.store(leader, output, "Leader");
	}

	@Override
	protected void readAdditionalSaveData(@NonNull ValueInput input) {
		super.readAdditionalSaveData(input);
		leader = EntityReference.read(input, "Leader");
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide()) {
			level().addParticle(MARKER,
					getX() + (random.nextFloat() - 0.5f) * 2 * MARKER_SPREAD,
					getY() + getBbHeight() + MARKER_HEIGHT + random.nextFloat() * MARKER_SPREAD,
					getZ() + (random.nextFloat() - 0.5f) * 2 * MARKER_SPREAD,
					0, 0, 0);
		}
	}

	@Override
	public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource source, float amount) {
		if (source.getEntity() instanceof AlphaWolfEntity) return false;
		return super.hurtServer(level, source, amount);
	}

	@Override
	public boolean shouldShowName() {
		return true;
	}

	@Override
	public @NonNull InteractionResult mobInteract(@NonNull Player player, @NonNull InteractionHand hand) {
		return InteractionResult.PASS;
	}

	@Override
	public @Nullable Wolf getBreedOffspring(@NonNull ServerLevel level, @NonNull AgeableMob partner) {
		return null;
	}

	public void setLeader(AlphaWolfEntity wolf) {
		leader = EntityReference.of(wolf);
	}

	public boolean isLedBy(AlphaWolfEntity wolf) {
		return leader != null && leader.matches(wolf);
	}

	public void growl() {
        playSound(getAmbientSound(), 1.5f, 0.8f);
	}
}
