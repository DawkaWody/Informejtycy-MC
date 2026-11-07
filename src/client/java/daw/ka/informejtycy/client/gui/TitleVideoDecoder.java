package daw.ka.informejtycy.client.gui;

import daw.ka.informejtycy.Informejtycy;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.ByteBufferSeekableByteChannel;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.ColorUtil;
import org.jcodec.scale.Transform;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class TitleVideoDecoder implements Runnable {
	private static final int QUEUE_SIZE = 8;
	private static final long OFFER_TIMEOUT_MS = 50;

	private record Frame(int index, int[] pixels) {}

	private final ResourceManager resourceManager;
	private final Identifier videoId;
	private final int frameCount;
	private final BlockingQueue<Frame> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
	private final Thread thread;
	private volatile boolean running = true;
	private volatile boolean restartRequested = false;

	public TitleVideoDecoder(ResourceManager resourceManager, Identifier videoId, int frameCount) {
		this.resourceManager = resourceManager;
		this.videoId = videoId;
		this.frameCount = frameCount;
		thread = new Thread(this, "Informejtycy title video decoder");
		thread.setDaemon(true);
		thread.start();
	}

	public int[] poll(int targetFrame) {
		if (restartRequested) return null;

		int[] latest = null;
		Frame head;
		while ((head = queue.peek()) != null && Math.floorMod(targetFrame - head.index, frameCount) < frameCount / 2) {
			latest = queue.poll().pixels;
		}
		return latest;
	}

	public void restart() {
		restartRequested = true;
	}

	public void stop() {
		running = false;
		thread.interrupt();
	}

	@Override
	public void run() {
		try {
			byte[] data;
			try (InputStream stream = resourceManager.open(videoId)) {
				data = stream.readAllBytes();
			}
			while (running) {
				if (restartRequested) {
					queue.clear();
					restartRequested = false;
				}
				decode(data);
			}
		} catch (InterruptedException ignored) {
		} catch (Exception e) {
			if (running) Informejtycy.LOGGER.error("Failed to decode title video", e);
		}
	}

	private void decode(byte[] data) throws IOException, JCodecException, InterruptedException {
		FrameGrab grab = FrameGrab.createFrameGrab(ByteBufferSeekableByteChannel.readFromByteBuffer(ByteBuffer.wrap(data)));
		Picture rgb = null;
		Transform transform = null;
		int index = 0;

		Picture picture;
		while ((picture = grab.getNativeFrame()) != null) {
			if (rgb == null) {
				rgb = Picture.create(picture.getWidth(), picture.getHeight(), ColorSpace.RGB);
				transform = ColorUtil.getTransform(picture.getColor(), ColorSpace.RGB);
			}
			transform.transform(picture, rgb);

			Frame frame = new Frame(index++ % frameCount, toArgb(rgb, picture.getCroppedWidth(), picture.getCroppedHeight()));
			while (!queue.offer(frame, OFFER_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
				if (!running || restartRequested) return;
			}
			if (!running || restartRequested) return;
		}
	}

	private static int[] toArgb(Picture rgb, int width, int height) {
		byte[] data = rgb.getPlaneData(0);
		int stride = rgb.getWidth() * 3;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			int src = y * stride;
			int dst = y * width;
			for (int x = 0; x < width; x++, src += 3) {
				int r = data[src] + 128;
				int g = data[src + 1] + 128;
				int b = data[src + 2] + 128;
				pixels[dst + x] = 0xFF000000 | r << 16 | g << 8 | b;
			}
		}
		return pixels;
	}
}
