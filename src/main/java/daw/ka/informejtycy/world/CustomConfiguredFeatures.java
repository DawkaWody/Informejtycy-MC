package daw.ka.informejtycy.world;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.CustomBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class CustomConfiguredFeatures {
	public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_WOLF_ORE_KEY = registerKey("silver_wolf_ore");

	public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
		RuleTest endStoneReplacebles = new BlockMatchRuleTest(Blocks.END_STONE);

		List<OreFeatureConfig.Target> silverWolfOres = List.of(
				OreFeatureConfig.createTarget(endStoneReplacebles, CustomBlocks.SILVER_WOLF_ORE.getDefaultState())
		);

		register(context, SILVER_WOLF_ORE_KEY, Feature.ORE, new OreFeatureConfig(silverWolfOres, 6, 0.0f));
	}

	public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, InformejtycyRegistry.id(name));
	}

	public static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
																				  RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
		context.register(key, new ConfiguredFeature<>(feature, config));

	}
}
