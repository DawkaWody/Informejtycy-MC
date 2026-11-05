package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public abstract class ActionGoal extends Goal {
	protected final AlphaWolfEntity wolf;
	private final AlphaWolfAction action;
	private final int cooldown;
	private int readyTick;
	protected int ticks;

	protected ActionGoal(AlphaWolfEntity wolf, AlphaWolfAction action, int cooldown) {
		this.wolf = wolf;
		this.action = action;
		this.cooldown = cooldown;
		setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	protected abstract boolean canStart(LivingEntity target);

	protected abstract void hit(LivingEntity target);

	@Override
	public boolean canUse() {
		LivingEntity target = wolf.getTarget();
		return wolf.getPhase().canAct() && wolf.tickCount >= readyTick && target != null && canStart(target);
	}

	@Override
	public boolean canContinueToUse() {
		return wolf.getPhase().canAct() && ticks < action.ticks;
	}

	@Override
	public void start() {
		ticks = 0;
		wolf.getNavigation().stop();
		wolf.playAction(action);
	}

	@Override
	public void stop() {
		readyTick = wolf.tickCount + cooldown;
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		ticks++;
		LivingEntity target = wolf.getTarget();
		if (target == null) return;

		wolf.lookAt(target, 10.0f, 30.0f);
		wolf.yBodyRot = wolf.getYRot();
		if (ticks == action.hitTick) hit(target);
	}

	protected boolean isWithin(LivingEntity target, float range) {
		return wolf.distanceToSqr(target) <= range * range;
	}
}
