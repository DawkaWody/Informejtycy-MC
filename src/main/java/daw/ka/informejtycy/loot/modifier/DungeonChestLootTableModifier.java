package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class DungeonChestLootTableModifier {
    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, lookup) -> {
            if (!source.isBuiltin()) return;

            if (BuiltInLootTables.SIMPLE_DUNGEON.equals(key)) {
                tableBuilder.withPool(LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(LootItem.lootTableItem(CustomItems.ZALEWIX_BEAT_MUSIC_DISC)));
                tableBuilder.withPool(LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.8f))
                        .add(LootItem.lootTableItem(CustomItems.RECYCLABLE_BOTTLE))
                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 12))));
            }
        });
    }
}
