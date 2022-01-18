package com.tfw.events.custom;

import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
@Getter
public class TeamJoinEvent extends Event {

    @Getter
    @Setter
    private boolean canceled = false;

    private final PlayerData playerData;

    private final Team team;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}