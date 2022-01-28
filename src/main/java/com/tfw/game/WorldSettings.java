package com.tfw.game;

import com.tfw.configuration.ConfigFile;
import com.tfw.configuration.Style;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class WorldSettings implements Listener {

    private ConfigFile configFile;

    public boolean isWorldBorder = true; // TRUE,FALSE
    public List<Double> center; //CORDINATES
    public int size; //SIZE
    public Set<Material> breakable; //ARRAY OF BLOCKS MATERIALS!
    public Set<Material> placeable; //ARRAY OF BLOCKS MATERIALS!

    /**
     * @param javaPlugin MAIN CLASS
     * @param settings   represents settings.yml!
     */
    public void setUpWorldSettings(JavaPlugin javaPlugin, ConfigFile settings) throws WorldExceptions {
        configFile = settings;

        String pathConfig = "worldSettings";
        if (settings.getYaml().contains(pathConfig)) {
            //Here world settings
            isWorldBorder = settings.getYaml().getBoolean(pathConfig + ".WorldBorder.enable");
            center = settings.getYaml().getDoubleList(pathConfig + ".WorldBorder.center");
            size = settings.getYaml().getInt(pathConfig + ".WorldBorder.size");
            breakable = new HashSet<>();
            placeable = new HashSet<>();

            for (String block : settings.getYaml().getStringList(pathConfig + ".Breakable_blocks"))
                breakable.add(Material.getMaterial(block));
            for (String block : settings.getYaml().getStringList(pathConfig + ".Placeable_blocks"))
                placeable.add(Material.getMaterial(block));
        } else throw new WorldExceptions(Style.translate(
                "&e&lCOULD NOT LOAD WORLD SETTINGS\n   &cPLEASE REMOVE THE FILE SETTINGS.YML THEN RESTART!"));

        //Now checks if world border is enabled or not!
        if (isWorldBorder) {
            //Now setup!
            //Something..
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "");
        }
    }

    //TODO: EVENTS OF WORLD SETTINGS! -> DONE

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(!breakable.contains(event.getBlock().getType()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(!placeable.contains(event.getBlock().getType()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onSpread(BlockSpreadEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBurn(BlockBurnEvent event) {
        event.setCancelled(true);
    }


    public void updateSettings() {
        assert configFile != null;
        String pathConfig = "worldSettings";
        configFile.getYaml().set(pathConfig + ".WorldBorder.enable", isWorldBorder);
        for (Double aDouble : center)
            configFile.getYaml().set(pathConfig + ".WorldBorder.center", aDouble);

        configFile.getYaml().set(pathConfig + ".WorldBorder.size", size);

        List<String> listOfStrings = new ArrayList<>();
        for (Material material : breakable)
            listOfStrings.add(material.name().toUpperCase(Locale.ROOT));

        configFile.getYaml().set(pathConfig + ".Breakable_blocks", listOfStrings);

        listOfStrings = new ArrayList<>();
        for (Material material : placeable)
            listOfStrings.add(material.name().toUpperCase(Locale.ROOT));
        configFile.getYaml().set(pathConfig + ".Placeable_blocks", listOfStrings);

        configFile.save();
    }
}