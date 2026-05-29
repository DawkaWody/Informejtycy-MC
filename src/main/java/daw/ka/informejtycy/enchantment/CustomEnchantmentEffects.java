package daw.ka.informejtycy.enchantment;

import com.mojang.serialization.MapCodec;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.enchantment.custom.ThunderstruckEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;

public class CustomEnchantmentEffects {
	public static MapCodec<? extends EnchantmentEntityEffect> THUNDERSTRUCK;
	public static void registerAll() {
		THUNDERSTRUCK = InformejtycyRegistry.registerEntityEffect("thunderstruck", ThunderstruckEnchantmentEffect.CODEC);
	}
}
