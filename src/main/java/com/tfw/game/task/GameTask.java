package com.tfw.game.task;

import com.tfw.game.GameManager;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    public int counter = 0;

    @Override
    public void run() {
        if (!GameManager.GameStates.getGameStates().equals(GameManager.GameStates.INGAME)) {
            cancel();
            return;
        }

        //TIMER - PLACEHOLDER TIMER!
        counter++;
    }
}
