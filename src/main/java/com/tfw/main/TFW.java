package com.tfw.main;

import com.tfw.bukkit.commands.TFWCommand;
import com.tfw.configuration.ConfigFile;
import com.tfw.configuration.Style;
import com.tfw.events.worldsEvent.MultiverseEvents;
import com.tfw.events.worldsEvent.NormalWorldEvents;
import com.tfw.events.worldsEvent.SlimeWorldEvents;
import com.tfw.game.GameManager;
import com.tfw.game.WorldExceptions;
import com.tfw.game.arena.ArenaExceptions;
import com.tfw.manager.TeamExceptions;
import com.tfw.placeholders.TFWPlaceHolder;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *
 *     Start point! - here is the initialization class!
 */
public final class TFW extends JavaPlugin {

    @Getter
    private static TFW instance;

    private static String prefix;

    @Getter
    private static boolean isDebug;

    @Getter(AccessLevel.PRIVATE)
    private TFWPlaceHolder tfwPlaceHolder;

    @Getter(AccessLevel.PRIVATE)
    ConfigFile configFile;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        configFile = new ConfigFile(this, "config.yml");
        prefix = configFile.getYaml().contains("Prefix") ? configFile.getYaml().getString("Prefix") : "NO PREFIX!";
        isDebug = configFile.getYaml().contains("Debug") && configFile.getYaml().getBoolean("Debug");

        try {

            if (DependencyLoader.load(this)) {
                //All instances for dependencies
                tfwPlaceHolder = new TFWPlaceHolder();
                tfwPlaceHolder.register();

            }

            TFWLoader.loadInstances(this);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(Style.DEPENDENCIES_FAILED);
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Generate all stuff needed!
        try {
            TFWCommand.init(this);

            TFWLoader.getTeamManager().teamSetup(this);
            TFWLoader.getArenaManager().arenaSetup(this);
            TFWLoader.getGameManager().gameSetup(this);
            TFWLoader.getIScoreboardManager().loadScoreboards(this);
            
        } catch (ArenaExceptions | TeamExceptions | WorldExceptions e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        GameManager.GameStates.setGameStates(GameManager.GameStates.LOBBY);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', prefix);
    }
}
