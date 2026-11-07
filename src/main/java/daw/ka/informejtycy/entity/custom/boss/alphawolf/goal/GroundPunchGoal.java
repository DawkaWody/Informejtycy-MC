package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfPhase;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;

public class GroundPunchGoal extends ActionGoal {
	private static final int COOLDOWN = 120;
	private static final float RANGE = 10.0f;
	private static final int UNMASKED_WAVES = 3;
	private static final int WAVE_GAP_TICKS = 14;

	public GroundPunchGoal(AlphaWolfEntity wolf) {
		super(wolf, AlphaWolfAction.GROUND_PUNCH, COOLDOWN);
	}

	@Override
	protected boolean canStart(LivingEntity target) {
		return isWithin(target, RANGE);
	}

	@Override
	protected void hit(LivingEntity target) {
		int waves = wolf.getPhase() == AlphaWolfPhase.UNMASKED ? UNMASKED_WAVES : 1;
		for (int i = 0; i < waves; i++) wolf.startShockwave(i * WAVE_GAP_TICKS);
		wolf.playSound(SoundEvents.MACE_SMASH_GROUND_HEAVY, 3.0f, 0.6f);
	}
}
