package simplexity.simpleback;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TeleportHandler {
    private static final HashMap<Player, BukkitTask> currentTasks = new HashMap<>();
    public static final HashMap<Player, Location> startingLocations = new HashMap<>();

    public static void delayTeleport(@NotNull Player player) {
        if (player.hasPermission(Permissions.DELAY_BYPASS)) {
            teleport(player);
            return;
        }
        if (!player.hasPermission(Permissions.MOVEMENT_BYPASS)) {
            startingLocations.put(player, player.getLocation());
        }
        BukkitTask task = Bukkit.getScheduler().runTaskLater(SimpleBack.getInstance(), () -> {
            teleport(player);
        }, ConfigHandler.getInstance().getDelayInSeconds() * 20L);
        currentTasks.put(player, task);
    }

    public static void teleport(@NotNull Player player) {
        Location location = SimpleBack.getInstance().getBackLocations().get(player.getUniqueId());
        player.sendMessage(MessageHandler.getParsedLocationMessage(Message.TELEPORT_SUCCESSFUL, location));
        player.teleportAsync(location);
        startingLocations.remove(player);
        currentTasks.remove(player);
    }

    public static void cancelTeleport(@NotNull Player player) {
        currentTasks.remove(player);
        startingLocations.remove(player);
        player.sendRichMessage(Message.TELEPORT_CANCELLED.getMessage());
    }


}
