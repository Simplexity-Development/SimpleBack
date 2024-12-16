package simplexity.simpleback.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import simplexity.simpleback.config.ConfigHandler;
import simplexity.simpleback.config.Message;
import simplexity.simpleback.SimpleBack;
import simplexity.simpleback.util.TeleportHandler;

public class Back implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ERROR_MUST_BE_PLAYER.getMessage());
            return true;
        }
        if (!SimpleBack.getInstance().getBackLocations().containsKey(player.getUniqueId())) {
            player.sendRichMessage(Message.ERROR_NO_BACK_LOCATIONS.getMessage());
            return true;
        }
        if (ConfigHandler.getInstance().isTeleportDelay()) {
            TeleportHandler.delayTeleport(player);
            return true;
        }
        TeleportHandler.teleport(player);
        System.out.println("TELEPORTED FROM COMMAND");
        return true;
    }
}
