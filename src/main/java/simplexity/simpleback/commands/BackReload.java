package simplexity.simpleback.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import simplexity.simpleback.config.ConfigHandler;
import simplexity.simpleback.config.LocaleMessage;
import simplexity.simpleback.handlers.BackPermission;

@SuppressWarnings("UnstableApiUsage")
public class BackReload {

    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands.literal("backreload")
                .requires(css -> css.getSender().hasPermission(BackPermission.BACK_RELOAD.getPermission()))
                .executes(css -> {
                    ConfigHandler.getInstance().loadConfigValues();
                    css.getSource().getSender().sendRichMessage(LocaleMessage.PLUGIN_RELOADED.getMessage());
                    return Command.SINGLE_SUCCESS;
                });
    }

}
