package com.tfw.events;

import com.tfw.events.custom.*;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamManager;
import com.tfw.manager.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;


/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *
 */

public class TeamListener implements Listener {

    /**
     * @param teamJoinEvent Used when we want to add player into a specific team!
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoinTeam(TeamJoinEvent teamJoinEvent) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), () -> {
            teamJoinEvent.getTeam().addTeamPlayer(teamJoinEvent.getPlayerData());
        });
    }


    /**
     * @param teamLeaveEvent Used when we want to remove a player from a specific team!
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLeaveTeam(TeamLeaveEvent teamLeaveEvent) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), () -> {
            teamLeaveEvent.getPlayerData().getTeam().removeTeamPlayer(teamLeaveEvent.getPlayerData(), teamLeaveEvent.getReason());
        });
    }


    /**
     * @param teamEliminationEvent Used when we want to eliminate the team
     *                             and then start the celebration event for the second team!
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onTeamElimination(TeamEliminationEvent teamEliminationEvent) {
        final Team team = teamEliminationEvent.getTeam();
        final Team winners = team.equals(TeamManager.getA()) ? TeamManager.getB() : TeamManager.getA();

        TeamManager.setWinners(winners);

        TFWLoader.getGameManager().celebrate(winners);
    }


    @EventHandler
    public void heartSpawn(HeartSpawnEvent heartSpawnEvent){
        heartSpawnEvent.getTeam().spawnHeart();
    }

    @EventHandler
    public void heartDestroy(HeartDestroyEvent heartDestroyEvent){

        TeamEliminationEvent teamEliminationEvent = new TeamEliminationEvent(heartDestroyEvent.getTeam());
        Bukkit.getServer().getPluginManager().callEvent(teamEliminationEvent);
    }
}