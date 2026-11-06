package daw.ka.informejtycy.block;

import com.mojang.math.OctahedralGroup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomShapes {
	private static final VoxelShape INTERACTION_BOUNDS = Block.box(-8, -8, -8, 24, 24, 24);

	public static VoxelShape clampToInteractionBounds(VoxelShape shape) {
		return Shapes.join(shape, INTERACTION_BOUNDS, BooleanOp.AND);
	}

	public static final VoxelShape BIDON_CAP = Shapes.or(
			Block.box(0, 0, 0, 16, 3, 16),
			Block.box(2, 3, 2, 14, 6, 14),
			Block.box(5, 6, 5, 11, 9, 11),
			Block.box(4, 9, 4, 12, 16, 12)
	);
	public static final VoxelShape RECYCLER = Shapes.or(
			Block.box(2, 0, 2, 14, 24, 14),
			Block.box(3, 24, 12, 13, 27, 14)
	);
	public static final VoxelShape TRASH_CAN = Shapes.rotate(Shapes.or(
			Block.box(3.5, 0, 2.5, 12.5, 3, 13.5),
			Block.box(3, 3, 2, 13, 11, 14),
			Block.box(2.5, 11, 1.5, 13.5, 16, 14.5),
			Block.box(3, 16, 2, 13, 17, 14),
			Block.box(4, 17, 2.5, 12.5, 18, 13.5)
	), OctahedralGroup.BLOCK_ROT_Y_90);

	// Brainrots
	public static final VoxelShape BOMBARDINO_COCODRILO = Shapes.or(
			Block.box(3.5, 16, 0, 12.5, 23, 12),
			Block.box(5.25, 23, 2.75, 10.75, 25.25, 9.25),
			Block.box(3.5, 16.75, -6, 12.5, 22.25, 0),
			Block.box(5, 16.75, -16, 11, 21.75, -6),
			Block.box(5.5, 17, 12, 10.5, 22, 21),
			Block.box(6.5, 17.5, 21, 9.5, 20.5, 28),
			Block.box(2, 22, 25, 14, 23, 29),
			Block.box(7, 20.5, 25, 9, 24, 31),
			Block.box(-13.5, 18, 0, 29.5, 20, 9),
			Block.box(-7.5, 15, -8, 0.5, 23, 5),
			Block.box(15.5, 15, -8, 23.5, 23, 5),
			Block.box(7.5, 6, 1, 8.5, 16, 8),
			Block.box(6.5, 3, -2, 9.5, 6, 9),
			Block.box(7.5, 0, 9, 8.5, 9, 15)
	);
	public static final VoxelShape TRALALERO_TRALALA = Shapes.or(
			Block.box(5, 8, -6, 11, 12, 5),
			Block.box(5, 6, -3, 11, 8, 5),
			Block.box(4, 5, 5, 12, 12, 18),
			Block.box(6, 7, 18, 10, 12, 29),
			Block.box(5, 7.5, 26, 12, 14.5, 32),
			Block.box(7.5, 12, 11, 8.5, 16, 16),
			Block.box(7.5, 12, 21, 8.5, 15, 24),
			Block.box(6, 0, 17, 10, 4, 25),
			Block.box(0, 0, 3, 4, 4, 11),
			Block.box(12, 0, 3, 16, 4, 11)
	);
	public static final VoxelShape TUNG_TUNG_SAHUR = Shapes.or(
			Block.box(4, 20, 4, 12, 32, 12),
			Block.box(2.75, 25, 2.5, 13.25, 29, 4),
			Block.box(6, 23, 2, 10, 28, 4),
			Block.box(4, 12, 5, 12, 20, 11),
			Block.box(2, 8, 7, 4, 24, 9),
			Block.box(12, 8, 7, 14, 24, 9),
			Block.box(12, 8, -11, 15, 11, 7),
			Block.box(5, 2, 7, 7, 12, 9),
			Block.box(9, 2, 7, 11, 12, 9),
			Block.box(4, 0, 3, 12, 2, 10)
	);
	public static final VoxelShape CHIMPANZINI_BANANINI = Shapes.or(
			Block.box(4, 18, 4, 12, 32, 10),
			Block.box(5, 27, 1, 11, 32, 4),
			Block.box(1, 29, 6, 15, 32, 8),
			Block.box(3, 0, 2, 13, 18, 12),
			Block.box(5.5, 11.75, 11.25, 11.5, 18.25, 18.75),
			Block.box(5.5, 0, -3, 11.5, 13, -1),
			Block.box(5.5, 11.5, -3.5, 11.5, 18.5, 2),
			Block.box(3, 0, 10.5, 8.75, 13, 16.5),
			Block.box(6.75, 0, 11.5, 14.5, 18, 19.25),
			Block.box(-6, 0, 3, 3, 8, 11),
			Block.box(-13, 0, 4, -6, 5, 10),
			Block.box(-15, 3, 5, -13, 8, 8)
	);
}
