package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrabThrowGoal extends ActionGoal {
	private static final int COOLDOWN = 240;
	private static final float START_RANGE = 5.5f;
	private static final float GRAB_RANGE = 7.0f;
	private static final float GRAB_DAMAGE = 4.0f;
	private static final int THROW_TICK = 20;
	private static final float GROUP_RADIUS = 6.0f;
	private static final float RANDOM_MIN_DISTANCE = 10.0f;
	private static final float RANDOM_MAX_DISTANCE = 16.0f;
	private static final int FLIGHT_TICKS = 22;
	private static final Vec3[] HAND_PATH = {
			new Vec3(-0.65, 1.09, 0.93),
			new Vec3(-0.65, 1.29, 0.92),
			new Vec3(-0.63, 1.60, 0.85),
			new Vec3(-0.57, 1.88, 0.68),
			new Vec3(-0.50, 2.10, 0.43),
			new Vec3(-0.42, 2.22, 0.12),
			new Vec3(-0.34, 2.24, -0.20),
			new Vec3(-0.31, 2.22, -0.30),
			new Vec3(-0.29, 2.19, -0.39),
			new Vec3(-0.25, 2.15, -0.47),
			new Vec3(-0.21, 2.11, -0.54),
			new Vec3(-0.17, 2.07, -0.61),
			new Vec3(-0.13, 2.02, -0.67),
			new Vec3(-0.44, 2.05, 0.56)
	};

	private @Nullable Player grabbed;

	public GrabThrowGoal(AlphaWolfEntity wolf) {
		super(wolf, AlphaWolfAction.GRAB_THROW, COOLDOWN);
	}

	@Override
	protected boolean canStart(LivingEntity target) {
		return target instanceof Player && isWithin(target, START_RANGE);
	}

	@Override
	protected void hit(LivingEntity target) {
		if (!(target instanceof Player player) || !isWithin(player, GRAB_RANGE)) return;
		grabbed = player;
		player.hurtServer(getServerLevel(wolf), wolf.damageSources().mobAttack(wolf), GRAB_DAMAGE);
	}

	@Override
	public void tick() {
		super.tick();
		if (grabbed == null) return;
		if (!grabbed.isAlive()) {
			grabbed = null;
			return;
		}

		int step = ticks - AlphaWolfAction.GRAB_THROW.hitTick;
		if (step >= 0 && step < HAND_PATH.length) {
			Vec3 hand = wolf.releasePosition(HAND_PATH[step]);
			grabbed.teleportTo(hand.x, hand.y - grabbed.getBbHeight() / 2, hand.z);
			grabbed.setDeltaMovement(Vec3.ZERO);
			grabbed.resetFallDistance();
		}
		if (ticks == THROW_TICK) throwGrabbed();
	}

	@Override
	public void stop() {
		grabbed = null;
		super.stop();
	}

	private void throwGrabbed() {
		Player player = grabbed;
		grabbed = null;
		if (player == null) return;

		Vec3 landing = groupCenter(player);
		if (landing == null) landing = randomSpot();

		double horizontalReach = (1 - Math.pow(0.91, FLIGHT_TICKS)) / 0.09;
		double verticalReach = 0, fallen = 0, fallSpeed = 0;
		for (int i = 0; i < FLIGHT_TICKS; i++) {
			verticalReach += Math.pow(0.98, i);
			fallSpeed = (fallSpeed + player.getGravity()) * 0.98;
			fallen += fallSpeed;
		}
		Vec3 distance = landing.subtract(player.position());
		player.setDeltaMovement(distance.x / horizontalReach, (distance.y + fallen) / verticalReach, distance.z / horizontalReach);
		player.needsSync = true;
		wolf.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 2.0f, 0.4f);
	}

	private @Nullable Vec3 groupCenter(Player thrown) {
		List<Player> others = wolf.nearbyTargets().stream().filter(other -> other != thrown).toList();
		Player best = null;
		int bestCount = 0;
		for (Player candidate : others) {
			int count = (int) others.stream().filter(other -> other.distanceToSqr(candidate) <= GROUP_RADIUS * GROUP_RADIUS).count();
			if (count > bestCount) {
				best = candidate;
				bestCount = count;
			}
		}
		if (best == null) return null;

		Player center = best;
		List<Player> group = others.stream().filter(other -> other.distanceToSqr(center) <= GROUP_RADIUS * GROUP_RADIUS).toList();
		Vec3 sum = Vec3.ZERO;
		for (Player player : group) sum = sum.add(player.position());
		return sum.scale(1.0 / group.size());
	}

	private Vec3 randomSpot() {
		RandomSource random = wolf.getRandom();
		float angle = random.nextFloat() * Mth.TWO_PI;
		float distance = Mth.lerp(random.nextFloat(), RANDOM_MIN_DISTANCE, RANDOM_MAX_DISTANCE);
		return wolf.position().add(Mth.cos(angle) * distance, 0, Mth.sin(angle) * distance);
	}
}
