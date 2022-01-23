package com.tfw.game.task;

import com.tfw.manager.data.PlayerBoard;
import com.tfw.scoreboard.AsyncBoard;
import org.bukkit.scheduler.BukkitRunnable;


/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *     Async Task Scoreboard Updater!
 */

public class IScoreBoardTask extends BukkitRunnable {

    @Override
    public void run() {
        for (PlayerBoard scoreBoard : AsyncBoard.getBoardArrayList()){
            if (scoreBoard == null)
                continue;
            else if(!scoreBoard.getPlayerData().getSettings().isRefresh())
                continue;

            AsyncBoard.generate_ifExists(scoreBoard);

            //Update lines
            AsyncBoard.updateBoard(scoreBoard);
        }
    }
}
