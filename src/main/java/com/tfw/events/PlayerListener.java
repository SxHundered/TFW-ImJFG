package com.tfw.events;

import com.tfw.configuration.Style;
import com.tfw.events.custom.*;
import com.tfw.game.GameManager;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamManager;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.data.PlayerStatus;
import com.tfw.manager.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Locale;


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

public class PlayerListener implements Listener {


    /**
     * @param tfwJoinEvent Handle player join event!
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void joinHandle(TFWJoinEvent tfwJoinEvent){
        //Adding the player to the list!, and make the modifications
        final Player player = tfwJoinEvent.getPlayer();

        TFWLoader.getPlayerManager().addPlayer(player);

        if (TFWLoader.getArenaManager().getSpawn() != null)
            player.teleport(TFWLoader.getArenaManager().getSpawn().toBukkitLocation());
    }

    /**
     * @param tfwLeaveEvent Handle the quit event when the player leaves the server!
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void quitHandle(TFWLeaveEvent tfwLeaveEvent){

        //Now here we delete the player from the game!
        TFWLoader.getPlayerManager().removePlayer(tfwLeaveEvent.getPlayerData());
    }

    /**
     * @param playerEliminationEvent Handle the player elimination in the game!
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onElimination(PlayerEliminationEvent playerEliminationEvent){

        if (playerEliminationEvent.getPlayerData().getTeam() == null)
            return;

        //Elimination effect!
        final Team killerTeam = TeamManager.getA().equals(playerEliminationEvent.getPlayerData().getTeam()) ? TeamManager.getB() : TeamManager.getA();

        if (killerTeam.getPlayers().size() == 0)
            return;

        killerTeam.eliminationEffect(playerEliminationEvent.getLocation());
        Bukkit.getWorld(playerEliminationEvent.getLocation().getWorld().getName()).playSound(playerEliminationEvent.getLocation(), Sound.FIZZ, 2.0F, 1.0f);
        killerTeam.updateStats();

        //Elimination method!
        TFWLoader.getPlayerManager().eliminatePlayer(playerEliminationEvent.getPlayerData());
    }

    /**
     * @param staffAddEvent Handle the player staff added in a list!
     */
    @EventHandler
    public void onStaffAdd(StaffAddEvent staffAddEvent){

        String target = staffAddEvent.getName();

        final PlayerData looking_for = TFWLoader.getPlayerManager().getPlayerDataList().stream()
                .filter(playerData -> playerData.getPlayerName().equalsIgnoreCase(target)).findAny().orElse(null);

        PlayerData executor = staffAddEvent.getExecutorData();

        if (!GameManager.GameStates.getGameStates().equals(GameManager.GameStates.LOBBY) && !GameManager.GameStates.getGameStates().equals(GameManager.GameStates.INGAME)){
            executor.textPlayer("%prefix% &cStaff can be only added in &aLOBBY&7/&eIN-GAME&c status!");
            return;
        }

        if (looking_for == null){
            //Offline detection
            if (TFWLoader.getPlayerManager().getOffline_name_staff().contains(target)){
                executor.textPlayer("%prefix% &c%player%&e is already a&b staff&9 member!".replace("%player%", target.toUpperCase(Locale.ROOT)));
                return;
            }

            TFWLoader.getPlayerManager().getOffline_name_staff().add(target);
            executor.textPlayer("%prefix% &c%player%&a has been assigned as a&b staff&9 member!".replace("%player%", target.toUpperCase(Locale.ROOT)));

        }else if (looking_for.getPlayerStatus().equals(PlayerStatus.STAFF)){
            final String STAFF_PLAYER = "%prefix% &c&lERROR&7, &e%playerName% &7is on &bStaff-Mode".replace("%playerName%", looking_for.getPlayerName());
            executor.textPlayer(Style.translate(STAFF_PLAYER));
        }else{
            if (looking_for.getPlayerStatus().equals(PlayerStatus.PLAYING)){
                executor.textPlayer("%prefix% &6&l%player% &cis already playing in the game!".replace("%player%", target.toUpperCase(Locale.ROOT)));
                return;
            }

            looking_for.setPlayerStatus(PlayerStatus.STAFF);
            looking_for.staffApply();
            looking_for.textPlayer("%prefix% " + ChatColor.GREEN + "You have been assigned as a staff member!");
        }
    }

    /**
     * @param staffRemoveEvent Handle the player staff removed in a list!
     */
    @EventHandler
    public void onStaffRemove(StaffRemoveEvent staffRemoveEvent){

        String target = staffRemoveEvent.getName();

        final PlayerData looking_for = TFWLoader.getPlayerManager().getPlayerDataList().stream()
                .filter(playerData -> playerData.getPlayerName().equalsIgnoreCase(target)).findAny().orElse(null);

        final PlayerData executor = staffRemoveEvent.getExecutorData();

        if (looking_for == null){
            //Offline detection
            if (!TFWLoader.getPlayerManager().getOffline_name_staff().contains(target)){
                final String NOT_STAFF_PLAYER = "%prefix% &c&lERROR&7, &e%playerName% &7is not on staff-mode!".replace("%playerName%", target.toUpperCase(Locale.ROOT));
                executor.textPlayer(Style.translate(NOT_STAFF_PLAYER));
                return;
            }
            executor.textPlayer("%prefix% &c%player%&e has been reassigned to normal player!".replace("%player%", target.toUpperCase(Locale.ROOT)));
            TFWLoader.getPlayerManager().getOffline_name_staff().remove(target);
        }else if (!looking_for.getPlayerStatus().equals(PlayerStatus.STAFF)){
            final String NOT_STAFF_PLAYER = "%prefix% &c&lERROR&7, &e%playerName% &7is not on staff-mode!".replace("%playerName%", looking_for.getPlayerName());
            executor.textPlayer(Style.translate(NOT_STAFF_PLAYER));
        }else{
            looking_for.getPlayer().kickPlayer(ChatColor.RED + "Please, rejoin!");
        }
    }
}
