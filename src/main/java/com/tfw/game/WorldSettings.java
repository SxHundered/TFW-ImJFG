package com.tfw.game;

import com.tfw.configuration.ConfigFile;
import com.tfw.configuration.Style;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldSettings implements Listener {

    String weather = "CLEAR"; //CLEAR,RAIN
    String time = "FIXED"; //FIXED,AUTO
    String time_type = "DAY"; //DAY,NIGHT
    boolean isWorldBorder = true; // TRUE,FALSE
    List<Double> center; //CORDINATES
    int size; //SIZE
    Set<Material> breakable; //ARRAY OF BLOCKS MATERIALS!
    Set<Material> placeable; //ARRAY OF BLOCKS MATERIALS!

    /**
     * @param javaPlugin MAIN CLASS
     * @param settings represents settings.yml!
     */
    public void setUpWorldSettings(JavaPlugin javaPlugin, ConfigFile settings) throws WorldExceptions {

        String pathConfig = "worldSettings";
        if (settings.getYaml().contains(pathConfig)) {
            //Here world settings
            weather = settings.getYaml().getString(pathConfig + ".Weather");
            time = settings.getYaml().getString(pathConfig + ".Time.type");
            time_type = settings.getYaml().getString(pathConfig + ".Time.fixed_to_be");
            isWorldBorder = settings.getYaml().getBoolean(pathConfig + ".WorldBorder.enable");
            center = settings.getYaml().getDoubleList(pathConfig + ".WorldBorder.center");
            size = settings.getYaml().getInt(pathConfig + ".WorldBorder.size");
            breakable = new HashSet<>();
            placeable = new HashSet<>();

            for (String block : settings.getYaml().getStringList(pathConfig + ".Breakable_blocks"))
                breakable.add(Material.getMaterial(block));
            for (String block : settings.getYaml().getStringList(pathConfig + ".Placeable_blocks"))
                placeable.add(Material.getMaterial(block));
        }else throw new WorldExceptions(Style.translate(
                "&e&lCOULD NOT LOAD WORLD SETTINGS\n   &cPLEASE REMOVE THE FILE SETTINGS.YML THEN RESTART!"));

        //Now checks if world border is enabled or not!
        if (isWorldBorder) {
            //Now setup!
            //Something..
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "");

            final World world = Bukkit.getWorld("world");

            if (world != null) {
                if (time.equalsIgnoreCase("FIXED")) {
                    if (time_type.equalsIgnoreCase("DAY"))
                        world.setTime(1200);
                    else if (time_type.equalsIgnoreCase("NIGHT"))
                        world.setTime(12000);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doDaylightCycle false");
                } else
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doDaylightCycle true");
            }
        }
    }

    //TODO: EVENTS OF WORLD SETTINGS! -> DONE

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event){
        event.setCancelled(!breakable.contains(event.getBlock().getType()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event){
        event.setCancelled(!placeable.contains(event.getBlock().getType()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onSpread(BlockSpreadEvent event){
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBurn(BlockBurnEvent event){
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onWeather(WeatherChangeEvent event){
        if(weather.equalsIgnoreCase("CLEAR"))
            event.getWorld().setStorm(false);
        else if(weather.equalsIgnoreCase("RAIN"))
            event.getWorld().setStorm(true);
    }

}
