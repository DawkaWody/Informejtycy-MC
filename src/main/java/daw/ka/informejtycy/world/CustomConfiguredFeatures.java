package daw.ka.informejtycy.world;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.world.gen.tree.placer.foliage.custom.ZarzykBushFoliagePlacer;
import daw.ka.informejtycy.world.gen.tree.placer.trunk.custom.ZarzykWoodTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BlockStateProviders;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;

import java.util.List;

public class CustomConfiguredFeatures {
	public static final ResourceKey<Feature> SILVER_WOLF_ORE_KEY = registerKey("silver_wolf_ore");
	public static final ResourceKey<Feature> ISTANBUL_TREE_KEY = registerKey("istanbul_tree");
	public static final ResourceKey<Feature> ZARZYK_TREE_KEY = registerKey("zarzyk_tree");

	public static void bootstrap(BootstrapContext<Feature> context) {
		register(context, SILVER_WOLF_ORE_KEY, new OreFeature(List.of(
				BlockReplacement.replace(new BlockMatchTest(Blocks.END_STONE), CustomBlocks.SILVER_WOLF_ORE.defaultBlockState())
		), 6, 0.0f));

		register(context, ISTANBUL_TREE_KEY, new TreeFeature.Builder(
				BlockStateProvider.of(CustomBlocks.ISTANBUL_LOG),
				new StraightTrunkPlacer(5, 2, 0),
				BlockStateProvider.of(CustomBlocks.ISTANBUL_LEAVES),

				new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
				new TwoLayersFeatureSize(1, 0, 2),

				context.lookup(Registries.BLOCK_STATE_PROVIDER).getOrThrow(BlockStateProviders.SOIL_BENEATH_TREE)
		).ignoreVines().build());

		register(context, ZARZYK_TREE_KEY, new TreeFeature.Builder(
				BlockStateProvider.of(CustomBlocks.ZARZYK_LOG),
				new ZarzykWoodTrunkPlacer(2, 2, 4),
				BlockStateProvider.of(CustomBlocks.ZARZYK_LEAVES),

				new ZarzykBushFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1)),
				new TwoLayersFeatureSize(1, 0, 2),

				context.lookup(Registries.BLOCK_STATE_PROVIDER).getOrThrow(BlockStateProviders.SOIL_BENEATH_TREE)
		).ignoreVines().build());
	}

	public static ResourceKey<Feature> registerKey(String name) {
		return ResourceKey.create(Registries.FEATURE, InformejtycyRegistry.id(name));
	}

	public static void register(BootstrapContext<Feature> context, ResourceKey<Feature> key, Feature feature) {
		context.register(key, feature);

	}
}
