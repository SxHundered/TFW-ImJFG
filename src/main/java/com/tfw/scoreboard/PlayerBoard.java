package com.tfw.scoreboard;

import com.tfw.configuration.Style;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import com.tfw.scoreboard.boards.FastBoard;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;


@Getter
public class PlayerBoard extends FastBoard {

    private final PlayerData playerData;
    @Setter
    private IScoreboard iScoreboard;
    private final Scoreboard scoreboard;

    private final String title = ChatColor.RESET.toString();

    private final boolean deleted = false;

    public PlayerBoard(Player player, PlayerData playerData) {
        super(player);

        try {
            this.iScoreboard = TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.LOBBY);
            System.out.println(iScoreboard);
        } catch (IScoreboardException e) {
            e.printStackTrace();
        }

        this.playerData = playerData;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    public void delete(String name) {
        if (getScoreboard().getTeam(name) != null)
            getScoreboard().getTeam(name).unregister();
    }

    public void updateTitle() {
        if (getIScoreboard().isAnimated())
            updateTitle(getIScoreboard().animatedText(getPlayer()));
        else
            updateTitle(getIScoreboard().getStaticTitle() == null ? "NO STATIC TITLE" : Style.translateLine_Holders(getPlayer(), getIScoreboard().getStaticTitle()));
    }
}
