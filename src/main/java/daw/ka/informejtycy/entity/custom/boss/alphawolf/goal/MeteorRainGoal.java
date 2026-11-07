package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfPhase;
import daw.ka.informejtycy.entity.custom.projectile.MeteorProjectileEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class MeteorRainGoal extends Goal {
	private static final int COOLDOWN = 160;
	private static final int WINDUP_TICKS = 20;
	private static final int METEOR_COUNT = 8;
	private static final int METEOR_INTERVAL = 6;
	private static final int FLIGHT_TICKS = 25;
	private static final float SPREAD = 4.0f;
	private static final float START_HEIGHT = 22.0f;
	private static final float START_DISTANCE = 8.0f;

	private final AlphaWolfEntity wolf;
	private int readyTick;
	private int ticks;
	private int launched;

	public MeteorRainGoal(AlphaWolfEntity wolf) {
		this.wolf = wolf;
		setFlags(EnumSet.noneOf(Flag.class));
	}

	@Override
	public boolean canUse() {
		return wolf.getPhase() == AlphaWolfPhase.SITTING && wolf.tickCount >= readyTick && wolf.getTarget() != null;
	}

	@Override
	public boolean canContinueToUse() {
		return wolf.getPhase() == AlphaWolfPhase.SITTING && launched < METEOR_COUNT;
	}

	@Override
	public void start() {
		ticks = 0;
		launched = 0;
		wolf.playSound(SoundEvents.EVOKER_PREPARE_SUMMON, 3.0f, 0.5f);
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
		ticks++;
		LivingEntity target = wolf.getTarget();
		if (target == null || ticks < WINDUP_TICKS || (ticks - WINDUP_TICKS) % METEOR_INTERVAL != 0) return;

		RandomSource random = wolf.getRandom();
		float offset = launched == 0 ? 0 : random.nextFloat() * SPREAD;
		float angle = random.nextFloat() * Mth.TWO_PI;
		Vec3 landing = target.position().add(Mth.cos(angle) * offset, 0, Mth.sin(angle) * offset);

		float from = random.nextFloat() * Mth.TWO_PI;
		Vec3 start = landing.add(Mth.cos(from) * START_DISTANCE, START_HEIGHT, Mth.sin(from) * START_DISTANCE);
		MeteorProjectileEntity meteor = new MeteorProjectileEntity(wolf.level(), wolf, start, landing);
		wolf.launch(meteor, landing, START_DISTANCE / FLIGHT_TICKS, 0.0);
		meteor.playSound(SoundEvents.BLAZE_SHOOT, 2.0f, 0.5f);
		launched++;
	}
}
