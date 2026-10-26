package daw.ka.informejtycy.world;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.util.PlacementModifiers;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class CustomPlacedFeatures {
	public static final ResourceKey<PlacedFeature> SILVER_WOLF_ORE_PLACED_KEY = registerKey("silver_wolf_ore_placed");
	public static final ResourceKey<PlacedFeature> ISTANBUL_TREE_PLACED_KEY = registerKey("istanbul_tree_placed");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		var configuredFeatures = context.lookup(Registries.FEATURE);

		register(context, SILVER_WOLF_ORE_PLACED_KEY, configuredFeatures.getOrThrow(CustomConfiguredFeatures.SILVER_WOLF_ORE_KEY),
				PlacementModifiers.modifiersWithCount(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(10), VerticalAnchor.absolute(40))));
		register(context, ISTANBUL_TREE_PLACED_KEY, configuredFeatures.getOrThrow(CustomConfiguredFeatures.ISTANBUL_TREE_KEY),
				VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2), CustomBlocks.ISTANBUL_SAPLING));
	}

	public static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, InformejtycyRegistry.id(name));
	}

	private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
								 Holder<Feature> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}

	private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
								 Holder<Feature> configuration, PlacementModifier... modifiers) {
		register(context, key, configuration, List.of(modifiers));
	}
}
