package com.tfw.events.custom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

@RequiredArgsConstructor@Getter
public class BorderSetUpEvent extends Event {

    @Getter
    @Setter
    private boolean canceled = false;

    private final String worldName;
    private final double borderSize;
    private final List<Double> center;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}