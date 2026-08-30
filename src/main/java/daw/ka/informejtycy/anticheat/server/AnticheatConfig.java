package daw.ka.informejtycy.anticheat.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daw.ka.informejtycy.Informejtycy;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnticheatConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILENAME = "allowed_mods.json";
    private static final int CURRENT_VERSION = 2;
    private static final long SANE_MINIMUM_TIMEOUT_MS = 60000L;

    public static ConfigData DATA;

    public static void load() {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path file = configDir.resolve(CONFIG_FILENAME);
        Informejtycy.LOGGER.info("Loading anticheat config from {}", file);

        if (!Files.exists(file)) {
            save(file, ConfigData.getDefault());
        }

        try (FileReader reader = new FileReader(file.toFile())) {
            DATA = GSON.fromJson(reader, ConfigData.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (DATA == null) {
            DATA = ConfigData.getDefault();
        }

        DATA.fillDefaults();
        if (DATA.migrateTimings()) {
            save(file, DATA);
            Informejtycy.LOGGER.info("[Anticheat] Updated the handshake timings in {} for this version", file);
        }

        if (DATA.timeout < SANE_MINIMUM_TIMEOUT_MS) {
            Informejtycy.LOGGER.warn("[Anticheat] timeout is {}ms. If this server has a login mod, a player "
                    + "cannot answer before logging in and will be recorded as TIMEOUT", DATA.timeout);
        }
    }

    private static void save(Path file, ConfigData data) {
        try (FileWriter writer = new FileWriter(file.toFile())) {
            GSON.toJson(data, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class ConfigData {
        public int configVersion;

        public boolean enforce;
        public boolean requireAttestation;
        public boolean whitelistMode;

        public Map<String, String> allowedHashes;
        public List<String> allowedIds;
        public List<String> blockedIds;
        public List<String> blockedHashes;
        public List<String> trustedMixinOwners;

        public long timeout;
        public long maxLatencyMs;
        public long maxCollectMs;
        public int measuredClassCount;

        public static ConfigData getDefault() {
            ConfigData config = new ConfigData();
            config.configVersion = CURRENT_VERSION;
            config.enforce = false;
            config.requireAttestation = false;
            config.whitelistMode = false;
            config.allowedHashes = new HashMap<>(Map.of("fabric-api", "4dd5d07067d5cf3e874d2780806c3da223819d39b28d6296c1005a36f2d0a5e4"));
            config.allowedIds = new ArrayList<>(List.of("minecraft", "fabricloader", "fabric-api", "java", "mixinextras", Informejtycy.MOD_ID));
            config.blockedIds = new ArrayList<>(List.of("meteorclient", "wurst", "baritone", "xaerominimap", "impact", "future"));
            config.blockedHashes = new ArrayList<>();
            config.trustedMixinOwners = new ArrayList<>(List.of("minecraft", "fabricloader", Informejtycy.MOD_ID));
            config.timeout = 180000L;
            config.maxLatencyMs = 0L;
            config.maxCollectMs = 60000L;
            config.measuredClassCount = 12;
            return config;
        }

        public boolean migrateTimings() {
            if (configVersion >= CURRENT_VERSION) {
                return false;
            }

            ConfigData defaults = getDefault();
            timeout = defaults.timeout;
            maxLatencyMs = defaults.maxLatencyMs;
            maxCollectMs = defaults.maxCollectMs;
            configVersion = CURRENT_VERSION;
            return true;
        }

        public void fillDefaults() {
            ConfigData defaults = getDefault();
            if (allowedHashes == null) allowedHashes = defaults.allowedHashes;
            if (allowedIds == null) allowedIds = defaults.allowedIds;
            if (blockedIds == null) blockedIds = defaults.blockedIds;
            if (blockedHashes == null) blockedHashes = defaults.blockedHashes;
            if (trustedMixinOwners == null) trustedMixinOwners = defaults.trustedMixinOwners;
            if (timeout <= 0L) timeout = defaults.timeout;
            if (maxCollectMs < 0L) maxCollectMs = defaults.maxCollectMs;
            if (measuredClassCount <= 0) measuredClassCount = defaults.measuredClassCount;
        }
    }
}
