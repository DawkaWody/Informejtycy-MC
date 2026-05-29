package daw.ka.informejtycy.event.custom;

import daw.ka.informejtycy.block.CustomBlocks;
import daw.ka.informejtycy.entity.CustomEntities;
import daw.ka.informejtycy.entity.custom.boss.ZmysioEntity;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class ZmysioSpawnEvent {
    private static final int ANCHOR_SEARCH_RADIUS = 10;
    private static final BlockPos GLINIANKA_BLOCK_OFFSET = new BlockPos(-7, 1, -2);
    private static final BlockPos TRASH_CAN_OFFSET = new BlockPos(-5, 0, 0);
    private static final BlockPos ZMYSIO_SPAWNPOINT_OFFSET = new BlockPos(-6, 0, 7);

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world instanceof ServerWorld serverWorld) {
                BlockPos pos = hitResult.getBlockPos().offset(hitResult.getSide());
                Item item = player.getStackInHand(hand).getItem();
                if (item instanceof BlockItem blockItem) {
                    trySummonZmysio(serverWorld, pos, blockItem.getBlock());
                }
            }

            return ActionResult.PASS;
        });
    }

    private static void trySummonZmysio(ServerWorld world, BlockPos placementPos, Block placedBlock) {
        BlockPos anchorPos = findAnchor(world, placementPos);
        int rotation = findRotation(world, anchorPos, placedBlock);
        if (rotation == -1 || anchorPos == null) return;

        BlockPos spawnPos = anchorPos.add(rotateOffset(ZMYSIO_SPAWNPOINT_OFFSET, rotation));
        world.setBlockState(anchorPos.add(rotateOffset(GLINIANKA_BLOCK_OFFSET, rotation)), Blocks.AIR.getDefaultState());
        ZmysioEntity boss = CustomEntities.ZMYSIO_BOSS.create(world, SpawnReason.MOB_SUMMONED);
        if (boss == null) return;
        boss.refreshPositionAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);
        world.spawnEntity(boss);
    }

    private static boolean checkSpawnConditions(ServerWorld world, BlockPos anchorPos, Block placedBlock, int rotation) {
        if (anchorPos == null) return false;

        BlockPos rotatedTrashCanOffset = rotateOffset(TRASH_CAN_OFFSET, rotation);
        BlockPos rotatedGliniankaOffset = rotateOffset(GLINIANKA_BLOCK_OFFSET, rotation);
        if (placedBlock == CustomBlocks.GLINIANKA_BLOCK) {
            BlockPos trashCanPos = anchorPos.add(rotatedTrashCanOffset);
            BlockState trashCanState = world.getBlockState(trashCanPos);
            return trashCanState.isOf(CustomBlocks.TRASH_CAN);
        } else if (placedBlock == CustomBlocks.TRASH_CAN) {
            BlockPos gliniankaPos = anchorPos.add(rotatedGliniankaOffset);
            BlockState gliniankaState = world.getBlockState(gliniankaPos);
            return gliniankaState.isOf(CustomBlocks.GLINIANKA_BLOCK);
        } else {
            return false;
        }
    }

    private static Integer findRotation(ServerWorld world, BlockPos anchorPos, Block placedBlock) {
        for (int rot = 0; rot < 4; rot++) {
            if (checkSpawnConditions(world, anchorPos, placedBlock, rot)) {
                return rot;
            }
        }
        return -1;
    }

    private static BlockPos findAnchor(ServerWorld world, BlockPos placementPos) {
        for (BlockPos pos : BlockPos.iterate(
                placementPos.add(-ANCHOR_SEARCH_RADIUS, -ANCHOR_SEARCH_RADIUS, -ANCHOR_SEARCH_RADIUS),
                placementPos.add(ANCHOR_SEARCH_RADIUS, ANCHOR_SEARCH_RADIUS, ANCHOR_SEARCH_RADIUS))) {
            if (world.getBlockState(pos).isOf(CustomBlocks.ZMYSIO_SUMMON_ANCHOR)) {
                return pos.toImmutable();
            }
        }
        return null;
    }

    private static BlockPos rotateOffset(BlockPos offset, int numRots) {
        int x = offset.getX();
        int y = offset.getY();
        int z = offset.getZ();

        numRots = ((numRots % 4)) % 4;
        return switch (numRots) {
            case 0 -> new BlockPos(x, y, z);
            case 1 -> new BlockPos(-z, y, x);
            case 2 -> new BlockPos(-x, y, -z);
            case 3 -> new BlockPos(z, y, -x);
            default -> offset;
        };
    }
}
