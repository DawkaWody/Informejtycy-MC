package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.resources.Identifier;

public class MelonLootTableModifier {
	private static final Identifier WATERMELON_LOOT_TABLE_ID = Identifier.withDefaultNamespace("blocks/melon");

	public static void register() {
		LootTableEvents.MODIFY.register((key, lootTableBuilder, source, lookup) -> {
			if (WATERMELON_LOOT_TABLE_ID.equals(key.identifier())) {
				lootTableBuilder.withPool(
					LootPool.lootPool()
							.setRolls(ContextIntProviders.exactly(1))
							.when(LootItemRandomChanceCondition.randomChance(.9f))
							.add(LootItem.lootTableItem(CustomItems.LIGHT_FOOD))
							.apply(SetItemCountFunction.setCount(
									ContextIntProviders.fromFloat(ContextFloatProviders.between(1.0f, 3.0f))))
				);
			}
		});
	}
}
