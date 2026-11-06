package daw.ka.informejtycy.block.custom;

import daw.ka.informejtycy.block.CustomShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class ShapedBlock extends Block {
	private final VoxelShape shape;

	public ShapedBlock(Properties settings, VoxelShape shape) {
		super(settings);
		this.shape = CustomShapes.clampToInteractionBounds(shape);
	}

	@Override
	protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter world, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return shape;
	}
}
