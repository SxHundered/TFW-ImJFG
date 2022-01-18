package com.tfw.manager;

import com.tfw.configuration.Style;
import com.tfw.events.custom.TeamLeaveEvent;
import com.tfw.main.TFW;
import com.tfw.manager.data.PlayerData;
import com.tfw.scoreboard.AsyncBoard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
    private final List<PlayerData> playerDataList = new ArrayList<>();

    /**
     * @param player player needed to be added into the list
     */
    public void addPlayer(Player player){
        PlayerData playerData = new PlayerData(player.getName(), player.getUniqueId());

        playerData.preparePlayer();

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

        //Deleting the player from his team,
        final String teamOrder = playerData.getTeam().getTeamOrder();

        //The player is still on a team, meaning that we might be in LOBBY STATE SO YEAH :p
        if (playerData.getTeam() != null)
            playerData.getTeam().removeTeamPlayer(playerData, "leave");

        AsyncBoard.getBoardArrayList().remove(playerData.getFastBoard());

        for (PlayerData playerDatas : playerDataList)
            if (playerDatas.isOnline())
                playerDatas.getFastBoard().delete(teamOrder);

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

        playerData.getPlayer().kickPlayer(Style.RED + "You have lost this tournament! \n §eHave a lovely day");

        //Elimination team event!
        if (playerData.getTeam() != null){
            TeamLeaveEvent teamLeaveEvent = new TeamLeaveEvent(playerData, "killed");
            Bukkit.getServer().getPluginManager().callEvent(teamLeaveEvent);
        }
    }
}
