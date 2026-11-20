package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.CustomEntities;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfMinionEntity;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfPhase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class SummonPackGoal extends Goal {
	private static final int COOLDOWN = 600;
	private static final int WINDUP_TICKS = 30;
	private static final int PACK_SIZE = 3;
	private static final float MIN_GAP = 1.5f;
	private static final float MAX_GAP = 4.5f;
	private static final int SPOT_ATTEMPTS = 10;
	private static final int SPOT_SEARCH_HEIGHT = 4;

	private final AlphaWolfEntity wolf;
	private int readyTick;
	private int ticks;

	public SummonPackGoal(AlphaWolfEntity wolf) {
		this.wolf = wolf;
		setFlags(EnumSet.noneOf(Flag.class));
	}

	@Override
	public boolean canUse() {
		return wolf.getPhase() == AlphaWolfPhase.SITTING && !wolf.isShielded()
				&& wolf.tickCount >= readyTick && wolf.getTarget() != null;
	}

	@Override
	public boolean canContinueToUse() {
		return wolf.getPhase() == AlphaWolfPhase.SITTING && ticks < WINDUP_TICKS;
	}

	@Override
	public void start() {
		ticks = 0;
		wolf.playSound(SoundEvents.EVOKER_PREPARE_SUMMON, 3.0f, 0.6f);
	}

	@Override
	public void stop() {
		readyTick = wolf.tickCount + COOLDOWN;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		if (++ticks != WINDUP_TICKS) return;

		ServerLevel level = getServerLevel(wolf);
		int spawned = 0;
		for (int i = 0; i < PACK_SIZE; i++) {
			BlockPos spot = findSpot(level);
			AlphaWolfMinionEntity minion = CustomEntities.ALPHA_WOLF_MINION.spawn(level, spot, EntitySpawnReason.MOB_SUMMONED);
			if (minion == null) continue;

			minion.setLeader(wolf);
			minion.setTarget(wolf.getTarget());
			minion.growl();
			level.sendParticles(ParticleTypes.LARGE_SMOKE, minion.getX(), minion.getY() + 0.5, minion.getZ(),
					15, 0.3, 0.4, 0.3, 0.02);
			spawned++;
		}
		if (spawned > 0) wolf.setShielded(true);
	}

	private BlockPos findSpot(ServerLevel level) {
		RandomSource random = wolf.getRandom();
		for (int attempt = 0; attempt < SPOT_ATTEMPTS; attempt++) {
			float angle = random.nextFloat() * Mth.TWO_PI;
			float distance = wolf.getBbWidth() / 2 + MIN_GAP + random.nextFloat() * (MAX_GAP - MIN_GAP);
			Vec3 at = wolf.position().add(Mth.cos(angle) * distance, 0, Mth.sin(angle) * distance);
			BlockPos spot = standingSpot(level, BlockPos.containing(at));
			if (spot != null) return spot;
		}
		return wolf.blockPosition();
	}

	private @Nullable BlockPos standingSpot(ServerLevel level, BlockPos around) {
		for (int dy = SPOT_SEARCH_HEIGHT; dy >= -SPOT_SEARCH_HEIGHT; dy--) {
			BlockPos pos = around.above(dy);
			BlockPos below = pos.below();
			if (level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)
					&& level.noCollision(CustomEntities.ALPHA_WOLF_MINION.getSpawnAABB(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5))) {
				return pos;
			}
		}
		return null;
	}
}
