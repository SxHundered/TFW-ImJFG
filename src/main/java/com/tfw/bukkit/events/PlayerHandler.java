package com.tfw.bukkit.events;

import com.tfw.configuration.Style;
import com.tfw.events.custom.PlayerEliminationEvent;
import com.tfw.events.custom.TFWJoinEvent;
import com.tfw.events.custom.TFWLeaveEvent;
import com.tfw.game.GameManager;
import com.tfw.main.TFWLoader;
import com.tfw.manager.PlayerManager;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.data.PlayerStatus;
import com.tfw.manager.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerHandler implements Listener {


    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onAsyncLogin(PlayerLoginEvent playerLoginEvent) {
        switch (GameManager.GameStates.getGameStates()) {
            case COUNTDOWN:
            case RESTART:
            case ENDING:
                playerLoginEvent.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Sorry, you can not join at this moment!");
                break;
            case INGAME:
                if (!TFWLoader.getPlayerManager().getOffline_name_staff().contains(playerLoginEvent.getPlayer().getName()))
                    playerLoginEvent.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "ONLY STAFF MEMBERS CAN JOIN AT THIS STATE!");
                break;
            default:
                break;
        }
    }


    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent playerJoinEvent) {
        playerJoinEvent.setJoinMessage(null);

        TFWJoinEvent tfwJoinEvent = new TFWJoinEvent(playerJoinEvent.getPlayer());
        Bukkit.getServer().getPluginManager().callEvent(tfwJoinEvent);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent playerQuitEvent) {
        playerQuitEvent.setQuitMessage(null);
        PlayerData playerData = TFWLoader.getPlayerManager().data(playerQuitEvent.getPlayer().getUniqueId());

        assert playerData != null;

        TFWLeaveEvent tfwLeaveEvent = new TFWLeaveEvent(playerData);
        Bukkit.getServer().getPluginManager().callEvent(tfwLeaveEvent);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onKill(PlayerDeathEvent playerDeathEvent) {

        playerDeathEvent.setDeathMessage(null);

        playerDeathEvent.setDroppedExp(0);
        playerDeathEvent.getDrops().clear();


        PlayerData playerData = TFWLoader.getPlayerManager().data(playerDeathEvent.getEntity().getUniqueId());

        if (playerData == null)
            return;

        if (playerData.getPlayerStatus().equals(PlayerStatus.DEAD))
            return;

        PlayerEliminationEvent playerEliminationEvent = new PlayerEliminationEvent(playerData, playerDeathEvent.getEntity().getLocation());
        Bukkit.getServer().getPluginManager().callEvent(playerEliminationEvent);

    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = TFWLoader.getPlayerManager().data(player.getUniqueId());
        if (playerData == null) return;
        event.setCancelled(true);

        final Team team = playerData.getTeam();
        switch (playerData.getPlayerStatus()) {
            case LOBBY:
            case PLAYING:
                for (Player players : Bukkit.getOnlinePlayers())
                    players.sendMessage(Style.translate(
                            (team != null ? team.getColorTeam() + team.getIdentifier() + " &7\u258f " + team.getColorTeam()
                                    : "&c&l✘ &7\u258f " + ChatColor.GRAY) + player.getName() + " &7» ") + Style.RESET + event.getMessage());
                break;
            case STAFF: {
                for (Player players : Bukkit.getOnlinePlayers())
                    players.sendMessage(Style.translate("&c&lSTAFF &7\u258f &c" + player.getName() + " &7» ") + Style.RESET + event.getMessage());
            }
            break;
        }
    }
}