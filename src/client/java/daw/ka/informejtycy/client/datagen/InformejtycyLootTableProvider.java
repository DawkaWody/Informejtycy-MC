package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.IntLimit;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class InformejtycyLootTableProvider extends FabricBlockLootSubProvider {
	private final CompletableFuture<HolderLookup.Provider> REGISTRY_LOOKUP;

	public InformejtycyLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(dataOutput, registryLookup);
		this.REGISTRY_LOOKUP = registryLookup;
	}

	@Override
	public void generate() {
		add(CustomBlocks.SILVER_WOLF_ORE, multipleOreDrops(CustomBlocks.SILVER_WOLF_ORE, CustomItems.SILVER_WOLF, 1.0f, 2.0f));
		add(CustomBlocks.DARK_GLOWSTONE, glowstoneDrops(CustomBlocks.DARK_GLOWSTONE, CustomItems.DARK_GLOWSTONE_DUST));
		dropSelf(CustomBlocks.THEORY_FORGE_BLOCK);
		dropSelf(CustomBlocks.BRAINROT_TABLE_BLOCK);
        dropSelf(CustomBlocks.SILVER_WOLF_BLOCK);
        dropSelf(CustomBlocks.GOLDEN_WOLF_BLOCK);
		dropSelf(CustomBlocks.GLINIANKA_BLOCK);
		dropSelf(CustomBlocks.TRASH_CAN);
		dropSelf(CustomBlocks.BOMBARDINO_COCODRILO);
		dropSelf(CustomBlocks.TRALALERO_TRALALA);
		dropSelf(CustomBlocks.TUNG_TUNG_SAHUR);
		dropSelf(CustomBlocks.CHIMPANZINI_BANANINI);
	}

	protected LootTable.Builder glowstoneDrops(Block block, Item drop) {
		try {
			HolderLookup.RegistryLookup<Enchantment> impl = REGISTRY_LOOKUP.get().lookupOrThrow(Registries.ENCHANTMENT);
			return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(drop)
					.apply(SetItemCountFunction.setCount(ContextIntProviders.fromFloat(ContextFloatProviders.between(2.0f, 4.0f))))
					.apply(ApplyBonusCount.addUniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))
					.apply(LimitCount.limitCount(IntLimit.range(1, 4)))));
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	protected LootTable.Builder multipleOreDrops(Block block, Item drop, float minDrops, float maxDrops) {
		try {
			HolderLookup.RegistryLookup<Enchantment> impl = REGISTRY_LOOKUP.get().lookupOrThrow(Registries.ENCHANTMENT);
			return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(drop, ((UniformContainerBase.Builder<?>)
					LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(ContextIntProviders.fromFloat(ContextFloatProviders.between(minDrops, maxDrops)))))
					.apply(ApplyBonusCount.addOreBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))));
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
