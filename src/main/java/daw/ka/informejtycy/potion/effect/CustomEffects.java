package daw.ka.informejtycy.potion.effect;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.potion.effect.custom.AuraEffect;
import daw.ka.informejtycy.potion.effect.custom.SneakinessEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.core.Holder;

public class CustomEffects {
	public static Holder<MobEffect> AURA;
	public static Holder<MobEffect> SNEAKINESS;

	public static void registerAll() {
		AURA = InformejtycyRegistry.registerStatusEffect("aura",
				new AuraEffect(MobEffectCategory.BENEFICIAL, 0x3bd3f7));
		SNEAKINESS = InformejtycyRegistry.registerStatusEffect("sneakiness",
				new SneakinessEffect(MobEffectCategory.BENEFICIAL, 0x02386d));
	}
}
