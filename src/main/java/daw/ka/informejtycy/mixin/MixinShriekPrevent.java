package daw.ka.informejtycy.mixin;

import daw.ka.informejtycy.potion.effect.CustomEffects;
import net.minecraft.block.entity.SculkShriekerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkShriekerBlockEntity.class)
public abstract class MixinShriekPrevent {
	@Inject(method = "shriek(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
	private void cancelShriekIfTalisman(ServerWorld world, Entity entity, CallbackInfo ci) {
		if (entity instanceof LivingEntity living) {
			if (living.hasStatusEffect(CustomEffects.SNEAKINESS)) {
				ci.cancel();
			}
		}
	}
}
