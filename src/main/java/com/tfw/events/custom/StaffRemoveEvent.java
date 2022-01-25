package com.tfw.events.custom;

import com.tfw.manager.data.PlayerData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
@Getter
public class StaffRemoveEvent extends Event {

    @Setter
    private boolean canceled = false;

    @Getter
    private final String name;

    @Getter
    private final PlayerData executorData;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}