package com.tfw.main;

import com.tfw.bukkit.DamageHandler;
import com.tfw.bukkit.PlayerHandler;
import com.tfw.events.GameListener;
import com.tfw.events.PlayerListener;
import com.tfw.events.SettingsListener;
import com.tfw.events.TeamListener;
import com.tfw.events.worldsEvent.MultiverseEvents;
import com.tfw.events.worldsEvent.NormalWorldEvents;
import com.tfw.events.worldsEvent.SlimeWorldEvents;
import com.tfw.game.GameManager;
import com.tfw.game.arena.ArenaManager;
import com.tfw.manager.PlayerManager;
import com.tfw.manager.TeamManager;
import com.tfw.scoreboard.IScoreboardManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class TFWLoader {

    @Getter
    private static GameManager gameManager;
    @Getter
    private static ArenaManager arenaManager;
    @Getter
    private static TeamManager teamManager;
    @Getter
    private static PlayerManager playerManager;

    @Getter
    private static IScoreboardManager iScoreboardManager;

    public static void loadInstances(JavaPlugin javaPlugin){

        gameManager = new GameManager();
        arenaManager = new ArenaManager();
        teamManager = new TeamManager();
        playerManager = new PlayerManager();
        iScoreboardManager = new IScoreboardManager();

        registerEvents(javaPlugin);
    }

    /**
     *
     * - ALL LISTENERS EVENTS, LOAD!
     * - WORLD MANAGEMENT EVENTS!
     *
     * @param javaPlugin MAIN CLASS
     */
    private static void registerEvents(JavaPlugin javaPlugin){
        Arrays.asList(
                new PlayerHandler(),
                new DamageHandler(),
                new GameListener(),
                new PlayerListener(),
                new SettingsListener(),
                new TeamListener()).forEach(listener -> javaPlugin.getServer().getPluginManager().registerEvents(listener, javaPlugin));


        //  WORLD MANAGEMENT!
        new SlimeWorldEvents(javaPlugin);
        new MultiverseEvents(javaPlugin);
        new NormalWorldEvents(javaPlugin);
    }


}
