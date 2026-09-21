package daw.ka.informejtycy.potion.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class SneakinessEffect extends MobEffect {
	public SneakinessEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
}
