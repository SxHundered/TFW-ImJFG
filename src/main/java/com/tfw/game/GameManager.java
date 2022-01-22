package com.tfw.game;


import com.tfw.configuration.ConfigFile;
import com.tfw.configuration.Style;
import com.tfw.events.custom.CelebrationEvent;
import com.tfw.events.custom.GameStartEvent;
import com.tfw.events.custom.KitHandleEvent;
import com.tfw.events.custom.PreparePlayersEvent;
import com.tfw.game.arena.ArenaManager;
import com.tfw.game.arena.iarena.Arena;
import com.tfw.game.arena.iarena.IArena;
import com.tfw.game.task.GameTask;
import com.tfw.game.task.IScoreBoardTask;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamManager;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.Team;
import com.tfw.utils.ReflectionUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.libs.kyori.adventure.text.Component;
import me.clip.placeholderapi.libs.kyori.adventure.title.Title;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

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

    @Getter
    private static ConfigFile settings;
    private GameTask gameTask;
    private WorldSettings worldSettings;

    @Override
    public void gameSetup(JavaPlugin javaPlugin) throws WorldExceptions {
        initializeSettings(javaPlugin);

        IArena.ARENA_STATUS.setArena_status(IArena.ARENA_STATUS.LOBBY);

        //Now Checks if Arenas loaded successfully or not!
        worldSettings = new WorldSettings();
        javaPlugin.getServer().getPluginManager().registerEvents(worldSettings, javaPlugin);
        worldSettings.setUpWorldSettings(javaPlugin, settings);

        new IScoreBoardTask().runTaskTimerAsynchronously(javaPlugin, 0L, 1L);
    }

    /**
     * Setting up configurations
     */
    @Override
    public void initializeSettings(JavaPlugin javaPlugin) {
        settings = new ConfigFile(javaPlugin, "settings.yml");
    }

    /**
     * @return STRING 00:00 TIMER USED FOR PLACEHOLDER ONLY!
     */
    @Override
    public String currentTime() {
        int timer = gameTask != null ? gameTask.counter : 0;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.SECOND, timer);
        return new SimpleDateFormat("mm:ss").format(calendar.getTime());
    }

    @Override
    public void loadKits(Set<PlayerData> playerDataSet) {
        //Give kits to all players
        KitHandleEvent kitHandleEvent = new KitHandleEvent(TFW.getInstance(), playerDataSet);
        Bukkit.getServer().getPluginManager().callEvent(kitHandleEvent);
    }

    @Override
    public void startGameTask() {
        gameTask = new GameTask();
        gameTask.runTaskTimer(TFW.getInstance(), 20L, 20L);
    }

    @Override
    public String game_info() {

        TextComponent textComponent = new TextComponent("Current State: " + GameStates.getGameStates().name().toUpperCase(Locale.ROOT) + "\n");

        textComponent.addExtra(TeamManager.getA().getTeam() + ": \n");
        textComponent.addExtra(TeamManager.getA().getMembersAsString().toLegacyText() + "\n");
        textComponent.addExtra(TeamManager.getB().getTeam() + ": " + "\n");
        textComponent.addExtra(TeamManager.getB().getMembersAsString().toLegacyText() + "\n");

        textComponent.addExtra("Staff Team: \n");
        textComponent.addExtra(TFWLoader.getPlayerManager().getStaffAsString().toLegacyText() + "\n");

        return textComponent.toLegacyText();
    }

    @Override
    public void startGame() {
        GameStartEvent gameStartEvent = new GameStartEvent();
        Bukkit.getServer().getPluginManager().callEvent(gameStartEvent);
    }

    /**
     * Teleport Teams to their destination!
     */
    @Override
    public void teleportPlayers() {
        PreparePlayersEvent preparePlayersEvent = new PreparePlayersEvent(TFWLoader.getPlayerManager().exceptStaff());
        Bukkit.getServer().getPluginManager().callEvent(preparePlayersEvent);
    }

    /**
     * Sound Effect for all players in the game!
     */
    @Override
    public void playSound(Sound sound) {
        for (PlayerData playerData : TeamManager.getA().alive_members())
            playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
        for (PlayerData playerData : TeamManager.getB().alive_members())
            playerData.getPlayer().playSound(playerData.getPlayer().getLocation(), Sound.AMBIENCE_THUNDER, 1.0f, 1.0f);
    }

    /**
     * Async Message sender!, If message == null then skip!
     * @param message Message is being sent to all teams!
     */
    @Override
    public void notification(String message) {

        assert message != null;

        final String finalMessage = Style.translate(message);
        TFW.getInstance().getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), () -> {

            TFWLoader.getPlayerManager().filtered_online_players().forEach(playerData ->
                playerData.getPlayer().sendMessage(finalMessage));
        });
    }

    @Override
    public void title_Notification(String title, String subTitle) {

        assert title != null;
        assert subTitle != null;

        final String finaltitle = Style.translate(title);
        final String finalsubTitle = Style.translate(subTitle);

        TFW.getInstance().getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), () -> {

            TFWLoader.getPlayerManager().filtered_online_players().forEach(playerData -> {
                //Already checked players is online!
                try {
                    ReflectionUtil.sendTitle(playerData.getPlayer(), finaltitle, finalsubTitle, 5, 20, 5);
                } catch (Exception ignore) {}
            });
        });
    }

    /**
     * Celebrating winners, cool effects and stuff
     */
    @Override
    public void celebrate(Team winners) {
        CelebrationEvent celebrationEvent = new CelebrationEvent(winners);
        Bukkit.getServer().getPluginManager().callEvent(celebrationEvent);
    }

    /**
     * Disabling all things and stop the server!
     */
    @Override
    public void restartTheGame() {
        GameStates.setGameStates(GameStates.RESTART);
        notification("&c&lCLOSING THE SERVER IN RIGHT NOW!");

        Bukkit.getServer().getScheduler().runTaskLater(TFW.getInstance(), ()->{
            Bukkit.getServer().shutdown();
        }, 60L);
    }

    @Override
    public Arena getArena() {
        return ArenaManager.getArena();
    }

    public enum GameStates{
        LOBBY,COUNTDOWN,INGAME,ENDING,RESTART;

        @Getter@Setter
        private static GameStates gameStates;
    }
}
