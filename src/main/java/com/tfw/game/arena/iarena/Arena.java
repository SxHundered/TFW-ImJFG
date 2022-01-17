package com.tfw.game.arena.iarena;

import com.tfw.manager.team.ITeam;

/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *
 *     Arena details class!
 */
public class Arena implements IArena{


    @Override
    public void notification() {
        //All teams recieve massage!
    }

    /**
     * @param team Specify team to send notification
     */
    @Override
    public void notification(ITeam team) {
        for (String player : team.getTeam().getPlayers()) {
            //send nitu
        }
    }
}
