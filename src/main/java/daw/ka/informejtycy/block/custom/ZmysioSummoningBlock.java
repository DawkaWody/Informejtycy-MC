package daw.ka.informejtycy.block.custom;

import com.mojang.serialization.MapCodec;
import daw.ka.informejtycy.event.custom.ZmysioSpawnEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * A block that is part of the Zmysio summoning altar (glinianka and trash can).
 * Only runs the summon check after the block has actually been placed by a player.
 */
public class ZmysioSummoningBlock extends FacingBlock {
	public static final MapCodec<ZmysioSummoningBlock> CODEC = ZmysioSummoningBlock.createCodec(ZmysioSummoningBlock::new);

	public ZmysioSummoningBlock(Settings settings) {
		super(settings);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		if (world instanceof ServerWorld serverWorld) {
			ZmysioSpawnEvent.trySummonZmysio(serverWorld, pos, this);
		}
	}

	@Override
	protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
		return CODEC;
	}
}
