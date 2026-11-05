package daw.ka.informejtycy.entity.custom.boss.alphawolf;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/** A ring from the ground punch that grows outward; anything standing on the ground when it passes is hit. */
public class AlphaWolfShockwave {
	private static final float SPEED = 0.6f;
	private static final float WIDTH = 1.5f;
	private static final float MAX_RADIUS = 12.0f;
	private static final float MAX_HEIGHT_DIFFERENCE = 3.0f;
	private static final float DAMAGE = 12.0f;
	private static final float KNOCKBACK = 1.4f;

	private final AlphaWolfEntity wolf;
	private final Vec3 origin;
	private final Set<UUID> struck = new HashSet<>();
	private float radius;

	public AlphaWolfShockwave(AlphaWolfEntity wolf) {
		this.wolf = wolf;
		this.origin = wolf.position();
	}

	/** @return true once the ring has reached its full size */
	public boolean tick(ServerLevel level) {
		radius += SPEED;
		spawnParticles(level);

		AABB box = new AABB(origin, origin).inflate(radius, MAX_HEIGHT_DIFFERENCE, radius);
		for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, box,
				entity -> entity.onGround() && wolf.canAttack(entity))) {
			Vec3 away = entity.position().subtract(origin).multiply(1, 0, 1);
			double distance = away.length();
			if (distance >= radius - WIDTH && distance <= radius && struck.add(entity.getUUID())) {
				entity.hurtServer(level, wolf.damageSources().mobAttack(wolf), DAMAGE);
				Vec3 knockback = away.normalize().scale(KNOCKBACK);
				entity.push(knockback.x, 0.5, knockback.z);
				entity.needsSync = true;
			}
		}
		return radius >= MAX_RADIUS;
	}

	private void spawnParticles(ServerLevel level) {
		int points = Math.max(12, (int) (Mth.TWO_PI * radius));
		for (int i = 0; i < points; i++) {
			float angle = Mth.TWO_PI * i / points;
			double x = origin.x + Mth.cos(angle) * radius;
			double z = origin.z + Mth.sin(angle) * radius;
			for (int y = 1; y >= -2; y--) {
				BlockPos pos = BlockPos.containing(x, origin.y + y, z);
				BlockState state = level.getBlockState(pos);
				if (!state.isAir() && level.getBlockState(pos.above()).isAir()) {
					level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
							x, pos.getY() + 1.1, z, 4, 0.2, 0.1, 0.2, 0.15);
					break;
				}
			}
		}
	}
}
