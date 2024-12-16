package simplexity.simpleback.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import simplexity.simpleback.config.ConfigHandler;
import simplexity.simpleback.handlers.TeleportHandler;

import java.util.UUID;

public class MovementListener implements Listener {
    @EventHandler(priority= EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        if (!ConfigHandler.getInstance().isTeleportDelay() || !ConfigHandler.getInstance().isCancelTeleportOnMove()) return;
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Location originalLocation = TeleportHandler.startingLocations.get(uuid);
        if (originalLocation == null) return;
        if (originalLocation.distance(player.getLocation()) < ConfigHandler.getInstance().getMovementBuffer()) return;
        TeleportHandler.cancelTeleport(player);
    }
}
