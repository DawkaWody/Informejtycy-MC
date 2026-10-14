package daw.ka.informejtycy.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import daw.ka.informejtycy.item.custom.VapeItem;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FirstPersonHandsAndItemsRenderer.class)
public abstract class MixinVapeAnimation {
	@WrapOperation(method = "submitArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/ItemUseAnimation;"))
	private ItemUseAnimation drinkVapeInFirstPerson(ItemStack itemStack, Operation<ItemUseAnimation> original) {
		return itemStack.getItem() instanceof VapeItem ? ItemUseAnimation.DRINK : original.call(itemStack);
	}
}
