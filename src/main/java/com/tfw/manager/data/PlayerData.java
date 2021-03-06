package com.tfw.manager.data;

import com.tfw.configuration.Style;
import com.tfw.game.GameManager;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.team.Team;
import com.tfw.scoreboard.AsyncBoard;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

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

    private final String defaultTeam;

    private Team team;
    private PlayerBoard fastBoard;
    private PlayerStatus playerStatus = PlayerStatus.LOBBY;

    public Player getPlayer(){
        return Bukkit.getPlayer(this.uuid);
    }

    /**
     *
     * Prepare the player and give scoreboard!
     *
     */
    @Override
    public void preparePlayer() {

        if (!TFWLoader.getPlayerManager().getOffline_name_staff().contains(playerName)) {
            updateStatus(PlayerStatus.LOBBY);
            clearPlayer();
        }else {
            updateStatus(PlayerStatus.STAFF);
            staffApply();
        }

        selectTeam(team);
        generateScoreboard();
    }

    /**
     * @return Is the player online ?
     */
    public boolean isOnline(){
        return Bukkit.getPlayer(uuid) != null;
    }

    @Override
    public void backToHome() {
        if (!isOnline())
            return;

        clearPlayer();
        getPlayer().teleport(TFWLoader.getArenaManager().getSpawn().toBukkitLocation());
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
        getPlayer().setHealth(20);
        getPlayer().setMaxHealth(20);
        getPlayer().setFoodLevel(20);
        getPlayer().setSaturation(12.8F);
        getPlayer().setMaximumNoDamageTicks(20);
        getPlayer().setFireTicks(0);
        getPlayer().setFallDistance(0.0F);
        getPlayer().setLevel(0);
        getPlayer().setExp(0.0F);
        getPlayer().setWalkSpeed(0.2F);
        getPlayer().getInventory().setHeldItemSlot(0);
        getPlayer().setAllowFlight(false);
        getPlayer().getInventory().clear();
        getPlayer().getInventory().setArmorContents(null);
        getPlayer().closeInventory();
        getPlayer().setGameMode(GameMode.SURVIVAL);
        for (PotionEffect activePotionEffect : getPlayer().getActivePotionEffects())
            getPlayer().removePotionEffect(activePotionEffect.getType());
        getPlayer().updateInventory();
    }

    /**
     * Staff Mode - Apply GameMode Spectator
     */
    @Override
    public void staffApply() {
        getPlayer().setHealth(20);
        getPlayer().setMaxHealth(20);
        getPlayer().setFoodLevel(20);
        getPlayer().setSaturation(12.8F);
        getPlayer().setMaximumNoDamageTicks(20);
        getPlayer().setFireTicks(0);
        getPlayer().setFallDistance(0.0F);
        getPlayer().setLevel(0);
        getPlayer().setExp(0.0F);
        getPlayer().setWalkSpeed(0.2F);
        getPlayer().getInventory().setHeldItemSlot(0);
        getPlayer().setAllowFlight(false);
        getPlayer().getInventory().clear();
        getPlayer().getInventory().setArmorContents(null);
        getPlayer().closeInventory();
        getPlayer().setGameMode(GameMode.SPECTATOR);
        getPlayer().setAllowFlight(true);
        getPlayer().updateInventory();
    }

    @Override
    public void selectTeam(Team iTeam) {
        setTeam(iTeam);
    }

    /**
     * create a full scoreboard for the getPlayer().
     */
    @Override
    public void generateScoreboard() {

        //Sync
        PlayerBoard playerBoard = new PlayerBoard(getPlayer(), this);
        setFastBoard(playerBoard);
        //
        settings.setRefresh(true);

        AsyncBoard.createTeams_Instance(playerBoard);
        getPlayer().setScoreboard(playerBoard.getScoreboard());
        fastBoard.updateTitle();

        //Async
        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), ()->{
            AsyncBoard.getBoardArrayList().add(getFastBoard());
            AsyncBoard.updateTitle();
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

    public String tabName(){
        return playerName;
    }
}