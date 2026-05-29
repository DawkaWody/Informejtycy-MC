package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.util.Identifier;

public class WardenLootTableModifier {
	public static final Identifier WARDEN_LOOT_TABLE_ID = Identifier.ofVanilla("entities/warden");

	public static void register() {
		LootTableEvents.MODIFY.register((key, lootTableBuilder, source, lookup) -> {
			if (WARDEN_LOOT_TABLE_ID.equals(key.getValue())) {
				lootTableBuilder.pool(
						LootPool.builder()
								.rolls(BinomialLootNumberProvider.create(4, 0.5f))
								.with(ItemEntry.builder(CustomItems.BALLS_UNDER_MAGNIFIER))
				);
			}
		});
	}
}
