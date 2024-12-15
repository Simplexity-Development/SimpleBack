package simplexity.simpleback;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class ConfigHandler {
    private static ConfigHandler instance;
    public static ConfigHandler getInstance() {
        if (instance == null) instance = new ConfigHandler();
        return instance;
    }
    private ConfigHandler() {}
    private final Logger logger = SimpleBack.getInstance().getLogger();

    private final HashSet<UUID> blacklistedWorlds = new HashSet<>();
    private final HashSet<PlayerTeleportEvent.TeleportCause> trackedTeleportCauses = new HashSet<>();
    private boolean teleportDelay, cancelTeleportOnMove, teleportCooldown;
    private double movementBuffer;
    private int delayInSeconds, cooldownInSeconds;



    public void loadConfigValues(){
        SimpleBack.getInstance().reloadConfig();
        LocaleHandler.getInstance().reloadLocale();
        FileConfiguration config = SimpleBack.getInstance().getConfig();
        delayInSeconds = config.getInt("teleport-delay.delay-in-seconds", 5);
        if (delayInSeconds <= 0) teleportDelay = false;
        cancelTeleportOnMove = config.getBoolean("teleport-delay.cancel-tp-on-move", true);
        movementBuffer = config.getDouble("teleport-delay.buffer-distance-in-blocks", 0.5);
        cooldownInSeconds = config.getInt("teleport-cooldown.length-in-seconds", 10);
        if (cooldownInSeconds <= 0) teleportCooldown = false;
        populateTeleportCauses(config);
        populateBlacklistedWorlds(config);
    }

    private void populateBlacklistedWorlds(FileConfiguration config) {
        blacklistedWorlds.clear();
        List<String> worlds = config.getStringList("blacklisted-worlds");
        if (worlds.isEmpty()) return;
        for (String world : worlds) {
            World worldByName = Bukkit.getWorld(world);
            if (worldByName == null) {
                logger.warning("World " + world + " not found, please check your config");
                continue;
            }
            UUID worldUUID = worldByName.getUID();
            blacklistedWorlds.add(worldUUID);
        }
    }

    private void populateTeleportCauses(FileConfiguration config) {
        trackedTeleportCauses.clear();
        List<String> causes = config.getStringList("teleport-causes");
        if (causes.isEmpty()) {
            logger.warning("No teleport-causes found, please check your config, using the default values of 'PLUGIN' and 'COMMAND'");
            trackedTeleportCauses.add(PlayerTeleportEvent.TeleportCause.COMMAND);
            trackedTeleportCauses.add(PlayerTeleportEvent.TeleportCause.PLUGIN);
            return;
        }
        for (String cause : causes) {
            PlayerTeleportEvent.TeleportCause causeByName;
            try {
                causeByName = PlayerTeleportEvent.TeleportCause.valueOf(cause);
            } catch (IllegalArgumentException e) {
                logger.warning("Invalid cause " + cause + " found, please check your config");
                continue;
            }
            trackedTeleportCauses.add(causeByName);
        }
    }

    public boolean isTeleportDelay() {
        return teleportDelay;
    }

    public double getMovementBuffer() {
        return movementBuffer;
    }

    public boolean isCancelTeleportOnMove() {
        return cancelTeleportOnMove;
    }

    public HashSet<UUID> getBlacklistedWorlds() {
        return blacklistedWorlds;
    }

    public HashSet<PlayerTeleportEvent.TeleportCause> getTeleportCauses() {
        return trackedTeleportCauses;
    }

    public boolean isTeleportCooldown() {
        return teleportCooldown;
    }

    public int getCooldownInSeconds() {
        return cooldownInSeconds;
    }

    public int getDelayInSeconds() {
        return delayInSeconds;
    }
}
