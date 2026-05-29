package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;

public class ReinforcedDeepslateLootTableModifier {
	private static final Identifier REINFORCED_DEEPSLATE_LOOT_TABLE_ID = Identifier.ofVanilla("blocks/reinforced_deepslate");

	public static void register() {
		LootTableEvents.MODIFY.register(((key, lootTableBuilder, source, lookup) -> {
			if (REINFORCED_DEEPSLATE_LOOT_TABLE_ID.equals(key.getValue())) {
				lootTableBuilder.pool(
						LootPool.builder()
								.rolls(ConstantLootNumberProvider.create(1))
								.conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create()
										.items(null, CustomItems.REINFORCED_INFORMEJTYCY_PICKAXE)))
								.with(ItemEntry.builder(Items.REINFORCED_DEEPSLATE))
								.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
				);
			}
		}));
	}
}
