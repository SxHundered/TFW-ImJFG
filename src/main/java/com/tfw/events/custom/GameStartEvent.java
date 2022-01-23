package com.tfw.events.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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


    public void toggleScoreBoard(){
        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), ()-> {
            for (PlayerData playerData : TFWLoader.getPlayerManager().filtered_online_players()) {
                if (playerData.isOnline()) {
                    try {
                        if (playerData.getTeam() != null)
                            playerData.setPlayerStatus(PlayerStatus.PLAYING);

                        playerData.getFastBoard().setIScoreboard(
                                TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.INGAME));
                    } catch (IScoreboardException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}