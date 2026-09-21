package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.resources.Identifier;

public class ReinforcedDeepslateLootTableModifier {
	private static final Identifier REINFORCED_DEEPSLATE_LOOT_TABLE_ID = Identifier.withDefaultNamespace("blocks/reinforced_deepslate");

	public static void register() {
		LootTableEvents.MODIFY.register(((key, lootTableBuilder, source, lookup) -> {
			if (REINFORCED_DEEPSLATE_LOOT_TABLE_ID.equals(key.identifier())) {
				lootTableBuilder.withPool(
						LootPool.lootPool()
								.setRolls(ContextIntProviders.exactly(1))
								.when(MatchTool.toolMatches(ItemPredicate.Builder.item()
										.of(lookup.lookupOrThrow(Registries.ITEM), CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE)))
								.add(LootItem.lootTableItem(Items.REINFORCED_DEEPSLATE))
								.apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
				);
			}
		}));
	}
}
