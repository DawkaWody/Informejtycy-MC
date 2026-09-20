package daw.ka.informejtycy.client;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import daw.ka.informejtycy.Informejtycy;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Properties;

public class InformejtycyDiscordRP {
	private static IPCClient client;
	private static volatile boolean ready = false;
	private static boolean loggedMissingDiscord = false;
	private static final Properties VERSIONS = loadVersions();

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
				loggedMissingDiscord = false;
				startTimestamp = OffsetDateTime.now();
                nickname = MinecraftClient.getInstance().getSession().getUsername();
				Informejtycy.LOGGER.info("Connected to Discord {}", client.getDiscordBuild());
			}

			@Override
			public void onClose(IPCClient client, JSONObject json) {
				ready = false;
			}

			@Override
			public void onDisconnect(IPCClient client, Throwable t) {
				ready = false;
			}
		});
	}

	private static void connect() {
		try {
			client.connect();
		} catch (NoDiscordClientException | RuntimeException e) {
			if (!loggedMissingDiscord) {
				Informejtycy.LOGGER.warn("No opened Discord client found, will keep trying");
				loggedMissingDiscord = true;
			}
		}
	}

	public static void update() {
		if (!ready) {
			connect();
			if (!ready) return;
		}

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
		if (ready) {
			ready = false;
			client.close();
		}
	}

	private static String getModVersion() {
		return VERSIONS.getProperty("mod_version", "unknown");
	}

	private static String getMinecraftVersion() {
		return VERSIONS.getProperty("minecraft_version", "unknown");
	}

	private static Properties loadVersions() {
		Properties p = new Properties();
		try (InputStream in = InformejtycyDiscordRP.class.getResourceAsStream("/version/version.properties")) {
			if (in != null) {
				p.load(in);
			}
		} catch (IOException e) {
			Informejtycy.LOGGER.warn("Could not read version.properties", e);
		}
		return p;
	}
}
