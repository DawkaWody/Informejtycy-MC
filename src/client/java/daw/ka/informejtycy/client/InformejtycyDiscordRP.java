package daw.ka.informejtycy.client;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import daw.ka.informejtycy.Informejtycy;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Properties;

public class InformejtycyDiscordRP {
	private static IPCClient client;
	private static boolean ready = false;

	private static OffsetDateTime startTimestamp;
    private static String nickname;
	private static final String STATE_MENU = "In menu";
	private static final String STATE_SINGLEPLAYER = "Singleplayer";
	private static final String STATE_MULTIPLAYER = "Multiplayer: %s";
    private static final String DETAILS = "Nickname: %s";
	private static final String LARGE_IMAGE = "Mod version: %s";
	private static final String SMALL_IMAGE = "Minecraft %s";

	private static void setRpMenu() {
		RichPresence.Builder presenceBuilder = new RichPresence.Builder();
		presenceBuilder.setDetails(String.format(DETAILS, nickname))
                .setState(STATE_MENU)
				.setLargeImage("logo", String.format(LARGE_IMAGE, getModVersion()))
				.setSmallImage("minecraft", String.format(SMALL_IMAGE, getMinecraftVersion()))
				.setStartTimestamp(startTimestamp);
		client.sendRichPresence(presenceBuilder.build());
	}

	private static void setRpSingleplayer() {
		RichPresence.Builder presenceBuilder = new RichPresence.Builder();
		presenceBuilder.setDetails(String.format(DETAILS, nickname))
                .setState(STATE_SINGLEPLAYER)
				.setLargeImage("logo", String.format(LARGE_IMAGE, getModVersion()))
				.setSmallImage("minecraft", String.format(SMALL_IMAGE, getMinecraftVersion()))
				.setStartTimestamp(startTimestamp);
		client.sendRichPresence(presenceBuilder.build());
	}

	private static void setRpMultiplayer(String serverAddr) {
		RichPresence.Builder presenceBuilder = new RichPresence.Builder();
		presenceBuilder.setDetails(String.format(DETAILS, nickname))
                .setState(String.format(STATE_MULTIPLAYER, serverAddr))
				.setLargeImage("logo", String.format(LARGE_IMAGE, getModVersion()))
				.setSmallImage("minecraft", String.format(SMALL_IMAGE, getMinecraftVersion()))
				.setStartTimestamp(startTimestamp);
		client.sendRichPresence(presenceBuilder.build());
	}

	public static void init() {
		client = new IPCClient(1393888932525244487L);
		client.setListener(new IPCListener() {
			@Override
			public void onReady(IPCClient client) {
				ready = true;
				startTimestamp = OffsetDateTime.now();
                nickname = MinecraftClient.getInstance().getSession().getUsername();
				Informejtycy.LOGGER.info("Connected to Discord {}", client.getDiscordBuild());
			}
		});
		try {
			client.connect();
		} catch (NoDiscordClientException | RuntimeException e) {
			Informejtycy.LOGGER.warn("No opened Discord client found");
		}
	}

	public static void update() {
		if (!ready) return;

		MinecraftClient client = MinecraftClient.getInstance();
		ServerInfo server = client.getCurrentServerEntry();

		if (client.isInSingleplayer() || client.isIntegratedServerRunning()) {
			setRpSingleplayer();
		}
		else if (server != null) {
			setRpMultiplayer(server.address);
		}
		else {
			setRpMenu();
		}
	}

	public static void stop() {
		client.close();
	}

	private static String getModVersion() {
		Properties p = new Properties();
		try (InputStream in = InformejtycyDiscordRP.class.getResourceAsStream("/version/version.properties")) {
			p.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return p.getProperty("mod_version");
	}

	private static String getMinecraftVersion() {
		Properties p = new Properties();
		try (InputStream in = InformejtycyDiscordRP.class.getResourceAsStream("/version/version.properties")) {
			p.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return p.getProperty("minecraft_version");
	}
}
