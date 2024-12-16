package simplexity.simpleback.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import simplexity.simpleback.SimpleBack;
import simplexity.simpleback.handlers.CacheHandler;

import java.util.UUID;

public class LeaveListener implements Listener {

    @EventHandler(priority= EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (!SimpleBack.getInstance().getBackLocations().containsKey(uuid)) return;
        CacheHandler.removeCache(event.getPlayer().getUniqueId());
    }


}
