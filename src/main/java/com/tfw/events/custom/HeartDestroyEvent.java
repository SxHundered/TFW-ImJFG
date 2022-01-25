package com.tfw.events.custom;

import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
@Getter
public class HeartDestroyEvent extends Event {

    @Setter
    private boolean canceled = false;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final PlayerData destroyer;
    private final Team team;

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
