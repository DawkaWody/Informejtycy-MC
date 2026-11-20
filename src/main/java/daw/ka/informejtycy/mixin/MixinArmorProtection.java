package daw.ka.informejtycy.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CombatRules.class)
public class MixinArmorProtection {
	@WrapOperation(method = "getDamageAfterAbsorb",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 0))
	private static float uncapEffectiveArmor(float value, float min, float max, Operation<Float> original) {
		float armor = Math.max(value, min);
		if (armor <= max) return original.call(value, min, max);
		return 25.0f * (1.0f - 4.0f / armor);
	}
}
