package daw.ka.informejtycy.anticheat.server;

import com.google.gson.Gson;
import daw.ka.informejtycy.Informejtycy;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class AnticheatConfig {
    private static final Gson GSON = new Gson();
    private static final String CONFIG_FILENAME = "allowed_mods.json";
    public static ConfigData DATA;

    public static void load() {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path file = configDir.resolve(CONFIG_FILENAME);
        Informejtycy.LOGGER.info("Loading anticheat config from {}", file);

        if (!Files.exists(file)) {
            try (FileWriter writer = new FileWriter(file.toFile())){
                GSON.toJson(ConfigData.getDefault(), writer);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try (FileReader reader = new FileReader(file.toFile())){
            DATA = GSON.fromJson(reader, ConfigData.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class ConfigData {
        public Map<String, String> allowedHashes;
        public List<String> allowedIds;
        public long timeout;

        public static ConfigData getDefault() {
            ConfigData cd = new ConfigData();
            cd.allowedHashes = Map.of("fabric-api", "4dd5d07067d5cf3e874d2780806c3da223819d39b28d6296c1005a36f2d0a5e4");
            cd.allowedIds = List.of("minecraft", "fabricloader", "java", Informejtycy.MOD_ID);
            cd.timeout = 1500;
            return cd;
        }
    }
}
