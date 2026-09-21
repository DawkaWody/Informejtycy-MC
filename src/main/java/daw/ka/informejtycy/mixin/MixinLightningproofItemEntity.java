package daw.ka.informejtycy.mixin;

import daw.ka.informejtycy.item.CustomItems;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class MixinLightningproofItemEntity {
	@Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
	private void disableLightningDamage(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
		ItemEntity self = (ItemEntity) (Object) this;
		ItemStack stack = self.getItem();
		if (stack.getItem() == CustomItems.RIDE_THE_LIGHTNING_MUSIC_DISC && source.is(DamageTypes.LIGHTNING_BOLT)) {
			cir.cancel();
		}
	}
}
