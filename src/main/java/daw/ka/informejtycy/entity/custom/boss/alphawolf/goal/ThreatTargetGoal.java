package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ThreatTargetGoal extends Goal {
	private static final int RETARGET_INTERVAL = 40;

	private final AlphaWolfEntity wolf;
	private int nextPick;

	public ThreatTargetGoal(AlphaWolfEntity wolf) {
		this.wolf = wolf;
		setFlags(EnumSet.of(Flag.TARGET));
	}

	@Override
	public boolean canUse() {
		LivingEntity target = wolf.getTarget();
		return wolf.tickCount >= nextPick || (target != null && !wolf.isValidTarget(target));
	}

	@Override
	public boolean canContinueToUse() {
		return false;
	}

	@Override
	public void start() {
		wolf.retarget();
		nextPick = wolf.tickCount + RETARGET_INTERVAL;
	}
}
