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
		tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(key(CustomBlocks.THEORY_FORGE_BLOCK))
                .add(key(CustomBlocks.TRASH_CAN))
                .add(key(CustomBlocks.SILVER_WOLF_ORE))
                .add(key(CustomBlocks.SILVER_WOLF_BLOCK))
                .add(key(CustomBlocks.GOLDEN_WOLF_BLOCK))
                .add(key(CustomBlocks.BOMBARDINO_COCODRILO))
                .add(key(CustomBlocks.RECYCLER_BLOCK));

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(key(CustomBlocks.BRAINROT_TABLE_BLOCK))
                .add(key(CustomBlocks.TUNG_TUNG_SAHUR))
                .add(key(CustomBlocks.CHIMPANZINI_BANANINI));
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(key(CustomBlocks.TRALALERO_TRALALA));

		tag(BlockTags.NEEDS_DIAMOND_TOOL)
				.add(key(CustomBlocks.SILVER_WOLF_ORE))
                .add(key(CustomBlocks.SILVER_WOLF_BLOCK))
                .add(key(CustomBlocks.GOLDEN_WOLF_BLOCK));
		tag(CustomTags.Blocks.NEEDS_INFORMEJTYCY_TOOL)
				.addTag(BlockTags.NEEDS_DIAMOND_TOOL);
		tag(CustomTags.Blocks.NEEDS_REINFORCED_INFORMEJTYCY_TOOL)
				.add(key(Blocks.REINFORCED_DEEPSLATE))
				.add(key(CustomBlocks.RECYCLER_BLOCK));
		for (TagKey<Block> incorrectTag : List.of(BlockTags.INCORRECT_FOR_WOODEN_TOOL, BlockTags.INCORRECT_FOR_STONE_TOOL,
				BlockTags.INCORRECT_FOR_COPPER_TOOL, BlockTags.INCORRECT_FOR_GOLD_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL,
				BlockTags.INCORRECT_FOR_DIAMOND_TOOL, BlockTags.INCORRECT_FOR_NETHERITE_TOOL)) {
			tag(incorrectTag).addTag(CustomTags.Blocks.NEEDS_REINFORCED_INFORMEJTYCY_TOOL);
		}

		tag(CustomTags.Blocks.ISTANBUL_LOGS)
				.add(key(CustomBlocks.ISTANBUL_LOG))
				.add(key(CustomBlocks.STRIPPED_ISTANBUL_LOG))
				.add(key(CustomBlocks.ISTANBUL_WOOD))
				.add(key(CustomBlocks.STRIPPED_ISTANBUL_WOOD));
		tag(BlockTags.LOGS)
				.addTag(CustomTags.Blocks.ISTANBUL_LOGS);
		tag(BlockTags.PLANKS)
				.add(key(CustomBlocks.ISTANBUL_PLANKS));
		tag(BlockTags.LEAVES)
				.add(key(CustomBlocks.ISTANBUL_LEAVES));
		tag(BlockTags.FLOWER_POTS)
				.add(key(CustomBlocks.POTTED_ISTANBUL_SAPLING));
		tag(BlockTags.WOODEN_STAIRS)
				.add(key(CustomBlocks.ISTANBUL_STAIRS));
		tag(BlockTags.WOODEN_SLABS)
				.add(key(CustomBlocks.ISTANBUL_SLAB));
		tag(BlockTags.WOODEN_FENCES)
				.add(key(CustomBlocks.ISTANBUL_FENCE));
		tag(BlockTags.FENCE_GATES)
				.add(key(CustomBlocks.ISTANBUL_FENCE_GATE));
		tag(BlockTags.WOODEN_PRESSURE_PLATES)
				.add(key(CustomBlocks.ISTANBUL_PRESSURE_PLATE));
		tag(BlockTags.WOODEN_BUTTONS)
				.add(key(CustomBlocks.ISTANBUL_BUTTON));
		tag(BlockTags.WOODEN_TRAPDOORS)
				.add(key(CustomBlocks.ISTANBUL_TRAPDOOR));
		tag(BlockTags.WOODEN_DOORS)
				.add(key(CustomBlocks.ISTANBUL_DOOR));
	}

	private static ResourceKey<Block> key(Block block) {
		return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
	}
}
