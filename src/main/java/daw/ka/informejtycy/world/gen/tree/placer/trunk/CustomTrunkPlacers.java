package daw.ka.informejtycy.world.gen.tree.placer.trunk;

import daw.ka.informejtycy.world.gen.tree.placer.trunk.custom.ZarzykWoodTrunkPlacer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class CustomTrunkPlacers {
    public static TrunkPlacerType<ZarzykWoodTrunkPlacer> ZARZYK_WOOD_TRUNK_PLACER;

    public static void registerAll() {
        ZARZYK_WOOD_TRUNK_PLACER = Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, "zarzyk_wood_trunk_placer",
                new TrunkPlacerType<>(ZarzykWoodTrunkPlacer.CODEC));
    }
}
