package daw.ka.informejtycy.anticheat.server;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import daw.ka.informejtycy.Informejtycy;
import daw.ka.informejtycy.InformejtycyRegistry;
import daw.ka.informejtycy.anticheat.server.payload.ModVerificationPayload;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InformejtycyAnticheatServer implements DedicatedServerModInitializer {
    public static final boolean FORCE_MODLIST = false;
    public static final Identifier HANDSHAKE_ID = InformejtycyRegistry.id("handshake");
    public static final CustomPayload.Id<ModVerificationPayload> HANDSHAKE_CHANNEL = new CustomPayload.Id<>(HANDSHAKE_ID);
    private static final Gson GSON = new Gson();
    private static final Set<UUID> pendingPlayers = ConcurrentHashMap.newKeySet();

    @Override
    public void onInitializeServer() {
        AnticheatConfig.load();

        PayloadTypeRegistry.playS2C().register(HANDSHAKE_CHANNEL, PacketCodecs.STRING.xmap(ModVerificationPayload::new, ModVerificationPayload::json));
        PayloadTypeRegistry.playC2S().register(HANDSHAKE_CHANNEL, PacketCodecs.STRING.xmap(ModVerificationPayload::new, ModVerificationPayload::json));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            UUID playerId = handler.player.getUuid();
            pendingPlayers.add(playerId);
            ServerPlayNetworking.send(handler.player, new ModVerificationPayload("{}"));

            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                if (pendingPlayers.contains(playerId)) {
                    handler.disconnect(Text.translatable("multiplayer.disconnect.handshake_timeout"));
                }
            }, AnticheatConfig.DATA.timeout, TimeUnit.MILLISECONDS);
        });

        ServerPlayNetworking.registerGlobalReceiver(HANDSHAKE_CHANNEL, (payload, context) -> {
            String playerName = context.player().getName().getString();
            Informejtycy.LOGGER.info("Received verification packet from {}", playerName);

            pendingPlayers.remove(context.player().getUuid());
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> clientHashes = GSON.fromJson(payload.json(), type);
            StringBuilder modList = new StringBuilder();
            for (Map.Entry<String, String> entry : clientHashes.entrySet()) {
                String modId = entry.getKey();
                String hash = entry.getValue();
                if (FORCE_MODLIST) {
                    if (!AnticheatConfig.DATA.allowedIds.contains(modId) &&
                            (!AnticheatConfig.DATA.allowedHashes.containsKey(modId) || !AnticheatConfig.DATA.allowedHashes.get(modId).equals(hash))) {
                        context.player().networkHandler.disconnect(Text.translatable("multiplayer.disconnect.forbidden_mod", modId));
                        return;
                    }
                } else {
                    modList.append(modId);
                    modList.append(',');
                }
            }

            if (FORCE_MODLIST)
                Informejtycy.LOGGER.info("Successfully verified {}", playerName);
            else
                Informejtycy.LOGGER.info("{} joined with mods: {}", playerName, modList);
        });
    }
}
