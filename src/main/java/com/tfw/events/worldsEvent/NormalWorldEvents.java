package com.tfw.events.worldsEvent;

import com.tfw.events.custom.WorldLoadEvent;
import com.tfw.events.custom.WorldUnLoadEvent;
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
 */

public class NormalWorldEvents implements Listener {


    private final JavaPlugin plugin;

    /**
     * Load (WorldLoad & WorldUnload)
     * <p>
     * If no world-management dependencies were detected use the normal procedure!
     *
     * @param javaPlugin Main Class
     */
    public NormalWorldEvents(JavaPlugin javaPlugin) {
        this.plugin = javaPlugin;
        javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);
    }

    /**
     * @param worldLoadEvent WORLD LOAD EVENT
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSlimeWorldLoad(WorldLoadEvent worldLoadEvent) {
        if (worldLoadEvent.isCanceled())
            return;

        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                       Bukkit.unloadWorld(worldLoadEvent.getWorldName(), true), 1L);
        });
    }

    /**
     * @param worldUnLoadEvent WORLD UNLOAD EVENT!
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSlimeWorldUnLoad(WorldUnLoadEvent worldUnLoadEvent) {
        if (worldUnLoadEvent.isCanceled())
            return;

        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                            Bukkit.unloadWorld(worldUnLoadEvent.getWorldName(), false)
                    , 1L);
        });
    }
}
