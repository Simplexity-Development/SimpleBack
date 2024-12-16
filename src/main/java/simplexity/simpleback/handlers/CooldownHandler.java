package simplexity.simpleback.handlers;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import simplexity.simpleback.config.ConfigHandler;

import java.util.HashMap;
import java.util.UUID;

public class CooldownHandler {
    public static final HashMap<UUID, Long> cooldownTime = new HashMap<>();

    static void addToCooldown(@NotNull Player player) {
        if (!ConfigHandler.getInstance().isTeleportCooldown()) return;
        if (player.hasPermission(Permissions.COOLDOWN_BYPASS)) return;
        UUID uuid = player.getUniqueId();
        Long cooldownTimeMilli = System.currentTimeMillis() + (ConfigHandler.getInstance().getCooldownInSeconds() * 1000L);
        cooldownTime.put(uuid, cooldownTimeMilli);
    }

    public static boolean cooldownExpired(@NotNull Player player) {
        UUID uuid = player.getUniqueId();
        if (!cooldownTime.containsKey(uuid)) return true;
        if (player.hasPermission(Permissions.COOLDOWN_BYPASS)) return true;
        long leftoverTime = getLeftoverCooldownTime(uuid);
        return leftoverTime <= 0;
    }

    public static Long getLeftoverCooldownTime(@NotNull UUID uuid) {
        Long currentTime = System.currentTimeMillis();
        Long coolDownTime = cooldownTime.get(uuid);
        return coolDownTime - currentTime;
    }
}
