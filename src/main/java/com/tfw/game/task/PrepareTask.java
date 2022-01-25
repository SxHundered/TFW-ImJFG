package com.tfw.game.task;

import com.tfw.configuration.Style;
import com.tfw.game.GameManager;
import com.tfw.main.TFWLoader;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Repeating Task for countdown in the game!
 */
public class PrepareTask extends BukkitRunnable {

    public static short countdown;

    public PrepareTask(short countDowns) {
        GameManager.GameStates.setGameStates(GameManager.GameStates.COUNTDOWN);
        countdown = countDowns;
        Bukkit.getServer().getConsoleSender().sendMessage(Style.translate("&9&lPREPARE TASK -> &a&lSTARTED"));
    }

    @Override
    public void run() {
        if (!GameManager.GameStates.getGameStates().equals(GameManager.GameStates.COUNTDOWN)) {
            cancel();
            return;
        }

        if (countdown != 0) {
            countdown--;
            switch (countdown){
                case 3:
                case 2:
                case 1:
                    TFWLoader.getGameManager().title_Notification((countdown == 3 ? "&a3" : countdown == 2 ? "&b2" : countdown == 1 ? "&c1" : null), "");
                    TFWLoader.getGameManager().notification(countdown == 3 ? "%prefix% &a3" : countdown == 2 ? "%prefix% &b2" : countdown == 1 ? "%prefix% &c1" : null);
                    break;
            }
        }else
            //Start the game!
            TFWLoader.getGameManager().startGame();
    }
}
