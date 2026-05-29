package daw.ka.informejtycy.mixin;

import daw.ka.informejtycy.sound.CustomSounds;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class MixinVillagerSound {
    @Inject(method = "sayNo", at = @At("HEAD"), cancellable = true)
    private void playCustomNoSound(CallbackInfo ci) {
        VillagerEntity self = (VillagerEntity) (Object) this;
        self.setHeadRollingTimeLeft(40);
        if (!self.getEntityWorld().isClient()) {
            self.playSound(CustomSounds.ZALES, 1.0F, 1.0F);
        }
        ci.cancel();
    }
}
