package com.tfw.manager.team;

import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.heart.Heart;
import com.tfw.utils.CustomLocation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

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
 *     list of functions for team class!
 */
public interface ITeam {

    String getTeam();

    List<PlayerData> alive_members();

    List<PlayerData> getMembers();

    boolean isInSameTeam(PlayerData target);

    void broadcastTeam(String message);

    int currentAlive();

    int totalTeamMembers();

    boolean addTeamPlayer(PlayerData target);

    void removeTeamPlayer(PlayerData target, String reason);

    ChatColor getColorTeam();

    String chat_Format();

    void generateHeart(EntityType entityType, CustomLocation location);

    Heart getHeart();
}
