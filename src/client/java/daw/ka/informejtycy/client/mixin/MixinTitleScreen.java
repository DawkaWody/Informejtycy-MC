package daw.ka.informejtycy.client.mixin;

import daw.ka.informejtycy.client.gui.TitleScreenVideo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class MixinTitleScreen {
	@Inject(method = "render", at = @At("HEAD"))
	private void playVideo(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();
		int width = client.getWindow().getScaledWidth();
		int height = client.getWindow().getScaledHeight();

		TitleScreenVideo.render(context, width, height);
	}
}
