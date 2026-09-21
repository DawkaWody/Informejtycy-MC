package daw.ka.informejtycy.potion;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.potion.effect.CustomEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.core.Holder;

public class CustomPotions {
	public static Holder<Potion> AURA_POTION;

	public static void registerAll() {
		AURA_POTION = InformejtycyRegistry.registerPotion("aura_potion",
				new Potion("Aura", new MobEffectInstance(CustomEffects.AURA, 3600, 0, false, true)));
	}
}
