package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfPhase;
import daw.ka.informejtycy.entity.custom.projectile.CigaretteProjectileEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ThrowCigaretteGoal extends ActionGoal {
	private static final int COOLDOWN = 60;
	private static final float MIN_RANGE = 6.0f;
	private static final float SPEED = 1.6f;
	private static final int COUNT = 3;
	private static final float SPREAD_DEGREES = 10.0f;
	private static final Vec3 RELEASE_OFFSET = new Vec3(-0.58, 1.26, 0.85);

	public ThrowCigaretteGoal(AlphaWolfEntity wolf) {
		super(wolf, AlphaWolfAction.THROW_SHURIKEN, COOLDOWN);
	}

	@Override
	protected boolean canStart(LivingEntity target) {
		return wolf.getPhase() == AlphaWolfPhase.UNMASKED && wolf.hasLineOfSight(target) && !isWithin(target, MIN_RANGE);
	}

	@Override
	protected void hit(LivingEntity target) {
		Vec3 start = wolf.releasePosition(RELEASE_OFFSET);
		Vec3 aim = target.getBoundingBox().getCenter().subtract(start);
		for (int i = 0; i < COUNT; i++) {
			float spread = (i - (COUNT - 1) / 2f) * SPREAD_DEGREES * Mth.DEG_TO_RAD;
			CigaretteProjectileEntity cigarette = new CigaretteProjectileEntity(wolf.level(), wolf, start);
			wolf.launch(cigarette, start.add(aim.yRot(spread)), SPEED, CigaretteProjectileEntity.GRAVITY);
		}
		wolf.playSound(SoundEvents.WITCH_THROW, 2.0f, 0.6f);
	}
}
