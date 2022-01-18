package com.tfw.events;

import com.tfw.events.custom.PlayerEliminationEvent;
import com.tfw.events.custom.TFWJoinEvent;
import com.tfw.events.custom.TFWLeaveEvent;
import com.tfw.main.TFWLoader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener {


    /**
     * @param tfwJoinEvent Handle player join event!
     */
    @EventHandler
    public void joinHandle(TFWJoinEvent tfwJoinEvent){

        //Adding the player to the list!, and make the modifications
        final Player player = tfwJoinEvent.getPlayer();

        TFWLoader.getPlayerManager().addPlayer(player);
    }

    /**
     * @param tfwLeaveEvent Handle the quit event when the player leaves the server!
     */
    @EventHandler
    public void quitHandle(TFWLeaveEvent tfwLeaveEvent){

        //Now here we delete the player from the game!
        TFWLoader.getPlayerManager().removePlayer(tfwLeaveEvent.getPlayerData());
    }

    /**
     * @param playerEliminationEvent Handle the player elimination in the game!
     */
    @EventHandler
    public void onElimination(PlayerEliminationEvent playerEliminationEvent){

        //Eliminate the player, and then kick him from the game!
        TFWLoader.getPlayerManager().eliminatePlayer(playerEliminationEvent.getPlayerData());
    }

}
