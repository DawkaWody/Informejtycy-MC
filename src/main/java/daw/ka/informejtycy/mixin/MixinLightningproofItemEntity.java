package daw.ka.informejtycy.mixin;

import daw.ka.informejtycy.item.CustomItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ItemEntity.class)
public abstract class MixinLightningproofItemEntity {
	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private void disableLightningDamage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		ItemEntity self = (ItemEntity) (Object) this;
		ItemStack stack = self.getStack();
		if (stack.getItem() == CustomItems.RIDE_THE_LIGHTNING_MUSIC_DISC) {
			if (Objects.equals(source.getName(), "lightningBolt")) {
				cir.cancel();
			}
		}
	}
}
