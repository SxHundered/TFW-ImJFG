package com.tfw.manager;

import com.tfw.configuration.Style;
import com.tfw.events.custom.TeamLeaveEvent;
import com.tfw.main.TFW;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.data.PlayerStatus;
import com.tfw.scoreboard.AsyncBoard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *
 *     Only used to modify player data class!
 */

public class PlayerManager implements IManage {

    /*
    TODO:
         1- adding the main scoreboard to addPlayer.
         2- removing the scoreboard @ removePlayer.
    */

    @Getter
    private final List<PlayerData> playerDataList = new CopyOnWriteArrayList<>();

    /**
     * @param player player needed to be added into the list
     */
    public void addPlayer(Player player){
        PlayerData playerData = new PlayerData(player.getName(), player.getUniqueId(),
                (AsyncBoard.getArgs()[0] + "_" +
                        player.getName()).length() >= 16 ? (AsyncBoard.getArgs()[0] + "_" +
                        player.getName()).substring(0, 15) : (AsyncBoard.getArgs()[0] + "_" +
                        player.getName()));

        playerData.preparePlayer();

        if (TFW.isDebug())
            playerData.printDebug();

        playerDataList.add(playerData);
    }

    /**
     *
     * Checks whether the player inside a team or not!
     * Disabling scoreboard for the player!
     *
     * @param playerData needed to be removed from the list!
     */
    @Override
    public void removePlayer(PlayerData playerData) {

        playerData.setPlayerStatus(PlayerStatus.DEAD);

        AsyncBoard.getBoardArrayList().remove(playerData.getFastBoard());

        String teamRemoval;

        //The player is still on a team, meaning that we might be in LOBBY STATE SO YEAH :p
        if (playerData.getTeam() != null) {
            playerData.getTeam().removeTeamPlayer(playerData, "leave");

            //Deleting the player from his team,
            teamRemoval = playerData.getTeam().getTeamOrder();
        }else
            teamRemoval = playerData.getDefaultTeam();

        for (PlayerData playerDatas : playerDataList)
            if (playerDatas.isOnline())
                playerDatas.getFastBoard().delete(teamRemoval);

        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), () -> playerDataList.remove(playerData));
    }

    /**
     * @param playerName retrieving the playerdata using the name
     * @return return player-data
     */
    @Override
    public PlayerData data(String playerName) {
        return playerDataList.stream().filter(playerData -> playerData.getPlayerName().equalsIgnoreCase(playerName)).findFirst().orElse(null);
    }

    /**
     * @param playerData eliminate the player from the game!
     *                   then eliminates him from the team!
     */
    @Override
    public void eliminatePlayer(PlayerData playerData) {
        playerData.setPlayerStatus(PlayerStatus.DEAD);

        playerData.getPlayer().kickPlayer(Style.translate("&cYou have lost this tournament! \n &aHave a lovely day"));

        //Elimination team event!
        if (playerData.getTeam() != null){
            TeamLeaveEvent teamLeaveEvent = new TeamLeaveEvent(playerData, "killed");
            Bukkit.getServer().getPluginManager().callEvent(teamLeaveEvent);
        }
    }

    /**
     * @return Retrieve only online players in the server!
     */
    @Override
    public Stream<PlayerData> filtered_online_players() {
        return getPlayerDataList().stream().filter(Objects::nonNull).filter(PlayerData::isOnline);
    }

    /**
     * @return Retrieve all players except staff ones
     */
    @Override
    public Set<PlayerData> exceptStaff() {
        return getPlayerDataList().stream().filter(Objects::nonNull).filter(playerData -> playerData.isOnline() && !playerData.getSettings().isStaff()).collect(Collectors.toSet());
    }
}
