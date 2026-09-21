package daw.ka.informejtycy.world;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.CustomBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;

import java.util.List;

public class CustomConfiguredFeatures {
	public static final ResourceKey<Feature> SILVER_WOLF_ORE_KEY = registerKey("silver_wolf_ore");

	public static void bootstrap(BootstrapContext<Feature> context) {
		RuleTest endStoneReplacebles = new BlockMatchTest(Blocks.END_STONE);

		List<BlockReplacement> silverWolfOres = List.of(
				BlockReplacement.replace(endStoneReplacebles, CustomBlocks.SILVER_WOLF_ORE.defaultBlockState())
		);

		register(context, SILVER_WOLF_ORE_KEY, new OreFeature(silverWolfOres, 6, 0.0f));
	}

	public static ResourceKey<Feature> registerKey(String name) {
		return ResourceKey.create(Registries.FEATURE, InformejtycyRegistry.id(name));
	}

	public static void register(BootstrapContext<Feature> context, ResourceKey<Feature> key, Feature feature) {
		context.register(key, feature);

	}
}
