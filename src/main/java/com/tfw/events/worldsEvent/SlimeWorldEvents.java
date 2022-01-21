package com.tfw.events.worldsEvent;

import com.tfw.configuration.Style;
import com.tfw.events.custom.WorldLoadEvent;
import com.tfw.events.custom.WorldUnLoadEvent;
import com.tfw.main.DependencyLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *     Only handle Game modifications
 *
 *     | Soft Dependency for SlimeWorldManager usage ONLY WORKS WITH 2.2.1 or HIGHER!, IF EXISTED!
 *     | (ONLY USE COMMANDS TO LOAD AND UNLOAD)
 */
public class SlimeWorldEvents implements Listener {


    private final JavaPlugin plugin;

    /**
     * Load (WorldLoad & WorldUnload)
     * <p>
     * If Slime World is detected active this events!
     *
     * @param javaPlugin Main Class
     */
    public SlimeWorldEvents(JavaPlugin javaPlugin) {
        this.plugin = javaPlugin;

        final DependencyLoader.PLUGINS plugin = DependencyLoader.depencendies.stream().filter(plugins -> plugins.getPluginName().equalsIgnoreCase("SlimeWorldManager")).findFirst().orElse(null);
        if (plugin != null)
            if (plugin.isLoaded()) {
                javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);
                Bukkit.getServer().getConsoleSender().sendMessage(Style.translate("&aSlimeWorld events have been triggered!"));
            }
    }

    /**
     * @param worldLoadEvent WORLD LOAD EVENT
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSlimeWorldLoad(WorldLoadEvent worldLoadEvent) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                            Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
                                    "swm load " + worldLoadEvent.getWorldName())
                    , 1L);
        });
    }

    /**
     * @param worldUnLoadEvent WORLD UNLOAD EVENT!
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSlimeWorldUnLoad(WorldUnLoadEvent worldUnLoadEvent) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                            Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
                                    "swm unload " + worldUnLoadEvent.getWorldName())
                    , 1L);
        });
    }
}
