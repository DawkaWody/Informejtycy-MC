package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;

public class SlashGoal extends ActionGoal {
	private static final int COOLDOWN = 8;
	private static final int SECOND_HIT_TICK = 19;
	private static final float START_RANGE = 5.0f;
	private static final float HIT_RANGE = 6.5f;

	public SlashGoal(AlphaWolfEntity wolf) {
		super(wolf, AlphaWolfAction.SLASH, COOLDOWN);
	}

	@Override
	protected boolean canStart(LivingEntity target) {
		return isWithin(target, START_RANGE);
	}

	@Override
	protected void hit(LivingEntity target) {
		wolf.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0f, 0.6f);
		if (isWithin(target, HIT_RANGE)) wolf.doHurtTarget(getServerLevel(wolf), target);
	}

	@Override
	public void tick() {
		super.tick();
		if (ticks == SECOND_HIT_TICK && target != null && target.isAlive()) hit(target);
	}
}
