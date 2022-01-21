package com.tfw.placeholders;

import com.tfw.configuration.Style;
import com.tfw.game.arena.ArenaManager;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.Team;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

        final PlayerData playerData = TFWLoader.getPlayerManager().data(p.getName());

        if(playerData == null)
            return "";

        if (params.equalsIgnoreCase("online"))
            return "" + Bukkit.getOnlinePlayers().size();
        else if (params.equalsIgnoreCase("status"))
            return playerData.getPlayerStatus().getCurrentStatus();
        else if (params.equalsIgnoreCase("time"))
            return TFWLoader.getGameManager().currentTime();

        Team team = playerData.getTeam();

        if(team == null)
            return "";

        if (params.equalsIgnoreCase("team_name"))
            return team.getTeam();
        else if (params.equalsIgnoreCase("team_alive"))
            return "" + team.currentAlive();
        else if (params.equalsIgnoreCase("team_kills"))
            return "" + team.getStats().getKills();
        else if (params.equalsIgnoreCase("team_heart"))
            return team.getTeam() + "'s" + Style.RED + Style.BOLD + "HEART";
        else if (params.equalsIgnoreCase("team_heart_status"))
            return (team.getHeart().isDestroyed() ? Style.RED + "\u274C" : Style.GREEN + "\u2713");
        else if (params.equalsIgnoreCase("arena_name"))
            return ArenaManager.getArena().getName();
        else if (params.equalsIgnoreCase("arena_players"))
            return "" + ArenaManager.getArena().arena_Players();

        return "";
    }
}
