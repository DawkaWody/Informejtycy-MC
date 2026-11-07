package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfPhase;
import daw.ka.informejtycy.entity.custom.projectile.RockProjectileEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;

public class ThrowRockGoal extends ActionGoal {
	private static final int COOLDOWN = 100;
	private static final float MIN_RANGE = 8.0f;
	private static final float SPEED = 1.4f;

	private static final Vec3 RELEASE_OFFSET = new Vec3(-0.35, 1.62, 1.05);

	public ThrowRockGoal(AlphaWolfEntity wolf) {
		super(wolf, AlphaWolfAction.THROW_ROCK, COOLDOWN);
	}

	@Override
	protected LivingEntity chooseTarget() {
		return wolf.nearbyTargets().stream()
				.filter(player -> !isWithin(player, MIN_RANGE) && wolf.hasLineOfSight(player))
				.max(Comparator.comparingDouble(wolf::distanceToSqr)).orElse(null);
	}

	@Override
	protected boolean canStart(LivingEntity target) {
		return wolf.getPhase() == AlphaWolfPhase.STANDING && wolf.hasLineOfSight(target) && !isWithin(target, MIN_RANGE);
	}

	@Override
	protected void hit(LivingEntity target) {
		RockProjectileEntity rock = new RockProjectileEntity(wolf.level(), wolf, wolf.releasePosition(RELEASE_OFFSET));
		wolf.launch(rock, target.getBoundingBox().getCenter(), SPEED, RockProjectileEntity.GRAVITY);
		wolf.playSound(SoundEvents.WIND_CHARGE_THROW, 2.0f, 0.5f);
	}
}
