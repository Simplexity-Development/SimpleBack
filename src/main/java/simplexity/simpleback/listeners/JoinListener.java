package simplexity.simpleback.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import simplexity.simpleback.util.TeleportHandler;

import java.util.UUID;

public class JoinListener implements Listener {
    @EventHandler(priority= EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (TeleportHandler.cacheClearTasks.containsKey(uuid)) {
            TeleportHandler.cancelCacheClear(uuid);
        }
    }
}
