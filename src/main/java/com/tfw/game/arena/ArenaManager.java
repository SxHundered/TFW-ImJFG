package com.tfw.game.arena;

import com.tfw.configuration.ConfigFile;
import com.tfw.configuration.Style;
import com.tfw.game.arena.iarena.Arena;
import com.tfw.main.TFW;
import com.tfw.utils.CustomLocation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
 *     Only handle arenas modifications!
 */

public class ArenaManager implements IArenas {

    @Setter@Getter
    private static Arena arena;

    @Getter@Setter
    private static String worldName;

    @Getter(AccessLevel.PRIVATE)
    String pathArena = "main_Arena";
    @Getter(AccessLevel.PRIVATE)
    ConfigFile arenasConfig;

    String[] chars = {"a", "a_heart", "b", "b_heart"};

    /**
     * @param javaPlugin MAIN CLASS
     * @throws ArenaExceptions WHEN ARENA FAILS TO BE GENERATED, IT THROWS THIS EXCEPTION!
     */
    @Override
    public void arenaSetup(JavaPlugin javaPlugin) throws ArenaExceptions {
        arenasConfig = new ConfigFile(javaPlugin, "arenas.yml");

        if (!arenasConfig.getYaml().contains(pathArena))
            throw new ArenaExceptions(Style.RED + Style.translate("&cCOULD NOT FIND ANY ARENA!, &aPLEASE SETUP USING &7/arena create &c&lmain"));

        pathArena = pathArena + ".displayName";

        if (arenasConfig.getYaml().contains(pathArena)) {
            Arena arenaTemp = new Arena(arenasConfig.getString(pathArena));

            CustomLocation a = null, b = null, a_heart = null, b_heart = null;

            pathArena = "main_Arena.locations";
            for (String loc : chars)
                if (arenasConfig.getYaml().contains(pathArena + "." + loc)) {
                    if (loc.equalsIgnoreCase("a"))
                        a = CustomLocation.stringToLocation(arenasConfig.getString(pathArena + "." + loc));
                    else if (loc.equalsIgnoreCase("b"))
                        b = CustomLocation.stringToLocation(arenasConfig.getString(pathArena + "." + loc));
                    else if (loc.equalsIgnoreCase("a_heart"))
                        a_heart = CustomLocation.stringToLocation(arenasConfig.getString(pathArena + "." + loc));
                    else if (loc.equalsIgnoreCase("b_heart"))
                        b_heart = CustomLocation.stringToLocation(arenasConfig.getString(pathArena + "." + loc));
                } else throw new ArenaExceptions(Style.translate("&6COULD NOT FIND &a&l" + loc + "&6 LOCATION&7,&c PLEASE SETUP THE ARENA CORRECTLY!"));

            assert a != null && a_heart != null && b != null && b_heart != null;

            worldName = a.getWorld();

            if (arenaTemp.prepareLocations(a, b, a_heart, b_heart))
                setArena(arenaTemp);
            else
                throw new ArenaExceptions(Style.translate(ChatColor.RED + "COULD NOT LOAD LOCATIONS CORRECTLY!, PLEASE DOUBLE CHECK FOR THE ENTRIES!"));

            loadArena();
        } else
            throw new ArenaExceptions(Style.translate(Style.RED + "COULD NOT FIND ARENA NAME IN THE arenas.yml," +
                    " set in arenas.yml (main_Arena.displayName) ,\n THEN TRY AGAIN!"));
    }

    private void loadArena(){
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TFW.getInstance(), ()-> {
            Bukkit.unloadWorld(worldName, false);
        });
    }
}