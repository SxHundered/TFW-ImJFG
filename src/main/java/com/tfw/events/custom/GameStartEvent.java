package com.tfw.events.custom;

import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.data.PlayerStatus;
import com.tfw.scoreboard.IScoreboardException;
import com.tfw.scoreboard.IScoreboardManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class GameStartEvent extends Event {

    @Getter
    @Setter
    private boolean canceled = false;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }


    public void toggleScoreBoard() {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), () -> {
            for (PlayerData playerData : TFWLoader.getPlayerManager().filtered_online_players()) {
                if (playerData.isOnline()) {
                    try {
                        if (playerData.getTeam() != null) {
                            playerData.setPlayerStatus(PlayerStatus.PLAYING);
                            playerData.getFastBoard().setIScoreboard(TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.INGAME));
                        }else
                            playerData.getFastBoard().setIScoreboard(TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.STAFF));
                    } catch (IScoreboardException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}