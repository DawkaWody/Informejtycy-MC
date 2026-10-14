package daw.ka.informejtycy.loot.modifier;

import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class MobLootTableModifiers {
    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin()) return;

            var enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);

            if (EntityTypes.WARDEN.getDefaultLootTable().orElseThrow().equals(key)) {
                tableBuilder.withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.binomial(4, 0.5f))
                        .add(LootItem.lootTableItem(CustomItems.BALLS_UNDER_MAGNIFIER)));
            } else if (EntityTypes.ZOMBIE.getDefaultLootTable().orElseThrow().equals(key)
                    || EntityTypes.SKELETON.getDefaultLootTable().orElseThrow().equals(key)) {
                tableBuilder.withPool(LootPool.lootPool()
                        .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(enchantments, 0.15f, 0.05f))
                        .add(LootItem.lootTableItem(CustomItems.RECYCLABLE_BOTTLE)));
            } else if (EntityTypes.ENDERMAN.getDefaultLootTable().orElseThrow().equals(key)) {
                tableBuilder.withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(CustomItems.RECYCLABLE_BOTTLE)
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0f, 1.0f)))));
            }
        });
    }
}
