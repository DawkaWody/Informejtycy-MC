package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class MelonLootTableModifier {
	private static final Identifier WATERMELON_LOOT_TABLE_ID = Identifier.ofVanilla("blocks/melon");

	public static void register() {
		LootTableEvents.MODIFY.register((key, lootTableBuilder, source, lookup) -> {
			if (WATERMELON_LOOT_TABLE_ID.equals(key.getValue())) {
				lootTableBuilder.pool(
					LootPool.builder()
							.rolls(ConstantLootNumberProvider.create(1))
							.conditionally(RandomChanceLootCondition.builder(.9f))
							.with(ItemEntry.builder(CustomItems.LIGHT_FOOD))
							.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)).build())
				);
			}
		});
	}
}
