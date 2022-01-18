package com.tfw.manager.data;

import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import com.tfw.manager.team.Team;
import com.tfw.scoreboard.AsyncBoard;
import com.tfw.scoreboard.PlayerBoard;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

}