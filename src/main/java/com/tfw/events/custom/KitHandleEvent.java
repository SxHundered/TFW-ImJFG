package com.tfw.events.custom;

import com.tfw.manager.data.PlayerData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

@RequiredArgsConstructor@Getter
public class KitHandleEvent extends Event {

    @Setter
    private boolean canceled = false;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final JavaPlugin javaPlugin;
    private final Set<PlayerData> playerDataSet;

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public void loadKits(){
        Bukkit.getServer().getScheduler().runTaskLater(javaPlugin, ()-> {
            for (PlayerData playerData : playerDataSet) {
                //Skip condition
                if (!playerData.isOnline() || playerData.getTeam() == null)
                    continue;
                playerData.clearPlayer();
                playerData.getTeam().getKit().giveKit(playerData);
            }
        }, 1L);
    }
}