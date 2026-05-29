package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.item.CustomItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class InformejtycyLootTableProvider extends FabricBlockLootTableProvider {
	private final CompletableFuture<RegistryWrapper.WrapperLookup> REGISTRY_LOOKUP;

	public InformejtycyLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
		this.REGISTRY_LOOKUP = registryLookup;
	}

	@Override
	public void generate() {
		addDrop(CustomBlocks.SILVER_WOLF_ORE, multipleOreDrops(CustomBlocks.SILVER_WOLF_ORE, CustomItems.SILVER_WOLF, 1.0f, 2.0f));
		addDrop(CustomBlocks.DARK_GLOWSTONE, multipleOreDrops(CustomBlocks.DARK_GLOWSTONE, CustomItems.DARK_GLOWSTONE_DUST, 2.0f, 4.0f));
		addDrop(CustomBlocks.THEORY_FORGE_BLOCK);
		addDrop(CustomBlocks.BRAINROT_TABLE_BLOCK);
        addDrop(CustomBlocks.SILVER_WOLF_BLOCK);
        addDrop(CustomBlocks.GOLDEN_WOLF_BLOCK);
		addDrop(CustomBlocks.GLINIANKA_BLOCK);
		addDrop(CustomBlocks.TRASH_CAN);
		addDrop(CustomBlocks.BOMBARDINO_COCODRILO);
		addDrop(CustomBlocks.TRALALERO_TRALALA);
		addDrop(CustomBlocks.TUNG_TUNG_SAHUR);
		addDrop(CustomBlocks.CHIMPANZINI_BANANINI);
	}

	protected LootTable.Builder multipleOreDrops(Block block, Item drop, float minDrops, float maxDrops) {
		try {
			RegistryWrapper.Impl<Enchantment> impl = REGISTRY_LOOKUP.get().getOrThrow(RegistryKeys.ENCHANTMENT);
			return this.dropsWithSilkTouch(block, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
					ItemEntry.builder(drop).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
					.apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
