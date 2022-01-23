package com.tfw.placeholders;

import com.tfw.configuration.Style;
import com.tfw.game.GameManager;
import com.tfw.game.arena.ArenaManager;
import com.tfw.game.arena.iarena.Arena;
import com.tfw.game.task.PrepareTask;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamManager;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.Team;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class TFWPlaceHolder extends PlaceholderExpansion {


    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "tfw";
    }

    @Override
    public @NotNull String getAuthor() {
        return "AbdulAzizCr";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer p, @NotNull String params) {
        return super.onRequest(p, params);
    }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String params) {
        if (p != null)
            return onRequests(p, params);
        return "";
    }

    private String onRequests(Player p, String params) {
        //All placeholders place here and translated!

        PlayerData playerData = TFWLoader.getPlayerManager().data(p.getName());

        if (params.equalsIgnoreCase("online"))
            return "" + Bukkit.getOnlinePlayers().size();
        else if (params.equalsIgnoreCase("status"))
            return GameManager.GameStates.getGameStates().name().toUpperCase(Locale.ROOT);
        else if (params.equalsIgnoreCase("time"))
            return TFWLoader.getGameManager().currentTime();
        else if (params.equalsIgnoreCase("startingtime"))
            return "" + PrepareTask.countdown;

        if (playerData != null) {
            Team team = playerData.getTeam();
            if (team != null) {
                if (params.equalsIgnoreCase("team_name"))
                    return team.getIdentifier();
                else if (params.equalsIgnoreCase("team_alive"))
                    return "" + team.currentAlive();
                else if (params.equalsIgnoreCase("team_kills"))
                    return "" + team.getKills();
                else if (params.equalsIgnoreCase("team_heart"))
                    return team.getTeam() + "'s" + Style.RED + Style.BOLD + "HEART";
                else if (params.equalsIgnoreCase("team_heart_status"))
                    return (team.getHeart().isDestroyed() ? Style.RED + "\u274C" : Style.GREEN + "\u2713");
            } else {
                if (params.equalsIgnoreCase("team_name"))
                    return "&c&lNONE";
            }
        }

        Arena arena = ArenaManager.getArena();
        if (arena != null) {
            if (params.equalsIgnoreCase("arena_name"))
                return arena.getName();
            else if (params.equalsIgnoreCase("arena_players"))
                return "" + arena.arena_Players();
            else if (params.equalsIgnoreCase("winner_team"))
                return TeamManager.getWinners() != null ? TeamManager.getWinners().getColorTeam().toString() + TeamManager.getWinners().getIdentifier() : ChatColor.RED + "No one";
            else if (params.equalsIgnoreCase("winner_team_kills"))
                return TeamManager.getWinners() != null ? "" + TeamManager.getWinners().getKills() : "" + 0;
        }
        return "";
    }
}