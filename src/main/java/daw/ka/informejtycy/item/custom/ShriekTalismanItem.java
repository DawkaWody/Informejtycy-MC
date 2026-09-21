package daw.ka.informejtycy.item.custom;

import com.mojang.serialization.Codec;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.potion.effect.CustomEffects;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class ShriekTalismanItem extends Item {
	private static final int DURATION = 1200;
	private static final int COOLDOWN = 2400;

	public static final AttachmentType<Long> COOLDOWN_END = AttachmentRegistry.create(
			InformejtycyRegistry.id("talisman_of_shriek_cooldown"),
			builder -> builder.persistent(Codec.LONG).copyOnDeath());

	public ShriekTalismanItem(Properties settings) {
		super(settings);
	}

	@Override
	public InteractionResult use(Level world, Player user, InteractionHand hand) {
		if (remainingCooldown(world, user) > 0) return InteractionResult.FAIL;

		ItemStack stack = user.getItemInHand(hand);
		user.addEffect(new MobEffectInstance(CustomEffects.SNEAKINESS, DURATION));
		user.getCooldowns().addCooldown(stack, COOLDOWN);
		if (!world.isClientSide()) {
			user.setAttached(COOLDOWN_END, world.getGameTime() + COOLDOWN);
		}
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.SCULK_SHRIEKER_SHRIEK, user.getSoundSource(), 0.8F, 1.0F);

		return super.use(world, user, hand);
	}

	@Override
	public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
		if (!(entity instanceof Player player)) return;

		long remaining = remainingCooldown(world, player);
		if (remaining > 0 && !player.getCooldowns().isOnCooldown(stack)) {
			player.getCooldowns().addCooldown(stack, (int) remaining);
		} else if (remaining <= 0 && player.hasAttached(COOLDOWN_END)) {
			player.removeAttached(COOLDOWN_END);
		}
	}

	private static long remainingCooldown(Level world, Player player) {
		Long end = player.getAttached(COOLDOWN_END);
		return end == null ? 0 : end - world.getGameTime();
	}
}
