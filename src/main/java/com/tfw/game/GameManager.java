package com.tfw.game;

import lombok.AccessLevel;
import lombok.Getter;

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

@Getter(AccessLevel.PACKAGE)
public class GameManager implements IGame{

    /**
     * Starts the game
     */

    @Override
    public int gameTime() {
        return timer;
    }

    @Override
    public String currentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.SECOND, timer);
        return new SimpleDateFormat("mm:ss").format(calendar.getTime());
    }

    @Override
    public void startGame() {

    }


}
