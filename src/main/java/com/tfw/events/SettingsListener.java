package com.tfw.events;

import com.tfw.events.custom.BorderSetUpEvent;
import com.tfw.events.custom.KitHandleEvent;
import com.tfw.events.custom.PreparePlayersEvent;
import com.tfw.game.task.PrepareTask;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;


/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *     Only handle Game modifications
 */

public class SettingsListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBorderEvent(BorderSetUpEvent borderSetUpEvent) {

        final World world = Bukkit.getWorld(borderSetUpEvent.getWorldName());
        assert world != null;

        //ASYNC HANDLE World Border!
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) world).getHandle();

        worldBorder.setCenter(borderSetUpEvent.getCenter().get(0), borderSetUpEvent.getCenter().get(1));
        worldBorder.setSize(borderSetUpEvent.getBorderSize());
        worldBorder.setWarningDistance(5);

        TFWLoader.getPlayerManager().filtered_online_players().forEach(playerData -> {
            PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
            ((CraftPlayer) playerData.getPlayer()).getHandle().playerConnection.sendPacket(packet);
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onKitHandler(KitHandleEvent kitHandleEvent){

        //Equip Kits to players!
        Bukkit.getServer().getScheduler().runTaskLater(kitHandleEvent.getJavaPlugin(), ()-> {
            for (PlayerData playerData : kitHandleEvent.getPlayerDataSet()) {
                //Skip condition
                if (!playerData.isOnline() || playerData.getTeam() == null) continue;

                playerData.getTeam().getKit().giveKit(playerData);
            }
        }, 1L);

        //WorldBorder Async Initializer!
        BorderSetUpEvent borderSetUpEvent = new BorderSetUpEvent("world", TFWLoader.getGameManager().getWorldSettings().size, TFWLoader.getGameManager().getWorldSettings().center);
        Bukkit.getServer().getPluginManager().callEvent(borderSetUpEvent);

        //Create Sync task, using async call! - PreparationTask
        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), ()-> new PrepareTask((short) 10).runTaskTimer(TFW.getInstance(), 20L, 20L));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPrepare(PreparePlayersEvent preparePlayersEvent){

        TFWLoader.getGameManager().notification("%prefix% &a&lYou will be teleported in a moment!");

        for (PlayerData playerData : preparePlayersEvent.getPlayerDataSet()) {
            if (playerData.isOnline()){
                playerData.clearPlayer();
                playerData.getPlayer().teleport(playerData.getTeam().getSpawn().toBukkitLocation());
            }
        }

        TFWLoader.getGameManager().loadKits(preparePlayersEvent.getPlayerDataSet());
    }
}
