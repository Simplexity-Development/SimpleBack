package simplexity.simpleback.handlers;

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
    public static final HashMap<UUID, BukkitTask> currentTasks = new HashMap<>();
    public static final HashMap<UUID, Location> startingLocations = new HashMap<>();

    public static void delayTeleport(@NotNull Player player) {
        if (player.hasPermission(Permissions.DELAY_BYPASS)) {
            teleport(player);
            return;
        }
        if (!player.hasPermission(Permissions.MOVEMENT_BYPASS)) {
            startingLocations.put(player.getUniqueId(), player.getLocation());
        }
        UUID uuid = player.getUniqueId();
        int delay = ConfigHandler.getInstance().getDelayInSeconds();
        player.sendRichMessage(Message.TELEPORT_PLEASE_WAIT.getMessage(),
                Placeholder.unparsed("value", String.valueOf(delay)));
        BukkitTask task = Bukkit.getScheduler().runTaskLater(SimpleBack.getInstance(), () -> {
            teleport(player);
        }, delay * 20L);
        currentTasks.put(uuid, task);
    }

    public static void teleport(@NotNull Player player) {
        Location location = SimpleBack.getInstance().getBackLocations().get(player.getUniqueId());
        player.sendMessage(MessageHandler.getParsedLocationMessage(Message.TELEPORT_SUCCESSFUL, location));
        player.teleportAsync(location);
        CooldownHandler.addToCooldown(player);
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

    public static boolean blacklistedWorld(@NotNull Player player) {
        UUID uuid = player.getUniqueId();
        if (player.hasPermission(Permissions.WORLD_BYPASS)) return false;
        Location location = SimpleBack.getInstance().getBackLocations().get(uuid);
        if (location == null) return false;
        UUID worldUUID = location.getWorld().getUID();
        return ConfigHandler.getInstance().getBlacklistedWorlds().contains(worldUUID);
    }
}
