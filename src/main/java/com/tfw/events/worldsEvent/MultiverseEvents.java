package com.tfw.events.worldsEvent;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiversePlugin;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.tfw.configuration.Style;
import com.tfw.events.custom.WorldLoadEvent;
import com.tfw.events.custom.WorldUnLoadEvent;
import com.tfw.main.DependencyLoader;
import lombok.AccessLevel;
import lombok.Getter;
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
 *
 *     Only handle Game modifications
 *     | Soft Dependency for Multiverse-Core usage ONLY WORKS WITH 2.5-b717 IF EXISTED!
 */
public class MultiverseEvents implements Listener {

    @Getter(AccessLevel.PRIVATE)
    private MultiverseCore multiverseCore;

    /**
     *
     * Load (WorldLoad & WorldUnload)
     *
     * If Slime World is detected active this events!
     *
     * @param javaPlugin Main Class
     */
    public MultiverseEvents(JavaPlugin javaPlugin){
        final DependencyLoader.PLUGINS plugin = DependencyLoader.depencendies.stream().filter(plugins -> plugins.getPluginName().equalsIgnoreCase("Multiverse-Core")).findFirst().orElse(null);
        if (plugin != null)
            if (plugin.isLoaded()) {
                javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);
                Bukkit.getServer().getConsoleSender().sendMessage(Style.translate("&aMultiverse-Core events have been triggered!"));
                multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            }
    }


    /**
     *
     * If world is already loaded skip!
     *
     * @param worldLoadEvent WORLD LOAD EVENT
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSlimeWorldLoad(WorldLoadEvent worldLoadEvent){
        String world = worldLoadEvent.getWorldName();

        Bukkit.getServer().getConsoleSender().sendMessage(Style.RED + "Loading " + Style.YELLOW + world);

        boolean loadWorld = multiverseCore.getMVWorldManager().loadWorld(world);

        if (loadWorld)
            Bukkit.getServer().getConsoleSender().sendMessage(Style.AQUA + world + " has been loaded successfully!");
        else
            Bukkit.getServer().getConsoleSender().sendMessage(Style.translate(
                    Style.YELLOW + "Failed to load " + Style.RED + world + Style.YELLOW + ", the world might now be &7(&cNOT_EXISTED&7/&cALREADY_LOADED&7)"));
    }

    /**
     * @param worldUnLoadEvent WORLD UNLOAD EVENT!
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSlimeWorldUnLoad(WorldUnLoadEvent worldUnLoadEvent){
        String world = worldUnLoadEvent.getWorldName();

        Bukkit.getServer().getConsoleSender().sendMessage(Style.RED + "UnLoading " + Style.YELLOW + world);

        boolean unloadWorld = multiverseCore.getMVWorldManager().unloadWorld(world);

        if (unloadWorld)
            Bukkit.getServer().getConsoleSender().sendMessage(Style.BLUE + world + " has been unloaded successfully!");
        else
            Bukkit.getServer().getConsoleSender().sendMessage(Style.translate(
                    Style.YELLOW + "Failed to unload " + Style.RED + world + Style.YELLOW + ", the world might now be &7(&cNOT_EXISTED&7/&cALREADY_UNLOADED&7)"));
    }

}
