package simplexity.simpleback;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BackReload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        ConfigHandler.getInstance().loadConfigValues();
        sender.sendRichMessage(Message.PLUGIN_RELOADED.getMessage());
        return false;
    }
}
