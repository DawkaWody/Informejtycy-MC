package daw.ka.informejtycy.client.mixin;

import daw.ka.informejtycy.client.sound.LoopingMusicSound;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.sound.CustomSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(TitleScreen.class)
public abstract class MixinTitleScreen {
	@Unique private static final int FRAME_COUNT = 5391;
	@Unique private static final int FRAME_RATE = 30;
	@Unique private long startTime = -1;
	@Unique private final LoopingMusicSound customAudio = new LoopingMusicSound(CustomSounds.MENU_AUDIO, SoundCategory.VOICE, 0.8f);

	@Inject(method = "render", at = @At("HEAD"))
	private void playVideo(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
		if (startTime == -1) startTime = System.currentTimeMillis();

		MinecraftClient client = MinecraftClient.getInstance();
		TextureManager textureManager = client.getTextureManager();

		if (!client.getSoundManager().isPlaying(customAudio)) {
			client.getSoundManager().play(customAudio);
		}

		long elapsedTime = System.currentTimeMillis() - startTime;
		int currentFrame = ((int) ((elapsedTime / 1000.0) * FRAME_RATE) % FRAME_COUNT) + 1;
		String frameName = String.format("frame_%04d", currentFrame);

		Identifier frameId = InformejtycyRegistry.id("textures/gui/video/" + frameName + ".png");
		ResourceTexture frameTexture = new ResourceTexture(frameId);
		textureManager.registerTexture(frameId, frameTexture);
		try {
			frameTexture.loadContents(client.getResourceManager());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		int width = client.getWindow().getScaledWidth();
		int height = client.getWindow().getScaledHeight();

		context.drawTexture(RenderPipelines.GUI_TEXTURED, frameId, 0, 0, 0, 0, width, height, width, height);
	}
}
