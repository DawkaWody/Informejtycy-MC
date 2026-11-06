package daw.ka.informejtycy.block.custom;

import daw.ka.informejtycy.event.custom.ZmysioSpawnEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ZmysioSummoningBlock extends FacingBlock {

	public ZmysioSummoningBlock(Properties settings) {
		super(settings);
	}

	public ZmysioSummoningBlock(Properties settings, VoxelShape northShape) {
		super(settings, northShape);
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.setPlacedBy(world, pos, state, placer, itemStack);
		if (world instanceof ServerLevel serverWorld) {
			ZmysioSpawnEvent.trySummonZmysio(serverWorld, pos, this);
		}
	}

}
