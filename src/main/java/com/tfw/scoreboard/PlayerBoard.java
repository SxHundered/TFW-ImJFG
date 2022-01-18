package com.tfw.scoreboard;

import com.tfw.manager.data.PlayerData;
import com.tfw.scoreboard.boards.FastBoard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;


@Getter
public class PlayerBoard extends FastBoard {

    private final PlayerData playerData;
    private IScoreboard iScoreboard;
    private final Scoreboard scoreboard;

    private final String title = ChatColor.RESET.toString();

    private final boolean deleted = false;

    public PlayerBoard(Player player, IScoreboard iScoreboard, PlayerData playerData) {
        super(player);
        this.iScoreboard = iScoreboard;
        this.playerData = playerData;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        initialBoard();
    }

    public PlayerBoard(Player player, PlayerData playerData) {
        super(player);
        this.playerData = playerData;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        initialBoard();
    }

    private void initialBoard(){
        playerData.getPlayer().setScoreboard(scoreboard);
    }

    public void delete(String name) {
        if (getScoreboard().getTeam(name) != null)
            getScoreboard().getTeam(name).unregister();
    }

    public void updateTitle() {
        if (getIScoreboard().isAnimated())
            updateTitle(getIScoreboard().animatedText());
        else
            updateTitle(getIScoreboard().getStaticTitle());
    }
}
