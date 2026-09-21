package daw.ka.informejtycy.mixin;

import daw.ka.informejtycy.potion.effect.CustomEffects;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkShriekerBlockEntity.class)
public abstract class MixinShriekPrevent {
	@Inject(method = "shriek(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
	private void cancelShriekIfTalisman(ServerLevel level, Entity entity, CallbackInfo ci) {
		if (entity instanceof LivingEntity living) {
			if (living.hasEffect(CustomEffects.SNEAKINESS)) {
				ci.cancel();
			}
		}
	}
}
