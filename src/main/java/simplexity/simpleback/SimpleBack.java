package simplexity.simpleback;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import simplexity.simpleback.commands.Back;
import simplexity.simpleback.commands.BackReload;
import simplexity.simpleback.config.ConfigHandler;
import simplexity.simpleback.listeners.DeathListener;
import simplexity.simpleback.listeners.JoinListener;
import simplexity.simpleback.listeners.LeaveListener;
import simplexity.simpleback.listeners.MovementListener;
import simplexity.simpleback.listeners.TeleportListener;

import java.util.HashMap;
import java.util.UUID;

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
        this.getServer().getPluginManager().registerEvents(new TeleportListener(), this);
        this.getServer().getPluginManager().registerEvents(new MovementListener(), this);
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new LeaveListener(), this);
        this.getServer().getPluginManager().registerEvents(new DeathListener(), this);
        //noinspection DataFlowIssue
        this.getCommand("back").setExecutor(new Back());
        //noinspection DataFlowIssue
        this.getCommand("backreload").setExecutor(new BackReload());
        // Plugin startup logic

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
