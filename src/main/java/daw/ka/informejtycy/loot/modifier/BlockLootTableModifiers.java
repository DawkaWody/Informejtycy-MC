package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.advancements.predicates.ItemPredicate;

public class BlockLootTableModifiers {
	public static void register() {
		LootTableEvents.MODIFY.register((key, tableBuilder, source, lookup) -> {
			if (!source.isBuiltin()) return;

			if (Blocks.REINFORCED_DEEPSLATE.getLootTable().orElseThrow().equals(key)) {
				tableBuilder.withPool(LootPool.lootPool()
						.when(MatchTool.toolMatches(ItemPredicate.Builder.item()
								.of(lookup.lookupOrThrow(Registries.ITEM), CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE)))
						.when(ExplosionCondition.survivesExplosion())
						.add(LootItem.lootTableItem(Blocks.REINFORCED_DEEPSLATE)));
			} else if (Blocks.MELON.getLootTable().orElseThrow().equals(key)) {
				tableBuilder.withPool(LootPool.lootPool()
						.when(LootItemRandomChanceCondition.randomChance(.9f))
						.add(LootItem.lootTableItem(CustomItems.LIGHT_FOOD))
						.apply(SetItemCountFunction.setCount(
								ContextIntProviders.fromFloat(ContextFloatProviders.between(1.0f, 3.0f)))));
			}
		});
	}
}
