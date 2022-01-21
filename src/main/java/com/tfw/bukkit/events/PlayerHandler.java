package com.tfw.bukkit.events;

import com.tfw.events.custom.PlayerEliminationEvent;
import com.tfw.events.custom.TFWJoinEvent;
import com.tfw.events.custom.TFWLeaveEvent;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerHandler implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        playerJoinEvent.setJoinMessage(null);

        TFWJoinEvent tfwJoinEvent = new TFWJoinEvent(playerJoinEvent.getPlayer());
        Bukkit.getServer().getPluginManager().callEvent(tfwJoinEvent);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        playerQuitEvent.setQuitMessage(null);
        PlayerData playerData = TFWLoader.getPlayerManager().data(playerQuitEvent.getPlayer().getName());

        assert playerData != null;

        TFWLeaveEvent tfwLeaveEvent = new TFWLeaveEvent(playerData);
        Bukkit.getServer().getPluginManager().callEvent(tfwLeaveEvent);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onKill(PlayerDeathEvent playerDeathEvent) {
        playerDeathEvent.setDroppedExp(0);
        playerDeathEvent.getDrops().clear();

        PlayerData playerData = TFWLoader.getPlayerManager().data(playerDeathEvent.getEntity().getName());

        assert playerData != null;

        PlayerEliminationEvent playerEliminationEvent = new PlayerEliminationEvent(playerData, playerDeathEvent.getEntity().getLocation());
        Bukkit.getServer().getPluginManager().callEvent(playerEliminationEvent);

    }

}