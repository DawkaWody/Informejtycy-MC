package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class KickGoal extends ActionGoal {
	private static final int COOLDOWN = 80;
	private static final float START_RANGE = 5.5f;
	private static final float HIT_RANGE = 6.5f;
	private static final float DAMAGE = 10.0f;
	private static final float KNOCKBACK = 2.0f;

	public KickGoal(AlphaWolfEntity wolf) {
		super(wolf, AlphaWolfAction.KICK, COOLDOWN);
	}

	@Override
	protected boolean canStart(LivingEntity target) {
		return isWithin(target, START_RANGE);
	}

	@Override
	protected void hit(LivingEntity target) {
		wolf.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 2.0f, 0.5f);
		if (!isWithin(target, HIT_RANGE)) return;

		if (target.hurtServer(getServerLevel(wolf), wolf.damageSources().mobAttack(wolf), DAMAGE)) {
			Vec3 away = target.position().subtract(wolf.position()).multiply(1, 0, 1).normalize().scale(KNOCKBACK);
			target.push(away.x, 0.6, away.z);
			target.needsSync = true;
		}
	}
}
