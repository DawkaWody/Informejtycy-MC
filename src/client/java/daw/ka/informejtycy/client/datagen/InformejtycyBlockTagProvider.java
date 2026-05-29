package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.tag.CustomTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class InformejtycyBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public InformejtycyBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(CustomBlocks.THEORY_FORGE_BLOCK)
                .add(CustomBlocks.TRASH_CAN)
                .add(CustomBlocks.SILVER_WOLF_ORE)
                .add(CustomBlocks.SILVER_WOLF_BLOCK)
                .add(CustomBlocks.GOLDEN_WOLF_BLOCK);
        valueLookupBuilder(BlockTags.AXE_MINEABLE)
                .add(CustomBlocks.BRAINROT_TABLE_BLOCK);
		valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
				.add(CustomBlocks.SILVER_WOLF_ORE)
                .add(CustomBlocks.SILVER_WOLF_BLOCK)
                .add(CustomBlocks.GOLDEN_WOLF_BLOCK);
		valueLookupBuilder(CustomTags.Blocks.NEEDS_INFORMEJTYCY_TOOL)
				.addTag(BlockTags.NEEDS_DIAMOND_TOOL);
		valueLookupBuilder(CustomTags.Blocks.NEEDS_REINFORCED_INFORMEJTYCY_TOOL)
				.addTag(BlockTags.NEEDS_DIAMOND_TOOL);
	}
}
