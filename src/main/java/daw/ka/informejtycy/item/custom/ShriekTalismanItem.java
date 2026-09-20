package daw.ka.informejtycy.item.custom;

import com.mojang.serialization.Codec;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.potion.effect.CustomEffects;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class ShriekTalismanItem extends Item {
	private static final int DURATION = 1200;
	private static final int COOLDOWN = 2400;

	public static final AttachmentType<Long> COOLDOWN_END = AttachmentRegistry.create(
			InformejtycyRegistry.id("talisman_of_shriek_cooldown"),
			builder -> builder.persistent(Codec.LONG).copyOnDeath());

	public ShriekTalismanItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		if (remainingCooldown(world, user) > 0) return ActionResult.FAIL;

		ItemStack stack = user.getStackInHand(hand);
		user.addStatusEffect(new StatusEffectInstance(CustomEffects.SNEAKINESS, DURATION));
		user.getItemCooldownManager().set(stack, COOLDOWN);
		if (!world.isClient()) {
			user.setAttached(COOLDOWN_END, world.getTime() + COOLDOWN);
		}
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, user.getSoundCategory(), 0.8F, 1.0F);

		return super.use(world, user, hand);
	}

	@Override
	public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
		if (!(entity instanceof PlayerEntity player)) return;

		long remaining = remainingCooldown(world, player);
		if (remaining > 0 && !player.getItemCooldownManager().isCoolingDown(stack)) {
			player.getItemCooldownManager().set(stack, (int) remaining);
		} else if (remaining <= 0 && player.hasAttached(COOLDOWN_END)) {
			player.removeAttached(COOLDOWN_END);
		}
	}

	private static long remainingCooldown(World world, PlayerEntity player) {
		Long end = player.getAttached(COOLDOWN_END);
		return end == null ? 0 : end - world.getTime();
	}
}
