package com.tfw.events;

import com.tfw.events.custom.CelebrationEvent;
import com.tfw.events.custom.GameRestartEvent;
import com.tfw.events.custom.GameStartEvent;
import com.tfw.game.GameManager;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamManager;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.data.PlayerStatus;
import com.tfw.scoreboard.IScoreboardException;
import com.tfw.scoreboard.IScoreboardManager;
import org.bukkit.Bukkit;
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

        gameStartEvent.toggleScoreBoard();

        TFWLoader.getGameManager().notification("&c&lTHE WAR HAS STARTED!");
        TFWLoader.getGameManager().notification("&9&lTEAM: " + TeamManager.getA().getIdentifier() + " &c&lVS &9&lTEAM: " + TeamManager.getB().getIdentifier());

        TFWLoader.getGameManager().title_Notification(
                TeamManager.getA().getColorTeam() + TeamManager.getA().getIdentifier()
                        + " &c&lvs " +
                        TeamManager.getB().getColorTeam() + TeamManager.getB().getIdentifier(),
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

        GameManager.GameStates.setGameStates(GameManager.GameStates.ENDING);
        celebrationEvent.toggleScoreBoard();


        TFWLoader.getGameManager().notification("%prefix% &c&lGREAT WAR, &a&lEVERYONE WAS HONOR!");
        TFWLoader.getGameManager().notification("  " + celebrationEvent.getWinners().getName() + " has won the tournament!");
        TFWLoader.getGameManager().notification("  Score: ");
        TFWLoader.getGameManager().notification("    &aKills &7-> &c&l" + celebrationEvent.getWinners().getKills() + "");

        for (PlayerData playerData : TFWLoader.getPlayerManager().filtered_online_players())
            playerData.backToHome();

        celebrationEvent.start_Celebration();
    }
}
