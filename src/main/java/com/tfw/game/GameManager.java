package com.tfw.game;


import com.tfw.configuration.ConfigFile;
import com.tfw.game.arena.ArenaManager;
import com.tfw.game.arena.iarena.Arena;
import com.tfw.game.arena.iarena.IArena;
import com.tfw.manager.TeamManager;
import com.tfw.manager.data.PlayerData;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

@Getter
public class GameManager implements IGame,ISettings{

    @Getter(AccessLevel.PRIVATE)
    private static ConfigFile settings;
    private int timer;
    private String currentTime;

    @Override
    public void gameSetup(JavaPlugin javaPlugin) throws WorldExceptions {
        initializeSettings(javaPlugin);

        timer = 0;
        currentTime = "00:00";
        IArena.ARENA_STATUS.setArena_status(IArena.ARENA_STATUS.LOBBY);

        //Now Checks if Arenas loaded successfully or not!
        WorldSettings worldSettings = new WorldSettings();
        javaPlugin.getServer().getPluginManager().registerEvents(worldSettings, javaPlugin);
        worldSettings.setUpWorldSettings(javaPlugin, settings);
    }

    /**
     * Setting up configurations
     */
    @Override
    public void initializeSettings(JavaPlugin javaPlugin) {
        settings = new ConfigFile(javaPlugin, "settings.yml");
    }

    /**
     * Starts the game
     */

    @Override
    public int gameTime() {
        return timer;
    }

    @Override
    public String currentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.SECOND, timer);
        return new SimpleDateFormat("mm:ss").format(calendar.getTime());
    }

    @Override
    public void startGame() {

    }

    /**
     * Teleport Teams to their destination!
     */
    @Override
    public void teleportPlayers() {

    }

    /**
     * Sound Effect for all players in the game!
     */
    @Override
    public void playSound() {
        for (PlayerData playerData : TeamManager.getA().alive_members())
            playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
        for (PlayerData playerData : TeamManager.getB().alive_members())
            playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
    }

    /**
     * @param message Message is being sent to all teams!
     */
    @Override
    public void notification(String message) {

    }

    /**
     * Celebrating winners, cool effects and stuff
     */
    @Override
    public void celebrate() {

    }

    /**
     * Disabling all things and stop the server!
     */
    @Override
    public void restartTheGame() {

    }

    @Override
    public Arena getArena() {
        return ArenaManager.getArena();
    }


    public enum GameStates{
        LOBBY,INGAME,RESTART;

        private static GameStates gameStates;

    }
}
