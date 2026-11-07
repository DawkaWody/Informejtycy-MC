package daw.ka.informejtycy.client.gui;

import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.client.sound.MenuMusicSound;
import daw.ka.informejtycy.sound.CustomSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.SoundEngine;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

public class TitleScreenVideo {
	private static final int FRAME_COUNT = 4314;
	private static final int FRAME_RATE = 24;
	private static final int FRAME_WIDTH = 200;
	private static final int FRAME_HEIGHT = 112;
	private static final Identifier TEXTURE_ID = InformejtycyRegistry.id("title_video");
	private static final Identifier VIDEO_ID = InformejtycyRegistry.id("textures/gui/video/title.mp4");
	private static final long AUDIO_RETRY_MS = 5000;

	private static DynamicTexture texture;
	private static TitleVideoDecoder decoder;

	private static MenuMusicSound audio;
	private static boolean audioStarted = false;
	private static boolean audioFailed = false;
	private static long audioRetryAt = 0;
	private static long startTime = -1;

	public static void render(GuiGraphicsExtractor context, int width, int height) {
		Minecraft client = Minecraft.getInstance();
		if (audio == null) {
			start(client);
		}
		syncToAudio(client.getSoundManager());

		long elapsedTime = System.currentTimeMillis() - startTime;
		int frame = (int) (elapsedTime * FRAME_RATE / 1000);
		if (audioStarted) {
			frame = Math.min(frame, FRAME_COUNT - 1);
		} else {
			frame %= FRAME_COUNT;
		}

		updateTexture(client, frame);
		if (texture == null) return;

		context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE_ID, 0, 0, 0, 0, width, height,
				FRAME_WIDTH, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
	}

	public static boolean isPlaying() {
		return audio != null;
	}

	public static void stop() {
		if (audio == null) return;

		Minecraft client = Minecraft.getInstance();
		client.getSoundManager().stop(audio);
		audio = null;

		if (decoder != null) {
			decoder.stop();
			decoder = null;
		}
		if (texture != null) {
			client.getTextureManager().release(TEXTURE_ID);
			texture = null;
		}
		startTime = -1;
	}

	private static void start(Minecraft client) {
		client.getMusicManager().stopPlaying();
		decoder = new TitleVideoDecoder(client.getResourceManager(), VIDEO_ID, FRAME_COUNT);
		playAudio(client.getSoundManager());
	}

	private static void playAudio(SoundManager soundManager) {
		audio = new MenuMusicSound(CustomSounds.MENU_AUDIO, 0.8f);
		audioFailed = soundManager.play(audio) == SoundEngine.PlayResult.NOT_STARTED;
		audioStarted = false;
		if (audioFailed) {
			audioRetryAt = System.currentTimeMillis() + AUDIO_RETRY_MS;
			if (startTime < 0) startTime = System.currentTimeMillis();
		} else {
			restartVideo();
		}
	}

	private static void syncToAudio(SoundManager soundManager) {
		if (audioFailed) {
			if (System.currentTimeMillis() >= audioRetryAt) {
				playAudio(soundManager);
			}
			return;
		}

		boolean playing = soundManager.isActive(audio);
		if (playing && !audioStarted) {
			audioStarted = true;
			restartVideo();
		} else if (!playing && audioStarted) {
			playAudio(soundManager);
		}
	}

	private static void restartVideo() {
		startTime = System.currentTimeMillis();
		decoder.restart();
	}

	private static void updateTexture(Minecraft client, int frame) {
		int[] pixels = decoder.poll(frame);
		if (pixels == null) return;

		if (texture == null) {
			texture = new DynamicTexture(() -> "Informejtycy title video", FRAME_WIDTH, FRAME_HEIGHT, false);
			client.getTextureManager().register(TEXTURE_ID, texture);
		}

		NativeImage image = texture.getPixels();
		for (int y = 0; y < FRAME_HEIGHT; y++) {
			for (int x = 0; x < FRAME_WIDTH; x++) {
				image.setPixel(x, y, pixels[y * FRAME_WIDTH + x]);
			}
		}
		texture.upload();
	}
}
