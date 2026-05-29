package daw.ka.informejtycy.client.anticheat;

import com.google.gson.Gson;
import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.anticheat.server.AnticheatServer;
import daw.ka.informejtycy.anticheat.server.payload.ModVerificationPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.ModOrigin;
import net.minecraft.network.codec.PacketCodecs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InformejtycyAnticheatClient {
    public static final int HASH_BUFFER_SIZE = 8192;
    public static final String HASH_ALGORITHM = "SHA-256";
    private static Map<String, String> CLIENT_HASHES;
    private static final Gson GSON = new Gson();

    public static void init() {
        new Thread(() -> {
            Informejtycy.LOGGER.info("Hashing loaded mods...");
            CLIENT_HASHES = hashMods();
        }, "Informejtycy-Anticheat").start();

        PayloadTypeRegistry.playS2C().register(AnticheatServer.HANDSHAKE_CHANNEL, PacketCodecs.STRING.xmap(ModVerificationPayload::new, ModVerificationPayload::json));
        PayloadTypeRegistry.playC2S().register(AnticheatServer.HANDSHAKE_CHANNEL, PacketCodecs.STRING.xmap(ModVerificationPayload::new, ModVerificationPayload::json));

        ClientPlayNetworking.registerGlobalReceiver(AnticheatServer.HANDSHAKE_CHANNEL, (payload, context) -> {
            Map<String, String> clientHashes = CLIENT_HASHES;
            if (clientHashes == null) clientHashes = new HashMap<>();
            String json = GSON.toJson(clientHashes);
            context.responseSender().sendPacket(new ModVerificationPayload(json));
        });
    }

    private static Map<String, String> hashMods() {
        Map<String, String> hashes = new HashMap<>();
        Collection<ModContainer> mods = FabricLoader.getInstance().getAllMods();
        Informejtycy.LOGGER.info("Detected {} mods", mods.size());

        for (ModContainer mod : mods) {
            ModMetadata metadata = mod.getMetadata();
            String modId = metadata.getId();
            if (mod.getOrigin().getKind() != ModOrigin.Kind.NESTED) {
                Path path = mod.getOrigin().getPaths().getFirst();
                if (Files.isRegularFile(path) && Files.isReadable(path)) {
                    Informejtycy.LOGGER.info("Hashing mod {} from {}", modId, path);
                    String hash = hashFile(path);
                    hashes.put(modId, hash);
                }
                else {
                    Informejtycy.LOGGER.warn("Cannot hash mod {} - not a readable file ({})", modId, path);
                    hashes.put(modId, null);
                }
            }
        }

        return hashes;
    }

    private static String hashFile(Path path) {
        try (InputStream is = Files.newInputStream(path)) {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] buffer = new byte[HASH_BUFFER_SIZE];
            int read;
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }

            byte[] hashBytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
