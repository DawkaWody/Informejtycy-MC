package daw.ka.informejtycy.client.datagen;

import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.tag.CustomTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class InformejtycyBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
	public InformejtycyBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
		builder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(key(CustomBlocks.THEORY_FORGE_BLOCK))
                .add(key(CustomBlocks.TRASH_CAN))
                .add(key(CustomBlocks.SILVER_WOLF_ORE))
                .add(key(CustomBlocks.SILVER_WOLF_BLOCK))
                .add(key(CustomBlocks.GOLDEN_WOLF_BLOCK))
                .add(key(CustomBlocks.BOMBARDINO_COCODRILO))
                .add(key(CustomBlocks.RECYCLER_BLOCK));
        builder(BlockTags.MINEABLE_WITH_AXE)
                .add(key(CustomBlocks.BRAINROT_TABLE_BLOCK))
                .add(key(CustomBlocks.TUNG_TUNG_SAHUR))
                .add(key(CustomBlocks.CHIMPANZINI_BANANINI));
        builder(BlockTags.MINEABLE_WITH_HOE)
                .add(key(CustomBlocks.TRALALERO_TRALALA));
		builder(BlockTags.NEEDS_DIAMOND_TOOL)
				.add(key(CustomBlocks.SILVER_WOLF_ORE))
                .add(key(CustomBlocks.SILVER_WOLF_BLOCK))
                .add(key(CustomBlocks.GOLDEN_WOLF_BLOCK));
		builder(CustomTags.Blocks.NEEDS_INFORMEJTYCY_TOOL)
				.addTag(BlockTags.NEEDS_DIAMOND_TOOL);
		builder(CustomTags.Blocks.NEEDS_REINFORCED_INFORMEJTYCY_TOOL)
				.add(key(Blocks.REINFORCED_DEEPSLATE))
				.add(key(CustomBlocks.RECYCLER_BLOCK));
		for (TagKey<Block> incorrectTag : List.of(BlockTags.INCORRECT_FOR_WOODEN_TOOL, BlockTags.INCORRECT_FOR_STONE_TOOL,
				BlockTags.INCORRECT_FOR_COPPER_TOOL, BlockTags.INCORRECT_FOR_GOLD_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL,
				BlockTags.INCORRECT_FOR_DIAMOND_TOOL, BlockTags.INCORRECT_FOR_NETHERITE_TOOL)) {
			builder(incorrectTag).addTag(CustomTags.Blocks.NEEDS_REINFORCED_INFORMEJTYCY_TOOL);
		}
	}

	private static ResourceKey<Block> key(Block block) {
		return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
	}
}
