package simplexity.simpleback.handlers;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public enum BackPermission {
    // Declaration order so I don't keep having to look and forget
    // Permission(@NotNull String name,
    // @Nullable String description,
    // @Nullable PermissionDefault defaultValue,
    // @Nullable Map<String, Boolean> children)

    BACK_USE(new Permission("back.use", "Allows the player to use the back command", PermissionDefault.TRUE)),
    FORCE_BACK(new Permission("back.force", "Runs /back for another player", PermissionDefault.OP)),
    BACK_RELOAD(new Permission("back.reload", "Allows the player to reload the SimpleBack config", PermissionDefault.OP)),
    DELAY_BYPASS(new Permission("back.bypass.delay", "Bypass the teleport delay completely", PermissionDefault.OP)),
    MOVEMENT_BYPASS(new Permission("back.bypass.movement", "Bypass the 'no movement' requirement of the teleport delay", PermissionDefault.OP)),
    WORLD_BYPASS(new Permission("back.bypass.worlds", "Bypass the worlds blacklist for /back", PermissionDefault.OP)),
    COOLDOWN_BYPASS(new Permission("back.bypass.cooldown", "Bypass the cooldown on /back", PermissionDefault.OP));

    private final Permission permission;

    BackPermission(Permission permission) {
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }
}
