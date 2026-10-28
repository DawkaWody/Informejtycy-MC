package daw.ka.informejtycy.world.gen.tree.placer.foliage.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import daw.ka.informejtycy.world.gen.tree.placer.foliage.CustomFoliagePlacers;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jspecify.annotations.NonNull;

public class ZarzykBushFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<ZarzykBushFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance).apply(instance, ZarzykBushFoliagePlacer::new));

    public ZarzykBushFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected @NonNull FoliagePlacerType<?> type() {
        return CustomFoliagePlacers.ZARZYK_BUSH_FOLIAGE_PLACER_FOLIAGE_PLACER;
    }

    @Override
    protected void createFoliage(@NonNull WorldGenLevel level, @NonNull FoliageSetter foliageSetter, @NonNull RandomSource random, @NonNull TreeFeature tree, int treeHeight, @NonNull FoliageAttachment foliageAttachment, int foliageHeight, int leafRadius, int offset) {
        tryPlaceLeaf(level, foliageSetter, random, tree, foliageAttachment.pos().north());
        tryPlaceLeaf(level, foliageSetter, random, tree, foliageAttachment.pos().south());
        tryPlaceLeaf(level, foliageSetter, random, tree, foliageAttachment.pos().west());
        tryPlaceLeaf(level, foliageSetter, random, tree, foliageAttachment.pos().east());
    }

    @Override
    public int foliageHeight(@NonNull RandomSource random, int treeHeight, @NonNull TreeFeature tree) {
        return 1;
    }

    @Override
    protected boolean shouldSkipLocation(@NonNull RandomSource random, int dx, int y, int dz, int currentRadius, boolean doubleTrunk) {
        return false;
    }
}
