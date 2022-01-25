package com.tfw.manager;

import com.tfw.configuration.Style;
import com.tfw.events.custom.TeamLeaveEvent;
import com.tfw.main.TFW;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.data.PlayerStatus;
import com.tfw.scoreboard.AsyncBoard;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    @Getter
    private final Set<String> offline_name_staff = new HashSet<>();

    /**
     * @param player player needed to be added into the list
     */
    public void addPlayer(Player player) {
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
     * Checks whether the player inside a team or not!
     * Disabling scoreboard for the player!
     *
     * @param playerData needed to be removed from the list!
     */
    @Override
    public void removePlayer(PlayerData playerData) {

        /*
            Detects if player is staff or not!
         */
        if (playerData.getPlayerStatus().equals(PlayerStatus.STAFF)){
            offline_name_staff.add(playerData.getPlayerName());

            for (PlayerData playerDatas : playerDataList)
                if (playerDatas.isOnline())
                    playerDatas.getFastBoard().delete(playerData.getDefaultTeam());
            Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), () -> playerDataList.remove(playerData));
            return;
        }


        playerData.setPlayerStatus(PlayerStatus.DEAD);

        AsyncBoard.getBoardArrayList().remove(playerData.getFastBoard());

        String teamRemoval;

        //The player is still on a team, meaning that we might be in LOBBY STATE SO YEAH :p
        if (playerData.getTeam() != null) {
            //Deleting the player from his team,
            teamRemoval = playerData.getTeam().getIdentifier();

            playerData.getTeam().removeTeamPlayer(playerData, "leave");
        } else
            teamRemoval = playerData.getDefaultTeam();

        for (PlayerData playerDatas : playerDataList)
            if (playerDatas.isOnline())
                playerDatas.getFastBoard().delete(teamRemoval);

        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), () -> playerDataList.remove(playerData));
    }

    /**
     * @param uuid retrieving the player-data using the name
     * @return return player-data
     */
    @Override
    public PlayerData data(UUID uuid) {
        return playerDataList.stream().filter(playerData -> playerData.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    /**
     * @param playerData eliminate the player from the game!
     *                   then eliminates him from the team!
     */
    @Override
    public void eliminatePlayer(PlayerData playerData) {

        playerData.setPlayerStatus(PlayerStatus.DEAD);
        playerData.getPlayer().kickPlayer(Style.translate("&cYou have lost this tournament! \n &aNext Time"));

        //Elimination team event!
        if (playerData.getTeam() != null) {
            TeamLeaveEvent teamLeaveEvent = new TeamLeaveEvent(playerData, "killed");
            Bukkit.getServer().getPluginManager().callEvent(teamLeaveEvent);
        }
    }

    /**
     * @return Retrieve only online players in the server!
     */
    @Override
    public Set<PlayerData> filtered_online_players() {
        return getPlayerDataList().stream().filter(Objects::nonNull).filter(PlayerData::isOnline).collect(Collectors.toSet());
    }

    /**
     * @return Retrieve all players except staff ones
     */
    @Override
    public Set<PlayerData> exceptStaff() {
        return getPlayerDataList().stream().filter(Objects::nonNull).filter(playerData -> playerData.isOnline() && !playerData.getSettings().isStaff()).collect(Collectors.toSet());
    }

    @Override
    public Set<PlayerData> onlyTeamPlayers() {
        return getPlayerDataList().stream().filter(Objects::nonNull).filter(playerData -> playerData.isOnline() && !playerData.getSettings().isStaff() && playerData.getTeam() != null).collect(Collectors.toSet());
    }

    @Override
    public TextComponent getStaffAsString() {
        TextComponent textComponent = new TextComponent(ChatColor.DARK_RED + "Staff: ");

        getPlayerDataList().stream().filter(Objects::nonNull).forEach(playerData -> {
            if (playerData.getPlayerStatus().equals(PlayerStatus.STAFF))
                textComponent.addExtra(ChatColor.RED + playerData.getPlayerName() + ChatColor.GRAY + ", ");
                }
        );
        return textComponent;
    }
}
