package com.tfw.manager.data;

import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.team.Team;
import com.tfw.scoreboard.AsyncBoard;
import com.tfw.scoreboard.IScoreboardException;
import com.tfw.scoreboard.IScoreboardManager;
import com.tfw.scoreboard.PlayerBoard;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Async;

import java.util.Locale;
import java.util.UUID;

/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *
 *     Players details
 */

@RequiredArgsConstructor@Data
public class PlayerData implements IData{

    /*
        TODO:
         1- players details -> Done
         2- stats details -> Done
         3- player status -> Done
         4- team -> Done
         5- Staff checking
    */

    private final String playerName;
    private final UUID uuid;

    private final Stats stats = Stats.STATS;
    private final Settings settings = Settings.PLAYER_SETTINGS;

    private Team team;
    private PlayerBoard fastBoard;
    private PlayerStatus playerStatus = PlayerStatus.LOBBY;

    public Player getPlayer(){
        return Bukkit.getPlayer(this.uuid);
    }

    /**
     * Prepare the player and give scoreboard!
     */
    @Override
    public void preparePlayer() {
        clearPlayer();
        updateStatus(PlayerStatus.LOBBY);
        selectTeam(team);
        generateScoreboard();
    }

    /**
     * @return Is the player online ?
     */
    public boolean isOnline(){
        return Bukkit.getPlayer(uuid) != null;
    }

    /**
     * @param massage String wanted to be sent
     */
    public void textPlayer(String massage){
        if (isOnline())
            getPlayer().sendMessage(Style.translate(massage));
    }

    /**
     * @param massage String wanted to be sent
     */
    public void textPlayer(TextComponent massage){
        if (isOnline())
            getPlayer().spigot().sendMessage(massage);
    }

    /**
     * @param playerStatus changing the player status.
     */
    @Override
    public void updateStatus(PlayerStatus playerStatus) {
        setPlayerStatus(playerStatus);
    }

    /**
     * Preparing the player for the game.
     */
    @Override
    public void clearPlayer() {
        //TODO
    }

    @Override
    public void selectTeam(Team iTeam) {
        setTeam(iTeam);
    }

    /**
     * create a full scoreboard for the player.
     */
    @Override
    public void generateScoreboard() {

        //Sync
        PlayerBoard playerBoard = new PlayerBoard(getPlayer(), this);
        setFastBoard(playerBoard);
        //
        settings.setRefresh(true);

        AsyncBoard.createTeams_Instance(getPlayer(), playerBoard, 0);
        getPlayer().setScoreboard(playerBoard.getScoreboard());

        //Async
        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), ()->{
            AsyncBoard.getBoardArrayList().add(getFastBoard());
            AsyncBoard.updateTitle();
            try {
                getFastBoard().setIScoreboard(TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.LOBBY));
            } catch (IScoreboardException e) {
                Bukkit.getConsoleSender().sendMessage(e.getMessage());
            }
        });
    }

    /**
     * Update Kills of the player!
     */
    @Override
    public void updateStats() {
        stats.setKills(stats.kills + 1);
    }

    /**
     * Apply staff mode to the player!
     *
     * @param playerData wanted to be staff!
     */
    public static void changeToStaff(PlayerData playerData){
        //Changes what is necessary to be !
        //TODO
        playerData.getSettings().setStaff(true);

    }

    /**
     * Return player to normal mode
     *
     * @param playerData wanted to be normal!
     */
    public static void changeBackToNormal(PlayerData playerData){
        //Changes what is necessary to be !
        //TODO
        playerData.getSettings().setStaff(false);

    }

    /**
     * If debug mode is enabled then print debug messages
     */
    public void printDebug(){
        Bukkit.getConsoleSender().sendMessage(Style.translate("&4&lDEBUG &7>>"));
        Bukkit.getConsoleSender().sendMessage(Style.YELLOW + playerName + Style.BLUE + "{");
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "UUID: " + Style.YELLOW + uuid.toString());
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "Stats: kills -> " + stats.kills);
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "Settings: " + settings.info());
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "Status: " + playerStatus.name().toUpperCase(Locale.ROOT));
        fastBoard.getIScoreboard().info();
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "}");
    }
}