package daw.ka.informejtycy.entity.custom.boss.alphawolf.goal;

import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfAction;
import daw.ka.informejtycy.entity.custom.boss.alphawolf.AlphaWolfEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RoarGoal extends ActionGoal {
	private static final int COOLDOWN = 400;
	private static final float RADIUS = 15.0f;
	private static final int GLOWING_TICKS = 200;
	private static final int SHIELD_COOLDOWN = 100;

	public RoarGoal(AlphaWolfEntity wolf) {
		super(wolf, AlphaWolfAction.ROAR, COOLDOWN);
	}

	@Override
	protected boolean canStart(LivingEntity target) {
		return isWithin(target, RADIUS);
	}

	@Override
	protected void hit(LivingEntity target) {
		wolf.playSound(SoundEvents.WARDEN_ROAR, 4.0f, 0.7f);
		getServerLevel(wolf).sendParticles(ParticleTypes.SONIC_BOOM, wolf.getX(), wolf.getY() + wolf.getBbHeight() * 0.85, wolf.getZ(),
				1, 0, 0, 0, 0);

		for (Player player : wolf.nearbyTargets(RADIUS)) {
			player.addEffect(new MobEffectInstance(MobEffects.GLOWING, GLOWING_TICKS), wolf);
			if (player.isFallFlying()) {
				player.stopFallFlying();
				player.push(0, -1.0, 0);
				player.needsSync = true;
			}
			for (InteractionHand hand : InteractionHand.values()) {
				ItemStack stack = player.getItemInHand(hand);
				if (stack.has(DataComponents.BLOCKS_ATTACKS)) player.getCooldowns().addCooldown(stack, SHIELD_COOLDOWN);
			}
			if (player.isBlocking()) player.stopUsingItem();
		}
	}
}
