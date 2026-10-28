package daw.ka.informejtycy.world.gen;

import daw.ka.informejtycy.world.gen.tree.placer.foliage.CustomFoliagePlacers;
import daw.ka.informejtycy.world.gen.tree.placer.trunk.CustomTrunkPlacers;

public class InformejtycyWorldGen {
	public static void registerGenerators() {
		OreGenerator.register();
		CustomTrunkPlacers.registerAll();
		CustomFoliagePlacers.registerAll();
	}
}
