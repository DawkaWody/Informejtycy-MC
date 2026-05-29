package daw.ka.informejtycy.potion.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.List;

public class AuraEffect extends StatusEffect {
	private static final double RADIUS = 4.0f;

	public AuraEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		Box bounding = new Box(x - RADIUS, y - RADIUS, z - RADIUS, x + RADIUS, y + RADIUS, z + RADIUS);
		List<LivingEntity> nearbyEntities = world.getEntitiesByClass(LivingEntity.class, bounding,
				e -> e != entity && e.isAlive());

		for (LivingEntity nearbyEntity : nearbyEntities) {
			if (!nearbyEntity.hasStatusEffect(StatusEffects.SLOWNESS)) {
				nearbyEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,
						100, amplifier, false, true));
			}
		}

		return super.applyUpdateEffect(world, entity, amplifier);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return duration % 20 == 0;
	}
}
