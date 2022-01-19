package com.tfw.events.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor@Getter
public class BorderSetUpEvent extends Event {

    @Getter
    @Setter
    private boolean canceled = false;

    private final String worldName;
    private final double borderSize;
    private final double[] center;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}