package daw.ka.informejtycy.world.gen.tree.placer.trunk.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import daw.ka.informejtycy.world.gen.tree.placer.trunk.CustomTrunkPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.function.BiConsumer;

public class ZarzykWoodTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<ZarzykWoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, ZarzykWoodTrunkPlacer::new));

    public ZarzykWoodTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected @NonNull TrunkPlacerType<?> type() {
        return CustomTrunkPlacers.ZARZYK_WOOD_TRUNK_PLACER;
    }

    @Override
    public @NonNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NonNull WorldGenLevel level, @NonNull BiConsumer<BlockPos, BlockState> trunkSetter, @NonNull RandomSource random, int treeHeight, @NonNull BlockPos origin, @NonNull TreeFeature tree) {
        placeBelowTrunkBlock(level, trunkSetter, random, origin.below(), tree);
        int height = treeHeight + random.nextInt(heightRandA, heightRandB);

        placeLog(level, trunkSetter, random, origin, tree);
        placeLog(level, trunkSetter, random, origin.west(), tree);
        placeLog(level, trunkSetter, random, origin.east(), tree);

        BlockPos currentPos = origin.above();
        for (int y = 0; y < height; y++) {
            placeLog(level, trunkSetter, random, currentPos, tree);
            currentPos = currentPos.above();
        }
        level.setBlock(currentPos, Blocks.CHERRY_PLANKS.defaultBlockState(), 3);

        return List.of(new FoliagePlacer.FoliageAttachment(origin, 0, false),
                new FoliagePlacer.FoliageAttachment(origin.west(), 0, false),
                new FoliagePlacer.FoliageAttachment(origin.east(), 0, false),
                new FoliagePlacer.FoliageAttachment(origin.above(), 0, false));
    }
}
