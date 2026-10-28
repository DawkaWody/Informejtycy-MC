package daw.ka.informejtycy.world.gen.tree.placer.foliage;

import daw.ka.informejtycy.world.gen.tree.placer.foliage.custom.ZarzykBushFoliagePlacer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class CustomFoliagePlacers {
    public static FoliagePlacerType<ZarzykBushFoliagePlacer> ZARZYK_BUSH_FOLIAGE_PLACER_FOLIAGE_PLACER;

    public static void registerAll() {
        ZARZYK_BUSH_FOLIAGE_PLACER_FOLIAGE_PLACER = Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, "zarzyk_bush_foliage_placer",
                new FoliagePlacerType<>(ZarzykBushFoliagePlacer.CODEC));
    }
}
