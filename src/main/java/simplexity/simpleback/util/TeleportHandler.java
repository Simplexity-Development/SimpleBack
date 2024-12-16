package simplexity.simpleback.util;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import simplexity.simpleback.SimpleBack;
import simplexity.simpleback.config.ConfigHandler;
import simplexity.simpleback.config.Message;

import java.util.HashMap;
import java.util.UUID;

public class TeleportHandler {
    private static final HashMap<UUID, BukkitTask> currentTasks = new HashMap<>();
    public static final HashMap<UUID, Location> startingLocations = new HashMap<>();
    public static final HashMap<UUID, BukkitTask> cacheClearTasks = new HashMap<>();

    public static void delayTeleport(@NotNull Player player) {
        if (player.hasPermission(Permissions.DELAY_BYPASS)) {
            teleport(player);
            return;
        }
        if (!player.hasPermission(Permissions.MOVEMENT_BYPASS)) {
            startingLocations.put(player.getUniqueId(), player.getLocation());
        }
        int delay = ConfigHandler.getInstance().getDelayInSeconds();
        player.sendRichMessage(Message.TELEPORT_PLEASE_WAIT.getMessage(),
                Placeholder.unparsed("value", String.valueOf(delay)));
        BukkitTask task = Bukkit.getScheduler().runTaskLater(SimpleBack.getInstance(), () -> {
            teleport(player);
        },  delay * 20L);
        currentTasks.put(player.getUniqueId(), task);
    }

    public static void teleport(@NotNull Player player) {
        Location location = SimpleBack.getInstance().getBackLocations().get(player.getUniqueId());
        player.sendMessage(MessageHandler.getParsedLocationMessage(Message.TELEPORT_SUCCESSFUL, location));
        player.teleportAsync(location);
        UUID uuid = player.getUniqueId();
        startingLocations.remove(uuid);
        currentTasks.remove(uuid);
    }

    public static void cancelTeleport(@NotNull Player player) {
        UUID uuid = player.getUniqueId();
        currentTasks.get(uuid).cancel();
        startingLocations.remove(uuid);
        player.sendRichMessage(Message.TELEPORT_CANCELLED.getMessage());
    }

    public static void removeCache(@NotNull Player player) {
        UUID uuid = player.getUniqueId();
        BukkitTask task = Bukkit.getScheduler().runTaskLater(SimpleBack.getInstance(), () -> {
            currentTasks.get(uuid).cancel();
            currentTasks.remove(uuid);
            startingLocations.remove(uuid);
            SimpleBack.getInstance().getBackLocations().remove(uuid);
        },  ConfigHandler.getInstance().getCacheClearTimeInSeconds() * 20L);
        cacheClearTasks.put(uuid, task);
    }

    public static void cancelCacheClear(@NotNull UUID uuid) {
        BukkitTask task = cacheClearTasks.get(uuid);
        if (task != null) {
            task.cancel();
        }
    }


}
