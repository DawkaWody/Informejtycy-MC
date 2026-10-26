package daw.ka.informejtycy.world.gen;

import daw.ka.informejtycy.world.CustomPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class InformejtycyWorldGen {
	public static void registerGenerators() {
		OreGenerator.register();

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(
				Biomes.PLAINS
		), GenerationStep.Decoration.VEGETAL_DECORATION, CustomPlacedFeatures.ISTANBUL_TREE_PLACED_KEY);
	}
}
