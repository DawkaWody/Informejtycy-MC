package daw.ka.informejtycy.command;

import daw.ka.informejtycy.command.custom.RecycleScoreCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;

public class InformejtycyCommands {
    public static void registerAll() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, buildContext, selection) -> {
            dispatcher.register(Commands.literal("kaucja")
                    .executes(RecycleScoreCommand::execute)
                    .then(Commands.literal("top")
                            .executes(RecycleScoreCommand::top))
                    .then(Commands.argument("player", EntityArgument.player())
                            .executes(ctx -> RecycleScoreCommand.execute(ctx, EntityArgument.getPlayer(ctx, "player")))));
        }));
    }
}
