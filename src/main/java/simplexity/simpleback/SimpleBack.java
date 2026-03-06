package simplexity.simpleback;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.simpleback.commands.Back;
import simplexity.simpleback.commands.BackReload;
import simplexity.simpleback.config.ConfigHandler;
import simplexity.simpleback.handlers.BackPermission;
import simplexity.simpleback.listeners.*;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public final class SimpleBack extends JavaPlugin {
    private final HashMap<UUID, Location> backLocations = new HashMap<>();
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static SimpleBack instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        ConfigHandler.getInstance().loadConfigValues();
        registerPermissions();
        this.getServer().getPluginManager().registerEvents(new TeleportListener(), this);
        this.getServer().getPluginManager().registerEvents(new MovementListener(), this);
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        this.getServer().getPluginManager().registerEvents(new DeathListener(), this);
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(Back.createCommand().build());
            commands.registrar().register(BackReload.createCommand().build());
        });
    }

    private void registerPermissions(){
        for (BackPermission permission : BackPermission.values()) {
            this.getServer().getPluginManager().addPermission(permission.getPermission());
        }
    }

    public HashMap<UUID, Location> getBackLocations() {
        return backLocations;
    }
    public static SimpleBack getInstance() {
        return instance;
    }

    public static MiniMessage getMiniMessage() {
        return miniMessage;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
