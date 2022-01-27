package com.tfw.events;

import com.tfw.events.custom.BorderSetUpEvent;
import com.tfw.events.custom.KitHandleEvent;
import com.tfw.events.custom.PreparePlayersEvent;
import com.tfw.game.GameManager;
import com.tfw.game.task.PrepareTask;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.messages.Messages;
import com.tfw.scoreboard.IScoreboardException;
import com.tfw.scoreboard.IScoreboardManager;
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

    final String STARTINGTITLE = Messages.STARTINGTITLE.toString();
    final String STARTINGSUBTITLE = Messages.STARTINGSUBTITLE.toString();
    final String STARTINGMESSAGE = Messages.STARTINGMESSAGE.toString();

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

        //Load kits
        kitHandleEvent.loadKits();

        //WorldBorder Async Initializer!
        BorderSetUpEvent borderSetUpEvent = new BorderSetUpEvent("world", TFWLoader.getGameManager().getWorldSettings().size, TFWLoader.getGameManager().getWorldSettings().center);
        Bukkit.getServer().getPluginManager().callEvent(borderSetUpEvent);

        //Create Sync task, using async call! - PreparationTask
        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), ()-> new PrepareTask((short) 10).runTaskTimer(TFW.getInstance(), 20L, 20L));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPrepare(PreparePlayersEvent preparePlayersEvent){

        GameManager.GameStates.setGameStates(GameManager.GameStates.COUNTDOWN);

        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), ()-> {
            for (PlayerData playerData : TFWLoader.getPlayerManager().filtered_online_players()) {
                if (playerData.isOnline())
                    try {
                        playerData.getFastBoard().setIScoreboard(
                                TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.GRACE));
                    } catch (IScoreboardException e) {
                        e.printStackTrace();
                    }
            }
        });

        TFWLoader.getGameManager().title_Notification(STARTINGTITLE, STARTINGSUBTITLE);
        TFWLoader.getGameManager().notification(STARTINGMESSAGE);


        //Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), ()-> TFWLoader.getGameManager().modifySkins());
        preparePlayersEvent.startTeleportation();

        TFWLoader.getGameManager().loadKits(preparePlayersEvent.getPlayerDataSet());
    }
}
