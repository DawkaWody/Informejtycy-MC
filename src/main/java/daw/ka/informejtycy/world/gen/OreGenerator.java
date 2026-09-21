package daw.ka.informejtycy.world.gen;

import daw.ka.informejtycy.world.CustomPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class OreGenerator {
	public static void register() {
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(
						Biomes.END_HIGHLANDS,
						Biomes.END_MIDLANDS
				),
				GenerationStep.Decoration.UNDERGROUND_ORES,
				CustomPlacedFeatures.SILVER_WOLF_ORE_PLACED_KEY
		);
	}
}
