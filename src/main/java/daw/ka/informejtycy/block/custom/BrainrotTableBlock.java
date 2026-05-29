package daw.ka.informejtycy.block.custom;

import com.mojang.serialization.MapCodec;
import daw.ka.informejtycy.block.entity.CustomBlockEntities;
import daw.ka.informejtycy.block.entity.custom.BrainrotTableBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BrainrotTableBlock extends BlockWithEntity implements BlockEntityProvider {
	public static final VoxelShape SHAPE = Block.createCubeShape(16);
	public static final MapCodec<BrainrotTableBlock> CODEC = BrainrotTableBlock.createCodec(BrainrotTableBlock::new);
	public static final Property<Direction> FACING = Properties.HORIZONTAL_FACING;

	public BrainrotTableBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
		if (state.getBlock() != world.getBlockState(pos).getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BrainrotTableBlockEntity) {
				ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
				world.updateComparators(pos, this);
			}
			super.onStateReplaced(state, world, pos, moved);
		}
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
										 PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient()) {
			NamedScreenHandlerFactory screenHandlerFactory = (BrainrotTableBlockEntity) world.getBlockEntity(pos);
			if (screenHandlerFactory != null) {
				player.openHandledScreen(screenHandlerFactory);
			}
		}

		return ActionResult.SUCCESS;
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		if (world.isClient()) return null;
		return validateTicker(type, CustomBlockEntities.BRAINROT_TABLE_BLOCK_ENTITY_TYPE,
				(w, p, s, be) -> be.tick(w, p, s, be));
	}

	@Override
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayer().getHorizontalFacing().getOpposite());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
		super.appendProperties(builder);
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return CustomBlockEntities.BRAINROT_TABLE_BLOCK_ENTITY_TYPE.instantiate(pos, state);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
