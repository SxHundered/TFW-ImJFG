package com.tfw.events;

import com.tfw.events.custom.CelebrationEvent;
import com.tfw.events.custom.GameRestartEvent;
import com.tfw.events.custom.GameStartEvent;
import com.tfw.game.GameManager;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamManager;
import org.bukkit.Sound;
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

public class GameListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGameStart(GameStartEvent gameStartEvent) {
        GameManager.GameStates.setGameStates(GameManager.GameStates.INGAME);

        TFWLoader.getGameManager().notification("&c&lTHE WAR HAS STARTED!");
        TFWLoader.getGameManager().notification("&9&lTEAM: " + TeamManager.getA().getTeam() + " &c&lVS &9&lTEAM: " + TeamManager.getB().getTeam());

        TFWLoader.getGameManager().title_Notification(
                TeamManager.getA().getColorTeam() + TeamManager.getA().getTeam()
                        + " &c&lvs " +
                        TeamManager.getB().getColorTeam() + TeamManager.getB().getTeam(),
                "&6&lFIGHT FOR HONOR");
        TFWLoader.getGameManager().playSound(Sound.ENDERDRAGON_GROWL);

        //Maybe we use gate to open the gate!

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGameRestart(GameRestartEvent gameRestartEvent){
        GameManager.GameStates.setGameStates(GameManager.GameStates.RESTART);
        TFWLoader.getGameManager().restartTheGame();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCelebration(CelebrationEvent celebrationEvent){
        //TODO:
    }
}
