package daw.ka.informejtycy.enchantment;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.enchantment.custom.ThunderstruckEnchantmentEffect;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;

public class CustomEnchantments {
	public static final RegistryKey<Enchantment> THUNDERSTRUCK =
			RegistryKey.of(RegistryKeys.ENCHANTMENT, InformejtycyRegistry.id("thunderstruck"));

	public static void bootstrap(Registerable<Enchantment> registerable) {
		var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
		var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

		register(registerable, THUNDERSTRUCK, Enchantment.builder(Enchantment.definition(
				items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
				items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
				5,
				3,
				Enchantment.leveledCost(5, 7),
				Enchantment.leveledCost(25,9),
				2,
				AttributeModifierSlot.MAINHAND))
				.exclusiveSet(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
				.addEffect(EnchantmentEffectComponentTypes.POST_ATTACK,
						EnchantmentEffectTarget.ATTACKER,
						EnchantmentEffectTarget.VICTIM,
						new ThunderstruckEnchantmentEffect())
		);
	}

	private static void register(Registerable<Enchantment> registerable, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
		registerable.register(key, builder.build(key.getValue()));
	}
}
