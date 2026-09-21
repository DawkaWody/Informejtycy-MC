package daw.ka.informejtycy.potion.effect.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AuraEffect extends MobEffect {
	private static final double RADIUS = 4.0f;

	public AuraEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		AABB bounding = new AABB(x - RADIUS, y - RADIUS, z - RADIUS, x + RADIUS, y + RADIUS, z + RADIUS);
		List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class, bounding,
				e -> e != entity && e.isAlive());

		for (LivingEntity nearbyEntity : nearbyEntities) {
			if (!nearbyEntity.hasEffect(MobEffects.SLOWNESS)) {
				nearbyEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS,
						100, amplifier, false, true));
			}
		}

		return super.applyEffectTick(world, entity, amplifier);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration % 20 == 0;
	}
}
