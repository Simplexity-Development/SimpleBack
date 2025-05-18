package simplexity.simpleback.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import simplexity.simpleback.SimpleBack;
import simplexity.simpleback.config.ConfigHandler;

public class DeathListener implements Listener {

    @EventHandler(ignoreCancelled = true,priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        if (!ConfigHandler.getInstance().isAllowBackOnDeath()) return;
        Player player = event.getEntity();
        Location deathLocation = event.getEntity().getLocation();
        SimpleBack.getInstance().getBackLocations().put(player.getUniqueId(), deathLocation);
    }

}
