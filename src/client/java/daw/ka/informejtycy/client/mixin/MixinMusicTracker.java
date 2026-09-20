package daw.ka.informejtycy.client.mixin;

import daw.ka.informejtycy.client.gui.TitleScreenVideo;
import net.minecraft.client.sound.MusicTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public abstract class MixinMusicTracker {
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void pauseForTitleVideo(CallbackInfo ci) {
		if (TitleScreenVideo.isPlaying()) {
			ci.cancel();
		}
	}
}
