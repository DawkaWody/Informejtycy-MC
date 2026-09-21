package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.resources.Identifier;

public class MobLootTableModifier {
    public static final Identifier WARDEN_LOOT_TABLE_ID = Identifier.withDefaultNamespace("entities/warden");
    public static final Identifier ZOMBIE_LOOT_TABLE_ID = Identifier.withDefaultNamespace("entities/zombie");
    public static final Identifier SKELETON_LOOT_TABLE_ID = Identifier.withDefaultNamespace("entities/skeleton");
    public static final Identifier ENDERMAN_LOOT_TABLE_ID = Identifier.withDefaultNamespace("entities/enderman");

    public static void register() {
        LootTableEvents.MODIFY.register((key, lootTableBuilder, source, registries) -> {
            var enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);

            if (WARDEN_LOOT_TABLE_ID.equals(key.identifier())) {
                lootTableBuilder.withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.binomial(4, 0.5f))
                                .add(LootItem.lootTableItem(CustomItems.BALLS_UNDER_MAGNIFIER))
                );
            } else if (ZOMBIE_LOOT_TABLE_ID.equals(key.identifier()) || SKELETON_LOOT_TABLE_ID.equals(key.identifier())) {
                lootTableBuilder.withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(enchantments, 0.15f, 0.05f))
                                .add(LootItem.lootTableItem(CustomItems.RECYCLABLE_BOTTLE).setWeight(1))
                );
            } else if (ENDERMAN_LOOT_TABLE_ID.equals(key.identifier())) {
                lootTableBuilder.withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(CustomItems.RECYCLABLE_BOTTLE).setWeight(1)
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0f, 1.0f))))
                );
            }
        });
    }
}
