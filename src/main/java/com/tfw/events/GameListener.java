package com.tfw.events;

import com.tfw.events.custom.CelebrationEvent;
import com.tfw.events.custom.GameRestartEvent;
import com.tfw.events.custom.GameStartEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameListener implements Listener {

    @EventHandler
    public void onGameStart(GameStartEvent gameStartEvent){

    }

    @EventHandler
    public void onGameRestart(GameRestartEvent gameRestartEvent){

    }

    @EventHandler
    public void onCelebration(CelebrationEvent celebrationEvent){

    }
}
