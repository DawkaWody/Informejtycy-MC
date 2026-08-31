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

public class DungeonChestLootTableModifier {
    public static final Identifier DUNGEON_CHEST_LOOT_TABLE_ID = Identifier.ofVanilla("chests/dungeon");

    public static void register() {
        LootTableEvents.MODIFY.register((key, lootTableBuilder, source, lookup) -> {
            if (DUNGEON_CHEST_LOOT_TABLE_ID.equals(key.getValue())) {
                lootTableBuilder.pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.25f))
                                .with(ItemEntry.builder(CustomItems.ZALEWIX_BEAT_MUSIC_DISC).weight(1))
                );
                lootTableBuilder.pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.8f))
                                .with(ItemEntry.builder(CustomItems.RECYCLABLE_BOTTLE).weight(1))
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4, 12)).build())
                );
            }
        });
    }
}
