package daw.ka.informejtycy.enchantment;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.enchantment.custom.ThunderstruckEnchantmentEffect;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;

public class CustomEnchantments {
	public static final ResourceKey<Enchantment> THUNDERSTRUCK =
			ResourceKey.create(Registries.ENCHANTMENT, InformejtycyRegistry.id("thunderstruck"));

	public static void bootstrap(BootstrapContext<Enchantment> registerable) {
		var enchantments = registerable.lookup(Registries.ENCHANTMENT);
		var items = registerable.lookup(Registries.ITEM);

		register(registerable, THUNDERSTRUCK, Enchantment.enchantment(Enchantment.definition(
				items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
				items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
				5,
				3,
				Enchantment.dynamicCost(5, 7),
				Enchantment.dynamicCost(25,9),
				2,
				EquipmentSlotGroup.MAINHAND))
				.withEffect(EnchantmentEffectComponents.POST_ATTACK,
						EnchantmentTarget.ATTACKER,
						EnchantmentTarget.VICTIM,
						new ThunderstruckEnchantmentEffect())
		);
	}

	private static void register(BootstrapContext<Enchantment> registerable, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
		registerable.register(key, builder.build(key.identifier()));
	}
}
