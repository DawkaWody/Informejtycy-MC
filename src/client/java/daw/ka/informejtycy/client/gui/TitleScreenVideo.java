package daw.ka.informejtycy.client.gui;

import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.client.sound.MenuMusicSound;
import daw.ka.informejtycy.sound.CustomSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class TitleScreenVideo {
	private static final int FRAME_COUNT = 4313;
	private static final int FRAME_RATE = 24;
	private static final int FRAME_WIDTH = 200;
	private static final int FRAME_HEIGHT = 113;
	private static final int SHEET_COLUMNS = 10;
	private static final int SHEET_ROWS = 10;
	private static final int FRAMES_PER_SHEET = SHEET_COLUMNS * SHEET_ROWS;
	private static final int SHEET_COUNT = (FRAME_COUNT + FRAMES_PER_SHEET - 1) / FRAMES_PER_SHEET;
	private static final Identifier TEXTURE_ID = InformejtycyRegistry.id("title_video");
	private static final long AUDIO_RETRY_MS = 5000;
	private static final long SHEET_RETRY_MS = 1000;

	private static NativeImageBackedTexture texture;
	private static int currentSheet = -1;
	private static CompletableFuture<NativeImage> nextSheet;
	private static int nextSheetIndex = -1;

	private static MenuMusicSound audio;
	private static boolean audioStarted = false;
	private static boolean audioFailed = false;
	private static long audioRetryAt = 0;
	private static long sheetRetryAt = 0;
	private static long startTime = -1;

	public static void render(DrawContext context, int width, int height) {
		MinecraftClient client = MinecraftClient.getInstance();
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

		updateSheet(client, frame / FRAMES_PER_SHEET);
		if (texture == null) return;

		int u = (frame % SHEET_COLUMNS) * FRAME_WIDTH;
		int v = (frame % FRAMES_PER_SHEET / SHEET_COLUMNS) * FRAME_HEIGHT;
		context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE_ID, 0, 0, u, v, width, height,
				FRAME_WIDTH, FRAME_HEIGHT, FRAME_WIDTH * SHEET_COLUMNS, FRAME_HEIGHT * SHEET_ROWS);
	}

	public static boolean isPlaying() {
		return audio != null;
	}

	public static void stop() {
		if (audio == null) return;

		MinecraftClient client = MinecraftClient.getInstance();
		client.getSoundManager().stop(audio);
		audio = null;

		if (texture != null) {
			client.getTextureManager().destroyTexture(TEXTURE_ID);
			texture = null;
		}
		currentSheet = -1;
		startTime = -1;
		if (nextSheet != null) {
			nextSheet.thenAccept(NativeImage::close);
			nextSheet = null;
			nextSheetIndex = -1;
		}
	}

	private static void start(MinecraftClient client) {
		client.getMusicTracker().stop();
		playAudio(client.getSoundManager());
	}

	private static void playAudio(SoundManager soundManager) {
		audio = new MenuMusicSound(CustomSounds.MENU_AUDIO, 0.8f);
		audioFailed = soundManager.play(audio) == SoundSystem.PlayResult.NOT_STARTED;
		audioStarted = false;
		if (audioFailed) {
			audioRetryAt = System.currentTimeMillis() + AUDIO_RETRY_MS;
			if (startTime < 0) startTime = System.currentTimeMillis();
		} else {
			startTime = System.currentTimeMillis();
		}
	}

	private static void syncToAudio(SoundManager soundManager) {
		if (audioFailed) {
			if (System.currentTimeMillis() >= audioRetryAt) {
				playAudio(soundManager);
			}
			return;
		}

		boolean playing = soundManager.isPlaying(audio);
		if (playing && !audioStarted) {
			audioStarted = true;
			startTime = System.currentTimeMillis();
		} else if (!playing && audioStarted) {
			playAudio(soundManager);
		}
	}

	private static void updateSheet(MinecraftClient client, int sheet) {
		if (sheet == currentSheet || System.currentTimeMillis() < sheetRetryAt) return;
		if (nextSheet == null || nextSheetIndex != sheet) {
			requestSheet(client.getResourceManager(), sheet);
		}

		NativeImage image;
		try {
			image = nextSheet.join();
		} catch (CompletionException e) {
			Informejtycy.LOGGER.error("Failed to load title video sheet {}", sheet, e);
			sheetRetryAt = System.currentTimeMillis() + SHEET_RETRY_MS;
			nextSheet = null;
			nextSheetIndex = -1;
			return;
		}
		currentSheet = sheet;
		nextSheet = null;
		nextSheetIndex = -1;

		if (texture == null) {
			texture = new NativeImageBackedTexture(() -> "Informejtycy title video", image);
			client.getTextureManager().registerTexture(TEXTURE_ID, texture);
		} else {
			texture.setImage(image);
			texture.upload();
		}

		requestSheet(client.getResourceManager(), (sheet + 1) % SHEET_COUNT);
	}

	private static void requestSheet(ResourceManager resourceManager, int sheet) {
		if (nextSheet != null) {
			nextSheet.thenAccept(NativeImage::close);
		}

		Identifier sheetId = InformejtycyRegistry.id(String.format("textures/gui/video/sheet_%02d.png", sheet));
		nextSheetIndex = sheet;
		nextSheet = CompletableFuture.supplyAsync(() -> {
			try (InputStream stream = resourceManager.open(sheetId)) {
				return NativeImage.read(stream);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}, Util.getMainWorkerExecutor());
	}
}
