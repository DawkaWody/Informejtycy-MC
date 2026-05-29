package daw.ka.informejtycy.client.mixin;

import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.URI;

@Mixin(ConfirmLinkScreen.class)
public abstract class MixinConfirmLinkScreen {
	@Inject(method = "open(Lnet/minecraft/client/gui/screen/Screen;Ljava/net/URI;Z)V", at = @At("HEAD"), cancellable = true)
	private static void bypassLinkConfirmation(Screen parent, URI uri, boolean linkTrusted, CallbackInfo ci) {
		Util.getOperatingSystem().open(uri);
		ci.cancel();
	}

	@Inject(method = "open(Lnet/minecraft/client/gui/screen/Screen;Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true)
	private static void bypassLinkConfirmation(Screen parent, String uri, boolean linkTrusted, CallbackInfo ci) {
		Util.getOperatingSystem().open(uri);
		ci.cancel();
	}
}
