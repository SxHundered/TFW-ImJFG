package com.tfw.events;

import com.tfw.events.custom.*;
import com.tfw.game.GameManager;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamManager;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.messages.Messages;
import com.tfw.manager.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


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


    final String GAME_STARTED = Messages.GAME_STARTED.toString();
    final String GAME_STARTEDTITLE = Messages.GAME_STARTEDTITLE.toString();
    final String GAME_STARTEDSUUBTITLE = Messages.GAME_STARTEDSUUBTITLE.toString();
    final String CAN_NOT_BREAK_HEART = Messages.CAN_NOT_BREAK_HEART.toString();
    final String WINNING_MESSAGE = Messages.WINNING_MESSAGE.toString();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGameStart(GameStartEvent gameStartEvent) {
        GameManager.GameStates.setGameStates(GameManager.GameStates.INGAME);

        gameStartEvent.toggleScoreBoard();

        TFWLoader.getGameManager().notification(GAME_STARTED.replace("%teamA%", TeamManager.getA().getIdentifier()).replace("%teamB%", TeamManager.getB().getIdentifier()));

        TFWLoader.getGameManager().title_Notification(
                GAME_STARTEDTITLE.replace("%teamA%", TeamManager.getA().getColorTeam() + TeamManager.getA().getIdentifier())
                        .replace("%teamB%", TeamManager.getB().getColorTeam() + TeamManager.getB().getIdentifier()),
                GAME_STARTEDSUUBTITLE);

        TFWLoader.getGameManager().playSound(Sound.ENDERDRAGON_GROWL);

        HeartSpawnEvent heartSpawnEvent = new HeartSpawnEvent(TeamManager.getA());
        Bukkit.getServer().getPluginManager().callEvent(heartSpawnEvent);

        heartSpawnEvent = new HeartSpawnEvent(TeamManager.getB());
        Bukkit.getServer().getPluginManager().callEvent(heartSpawnEvent);
        
        //Maybe we use gate to open the gate!
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGameRestart(GameRestartEvent gameRestartEvent){
        GameManager.GameStates.setGameStates(GameManager.GameStates.RESTART);
        TFWLoader.getGameManager().restartTheGame();
    }

    /**
     * @param blockBreakEvent Heart Destroy through breaking the heart!
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBedDestroy(BlockBreakEvent blockBreakEvent){
        if (GameManager.GameStates.getGameStates().equals(GameManager.GameStates.INGAME)){
            Material material = blockBreakEvent.getBlock().getType();

            //Match material block with the heart!
            if (material.equals(TFWLoader.getGameManager().getHeartType())){
                blockBreakEvent.setCancelled(true);
                PlayerData playerData = TFWLoader.getPlayerManager().data(blockBreakEvent.getPlayer().getUniqueId());
                if (playerData == null || playerData.getTeam() == null)
                    return;

                final Team team = TFWLoader.getGameManager().isHeartTeam(blockBreakEvent.getBlock().getLocation());

                if (team == null)
                    return;
                if (team == playerData.getTeam()){
                    //Detected same team!
                    playerData.textPlayer(CAN_NOT_BREAK_HEART);
                }else{
                    //Break heart!
                    blockBreakEvent.getBlock().setType(Material.AIR);
                    HeartDestroyEvent heartDestroyEvent = new HeartDestroyEvent(playerData, team);
                    Bukkit.getServer().getPluginManager().callEvent(heartDestroyEvent);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCelebration(CelebrationEvent celebrationEvent){

        GameManager.GameStates.setGameStates(GameManager.GameStates.ENDING);
        celebrationEvent.toggleScoreBoard();

        TFWLoader.getGameManager().notification(WINNING_MESSAGE.replace("%winners%", celebrationEvent.getWinners().getName()).replace("%kills%", "" + celebrationEvent.getWinners().getKills()));

        for (PlayerData playerData : TFWLoader.getPlayerManager().filtered_online_players())
            playerData.backToHome();

        celebrationEvent.start_Celebration();
    }
}
