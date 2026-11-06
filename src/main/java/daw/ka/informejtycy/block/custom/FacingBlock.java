package daw.ka.informejtycy.block.custom;

import daw.ka.informejtycy.block.CustomShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public class FacingBlock extends HorizontalDirectionalBlock {
	public static final Property<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

	private final Map<Direction, VoxelShape> shapes;
	private final Map<Direction, VoxelShape> collisionShapes = new EnumMap<>(Direction.class);

	public FacingBlock(Properties settings) {
		this(settings, Shapes.block());
	}

	/**
	 * @param northShape outline when facing north, rotated for the other directions.
	 *                   Collision is clipped to the block's own space.
	 */
	public FacingBlock(Properties settings, VoxelShape northShape) {
		super(settings);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
		shapes = Shapes.rotateHorizontal(CustomShapes.clampToInteractionBounds(northShape));
		shapes.forEach((direction, shape) ->
				collisionShapes.put(direction, Shapes.join(shape, Shapes.block(), BooleanOp.AND)));
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		super.createBlockStateDefinition(builder);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return shapes.get(state.getValue(FACING));
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return hasCollision ? collisionShapes.get(state.getValue(FACING)) : Shapes.empty();
	}
}
