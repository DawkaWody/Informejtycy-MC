package daw.ka.informejtycy.item.custom;

import daw.ka.informejtycy.potion.effect.CustomEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ShriekTalismanItem extends Item {
	private static final int DURATION = 1200;
	private static final int COOLDOWN = 2400;

	public ShriekTalismanItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		user.addStatusEffect(new StatusEffectInstance(CustomEffects.SNEAKINESS, DURATION));
		user.getItemCooldownManager().set(stack, COOLDOWN);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_SCULK_SHRIEKER_SHRIEK, user.getSoundCategory(), 0.8F, 1.0F);

		return super.use(world, user, hand);
	}
}
