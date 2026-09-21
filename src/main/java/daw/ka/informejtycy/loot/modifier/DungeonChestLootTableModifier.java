package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.resources.Identifier;

public class DungeonChestLootTableModifier {
    public static final Identifier DUNGEON_CHEST_LOOT_TABLE_ID = Identifier.withDefaultNamespace("chests/dungeon");

    public static void register() {
        LootTableEvents.MODIFY.register((key, lootTableBuilder, source, lookup) -> {
            if (DUNGEON_CHEST_LOOT_TABLE_ID.equals(key.identifier())) {
                lootTableBuilder.withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .when(LootItemRandomChanceCondition.randomChance(0.25f))
                                .add(LootItem.lootTableItem(CustomItems.ZALEWIX_BEAT_MUSIC_DISC).setWeight(1))
                );
                lootTableBuilder.withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .when(LootItemRandomChanceCondition.randomChance(0.8f))
                                .add(LootItem.lootTableItem(CustomItems.RECYCLABLE_BOTTLE).setWeight(1))
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(4, 12)))
                );
            }
        });
    }
}
