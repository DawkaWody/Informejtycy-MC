package daw.ka.informejtycy.potion;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.item.CustomItems;
import daw.ka.informejtycy.potion.effect.CustomEffects;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;

public class CustomPotions {
	public static RegistryEntry<Potion> AURA_POTION;

	public static void registerAll() {
		AURA_POTION = InformejtycyRegistry.registerPotion("aura_potion",
				new Potion("Aura", new StatusEffectInstance(CustomEffects.AURA, 3600, 0, false, true)));

		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerRecipes(Item.fromBlock(CustomBlocks.SILVER_WOLF_ORE), AURA_POTION);
		});
	}
}
