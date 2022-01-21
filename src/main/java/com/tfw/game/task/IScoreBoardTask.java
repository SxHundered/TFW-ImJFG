package com.tfw.game.task;

import com.tfw.game.GameManager;
import org.bukkit.scheduler.BukkitRunnable;


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

// TODO:
public class IScoreBoardTask extends BukkitRunnable {

    //WE DO NOT STOP THIS TASK AT ALL!

    @Override
    public void run() {

        switch (GameManager.GameStates.getGameStates()){
            case LOBBY:
                break;
            case INGAME:
                break;
            case RESTART:
                break;
            case COUNTDOWN:
                break;
        }
    }
}
