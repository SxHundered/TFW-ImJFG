package com.tfw.events;

import com.tfw.events.custom.BorderSetUpEvent;
import com.tfw.events.custom.KitHandleEvent;
import com.tfw.events.custom.PrepareArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SettingsListener implements Listener {

    @EventHandler
    public void onBorderEvent(BorderSetUpEvent borderSetUpEvent){

        final World world = Bukkit.getWorld(borderSetUpEvent.getWorldName());
        assert world != null;

        world.getWorldBorder().setCenter(borderSetUpEvent.getCenter()[0], borderSetUpEvent.getCenter()[1]);
        world.getWorldBorder().setSize(borderSetUpEvent.getBorderSize());


    }

    @EventHandler
    public void onKitHandler(KitHandleEvent kitHandleEvent){

    }

    @EventHandler
    public void onPrepare(PrepareArenaEvent prepareArenaEvent){

    }
}
