package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class MobLootTableModifier {
    public static final Identifier WARDEN_LOOT_TABLE_ID = Identifier.ofVanilla("entities/warden");
    public static final Identifier ZOMBIE_LOOT_TABLE_ID = Identifier.ofVanilla("entities/zombie");
    public static final Identifier SKELETON_LOOT_TABLE_ID = Identifier.ofVanilla("entities/skeleton");
    public static final Identifier ENDERMAN_LOOT_TABLE_ID = Identifier.ofVanilla("entities/enderman");

    public static void register() {
        LootTableEvents.MODIFY.register((key, lootTableBuilder, source, lookup) -> {
            if (WARDEN_LOOT_TABLE_ID.equals(key.getValue())) {
                lootTableBuilder.pool(
                        LootPool.builder()
                                .rolls(BinomialLootNumberProvider.create(4, 0.5f))
                                .with(ItemEntry.builder(CustomItems.BALLS_UNDER_MAGNIFIER))
                );
            } else if (ZOMBIE_LOOT_TABLE_ID.equals(key.getValue()) || SKELETON_LOOT_TABLE_ID.equals(key.getValue())) {
                lootTableBuilder.pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .conditionally(RandomChanceLootCondition.builder(0.15f))
                                .with(ItemEntry.builder(CustomItems.RECYCLABLE_BOTTLE).weight(1))
                );
            } else if (ENDERMAN_LOOT_TABLE_ID.equals(key.getValue())) {
                lootTableBuilder.pool(
                        LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(CustomItems.RECYCLABLE_BOTTLE).weight(1))
                );
            }
        });
    }
}
