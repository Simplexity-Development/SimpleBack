package simplexity.simpleback.handlers;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import simplexity.simpleback.SimpleBack;
import simplexity.simpleback.config.ConfigHandler;

import java.util.HashMap;
import java.util.UUID;

public class CacheHandler {
    public static final HashMap<UUID, BukkitTask> cacheClearTasks = new HashMap<>();

    public static void cancelCacheClear(@NotNull UUID uuid) {
        BukkitTask task = cacheClearTasks.get(uuid);
        if (task != null) {
            task.cancel();
        }
    }

    public static void removeCache(@NotNull UUID uuid) {
        BukkitTask task = Bukkit.getScheduler().runTaskLater(SimpleBack.getInstance(), () -> {
            BukkitTask teleportTask = TeleportHandler.currentTasks.get(uuid);
            if (teleportTask != null) {
                teleportTask.cancel();
            }
            TeleportHandler.currentTasks.remove(uuid);
            TeleportHandler.startingLocations.remove(uuid);
            CooldownHandler.cooldownTime.remove(uuid);
            SimpleBack.getInstance().getBackLocations().remove(uuid);
        }, ConfigHandler.getInstance().getCacheClearTimeInSeconds() * 20L);
        cacheClearTasks.put(uuid, task);
    }

    public static void clearCache() {
        TeleportHandler.currentTasks.forEach((k, v) -> v.cancel());
        TeleportHandler.currentTasks.clear();
        TeleportHandler.startingLocations.clear();
        CooldownHandler.cooldownTime.clear();
        CacheHandler.cacheClearTasks.forEach((k, v) -> v.cancel());
        CacheHandler.cacheClearTasks.clear();
    }
}
