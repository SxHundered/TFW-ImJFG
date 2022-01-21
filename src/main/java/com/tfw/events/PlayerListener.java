package com.tfw.events;

import com.tfw.events.custom.PlayerEliminationEvent;
import com.tfw.events.custom.TFWJoinEvent;
import com.tfw.events.custom.TFWLeaveEvent;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamManager;
import com.tfw.manager.team.Team;
import org.bukkit.entity.Player;
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
 *     Only handle Game modifications
 */

public class PlayerListener implements Listener {


    /**
     * @param tfwJoinEvent Handle player join event!
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void joinHandle(TFWJoinEvent tfwJoinEvent){
        //Adding the player to the list!, and make the modifications
        final Player player = tfwJoinEvent.getPlayer();

        TFWLoader.getPlayerManager().addPlayer(player);
    }

    /**
     * @param tfwLeaveEvent Handle the quit event when the player leaves the server!
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void quitHandle(TFWLeaveEvent tfwLeaveEvent){

        //Now here we delete the player from the game!
        TFWLoader.getPlayerManager().removePlayer(tfwLeaveEvent.getPlayerData());
    }

    /**
     * @param playerEliminationEvent Handle the player elimination in the game!
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onElimination(PlayerEliminationEvent playerEliminationEvent){

        //Elimination effect!
        Team killerTeam = TeamManager.getA().equals(playerEliminationEvent.getPlayerData().getTeam()) ? TeamManager.getB() : TeamManager.getA();
        killerTeam.eliminationEffect(playerEliminationEvent.getLocation());
        killerTeam.updateStats();

        //Elimination method!
        TFWLoader.getPlayerManager().eliminatePlayer(playerEliminationEvent.getPlayerData());
    }

}
