package daw.ka.informejtycy.mixin;

import daw.ka.informejtycy.sound.CustomSounds;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class MixinVillagerSound {
    @Inject(method = "setUnhappy", at = @At("HEAD"), cancellable = true)
    private void playCustomNoSound(CallbackInfo ci) {
        Villager self = (Villager) (Object) this;
        self.setUnhappyCounter(40);
        if (!self.level().isClientSide()) {
            self.playSound(CustomSounds.ZALES, 1.0F, 1.0F);
        }
        ci.cancel();
    }
}
