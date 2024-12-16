package simplexity.simpleback.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import simplexity.simpleback.SimpleBack;

public class TeleportListener implements Listener {
    @EventHandler(priority= EventPriority.MONITOR)
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location previousLocation = event.getFrom();
        SimpleBack.getInstance().getBackLocations().put(player.getUniqueId(), previousLocation);
    }
}
