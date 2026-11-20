package daw.ka.informejtycy.mixin;

import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Attributes.class)
public class MixinArmorCap {
	@ModifyConstant(method = "<clinit>", constant = @Constant(doubleValue = 30.0))
	private static double raiseArmorMax(double max) {
		return 100.0;
	}
}
