package simplexity.simpleback.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simplexity.simpleback.config.ConfigHandler;
import simplexity.simpleback.config.Message;

public class BackReload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        ConfigHandler.getInstance().loadConfigValues();
        sender.sendRichMessage(Message.PLUGIN_RELOADED.getMessage());
        return false;
    }
}