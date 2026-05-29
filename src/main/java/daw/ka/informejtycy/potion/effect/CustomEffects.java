package daw.ka.informejtycy.potion.effect;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.potion.effect.custom.AuraEffect;
import daw.ka.informejtycy.potion.effect.custom.SneakinessEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.entry.RegistryEntry;

public class CustomEffects {
	public static RegistryEntry<StatusEffect> AURA;
	public static RegistryEntry<StatusEffect> SNEAKINESS;

	public static void registerAll() {
		AURA = InformejtycyRegistry.registerStatusEffect("aura",
				new AuraEffect(StatusEffectCategory.HARMFUL, 0x3bd3f7));
		SNEAKINESS = InformejtycyRegistry.registerStatusEffect("sneakiness",
				new SneakinessEffect(StatusEffectCategory.BENEFICIAL, 0x02386d));
	}
}
