package daw.ka.informejtycy.event.custom;

import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.entity.CustomEntities;
import daw.ka.informejtycy.entity.custom.boss.ZmysioEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ZmysioSpawnEvent {
    private static final BlockPos GLINIANKA_BLOCK_OFFSET = new BlockPos(-7, 1, -2);
    private static final BlockPos TRASH_CAN_OFFSET = new BlockPos(-5, 0, 0);
    private static final BlockPos ZMYSIO_SPAWNPOINT_OFFSET = new BlockPos(-6, 0, 7);

    public static void trySummonZmysio(ServerWorld world, BlockPos placementPos, Block placedBlock) {
        BlockPos placedOffset;
        BlockPos otherOffset;
        Block otherBlock;
        if (placedBlock == CustomBlocks.GLINIANKA_BLOCK) {
            placedOffset = GLINIANKA_BLOCK_OFFSET;
            otherOffset = TRASH_CAN_OFFSET;
            otherBlock = CustomBlocks.TRASH_CAN;
        } else if (placedBlock == CustomBlocks.TRASH_CAN) {
            placedOffset = TRASH_CAN_OFFSET;
            otherOffset = GLINIANKA_BLOCK_OFFSET;
            otherBlock = CustomBlocks.GLINIANKA_BLOCK;
        } else {
            return;
        }

        for (int rotation = 0; rotation < 4; rotation++) {
            BlockPos anchorPos = placementPos.subtract(rotateOffset(placedOffset, rotation));
            if (!world.getBlockState(anchorPos).isOf(CustomBlocks.ZMYSIO_SUMMON_ANCHOR)) continue;
            if (!world.getBlockState(anchorPos.add(rotateOffset(otherOffset, rotation))).isOf(otherBlock)) continue;

            summon(world, anchorPos, rotation);
            return;
        }
    }

    private static void summon(ServerWorld world, BlockPos anchorPos, int rotation) {
        ZmysioEntity boss = CustomEntities.ZMYSIO_BOSS.create(world, SpawnReason.MOB_SUMMONED);
        if (boss == null) return;

        world.setBlockState(anchorPos.add(rotateOffset(GLINIANKA_BLOCK_OFFSET, rotation)), Blocks.AIR.getDefaultState());

        BlockPos spawnPos = anchorPos.add(rotateOffset(ZMYSIO_SPAWNPOINT_OFFSET, rotation));
        boss.refreshPositionAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);
        world.spawnEntity(boss);
    }

    private static BlockPos rotateOffset(BlockPos offset, int numRots) {
        int x = offset.getX();
        int y = offset.getY();
        int z = offset.getZ();

        return switch (numRots % 4) {
            case 1 -> new BlockPos(-z, y, x);
            case 2 -> new BlockPos(-x, y, -z);
            case 3 -> new BlockPos(z, y, -x);
            default -> offset;
        };
    }
}
