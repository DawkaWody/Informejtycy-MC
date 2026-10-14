package daw.ka.informejtycy.command.custom;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RecycleScoreCommand {
    public static HashMap<UUID, Long> playerScores = new HashMap<>();

    public static int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        return execute(context, player);
    }

    public static int execute(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        ServerPlayer sender = context.getSource().getPlayer();
        if (player == null || sender == null) return 0;

        long score = playerScores.getOrDefault(player.getUUID(), 0L);
        sender.sendSystemMessage(Component.translatable("command.informejtycy.recycle_score.player", player.getName().getString(), score));
        return 1;
    }

    public static int top(CommandContext<CommandSourceStack> context) {
        ServerPlayer sender = context.getSource().getPlayer();
        if (sender == null) return 0;

        MutableComponent topScores = Component.empty();
        topScores.append(Component.translatable("command.informejtycy.recycle_score.header"));
        topScores.append(Component.literal("\n"));
        AtomicInteger a = new AtomicInteger(1);
        playerScores.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(5)
                .forEach(entry -> {
                    ServerPlayer player = context.getSource().getServer().getPlayerList().getPlayer(entry.getKey());
                    if (player != null) {
                        int place = a.getAndIncrement();
                        MutableComponent line = Component.literal(place + ". " + player.getName().getString() + " -> [" + entry.getValue() + "]\n");

                        if (place == 1) {
                            line.withStyle(style -> style.withColor(TextColor.GOLD));
                        } else if (place == 2) {
                            line.withStyle(style -> style.withColor(TextColor.fromRgb(0xC0C0C0)));
                        } else if (place == 3) {
                            line.withStyle(style -> style.withColor(TextColor.fromRgb(0xCD7F32)));
                        }

                        topScores.append(line);
                    }
                });

        sender.sendSystemMessage(topScores);
        return 1;
    }

    public static void incrementScore(ServerPlayer player) {
        playerScores.merge(player.getUUID(), 1L, Long::sum);
    }
}
