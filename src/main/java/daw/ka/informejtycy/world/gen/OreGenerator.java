package daw.ka.informejtycy.world.gen;

import daw.ka.informejtycy.world.CustomPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class OreGenerator {
	public static void register() {
		BiomeModifications.addFeature(
				BiomeSelectors.includeByKey(
						BiomeKeys.END_HIGHLANDS,
						BiomeKeys.END_MIDLANDS
				),
				GenerationStep.Feature.UNDERGROUND_ORES,
				CustomPlacedFeatures.SILVER_WOLF_ORE_PLACED_KEY
		);
	}
}
