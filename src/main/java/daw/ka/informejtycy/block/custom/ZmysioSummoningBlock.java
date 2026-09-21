package daw.ka.informejtycy.block.custom;

import daw.ka.informejtycy.event.custom.ZmysioSpawnEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * A block that is part of the Zmysio summoning altar (glinianka and trash can).
 * Only runs the summon check after the block has actually been placed by a player.
 */
public class ZmysioSummoningBlock extends FacingBlock {

	public ZmysioSummoningBlock(Properties settings) {
		super(settings);
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.setPlacedBy(world, pos, state, placer, itemStack);
		if (world instanceof ServerLevel serverWorld) {
			ZmysioSpawnEvent.trySummonZmysio(serverWorld, pos, this);
		}
	}

}
