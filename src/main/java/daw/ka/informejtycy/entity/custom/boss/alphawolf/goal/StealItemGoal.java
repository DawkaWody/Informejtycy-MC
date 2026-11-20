package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfPhase;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public class StealItemGoal extends ActionGoal {
	private static final int COOLDOWN = 300;
	private static final float START_RANGE = 4.0f;
	private static final float STEAL_RANGE = 5.0f;
	private static final int PLACE_TICK = 22;

	public StealItemGoal(AlphaWolfEntity wolf) {
		super(wolf, AlphaWolfAction.STEAL_ITEM, COOLDOWN);
	}

	@Override
	protected @Nullable LivingEntity chooseTarget() {
		if (wolf.getTarget() instanceof Player current && canSteal(current, START_RANGE)) return current;
		return wolf.nearbyTargets(START_RANGE).stream()
				.filter(player -> canSteal(player, START_RANGE))
				.min(Comparator.comparingDouble(wolf::distanceToSqr)).orElse(null);
	}

	@Override
	protected boolean canStart(LivingEntity target) {
		return wolf.getPhase() == AlphaWolfPhase.UNMASKED && wolf.getStolenItem().isEmpty()
				&& target instanceof Player player && canSteal(player, START_RANGE);
	}

	@Override
	protected void hit(LivingEntity target) {
		if (!(target instanceof Player player) || !canSteal(player, STEAL_RANGE)) return;

		if (player.isUsingItem()) player.stopUsingItem();
		wolf.setStolenItem(player.getItemInHand(InteractionHand.MAIN_HAND).copyAndClear());
		player.containerMenu.broadcastChanges();
		wolf.playSound(SoundEvents.ITEM_PICKUP, 2.0f, 0.5f);
	}

	@Override
	public void tick() {
		super.tick();
		if (ticks == PLACE_TICK) wolf.placeStolenItem(getServerLevel(wolf));
	}

	@Override
	public void stop() {
		wolf.placeStolenItem(getServerLevel(wolf));
		super.stop();
	}

	private boolean canSteal(Player player, float range) {
		return !player.getMainHandItem().isEmpty() && isWithin(player, range);
	}
}
