package com.tfw.scoreboard;

import com.tfw.configuration.Style;
import com.tfw.main.TFWLoader;
import com.tfw.manager.team.Team;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class AsyncBoard {

    @Getter private final static List<PlayerBoard> boardArrayList = new ArrayList<>();

    /**
     * A = (NORMAL), B= (STAFF), C0= (TEAM A) , C1= (TEAM B)
     */
    @Getter
    private static final String[] args = {"A", "B", "C0", "C1"};

    public static void createTeams_Instance(Player player, PlayerBoard fastBoard, int weight){

        final Scoreboard scoreboard = fastBoard.getScoreboard();

        scoreboard.registerNewTeam(fastBoard.getPlayerData().getTeam() != null ? fastBoard.getPlayerData().getTeam().getIdentifier() :
                fastBoard.getPlayerData().getDefaultTeam());

        generateTeam(player, fastBoard.getPlayerData().getTeam() != null ? fastBoard.getPlayerData().getTeam().getIdentifier() :
                fastBoard.getPlayerData().getDefaultTeam(), fastBoard.getPlayerData().getTeam() != null ?
                fastBoard.getPlayerData().getTeam().getColorTeam().toString() : ChatColor.GRAY.toString(), fastBoard);
    }

    private static void generateTeam(Player player, String name, String prefix, PlayerBoard fastBoard){
        org.bukkit.scoreboard.Team team = fastBoard.getScoreboard().getTeam(name) == null ?
                (fastBoard.getScoreboard().registerNewTeam(name)) :
                (fastBoard.getScoreboard().getTeam(name));
        if (!team.hasEntry(player.getName())) {
            team.addEntry(player.getName());
            team.setPrefix(prefix);
        }
    }

    public static void generate_ifExists(PlayerBoard fastBoard) {
        TFWLoader.getPlayerManager().filtered_online_players().forEach(playerData -> {
            if (playerData != null) {
                Team teamName = playerData.getTeam();
                if (teamName == null) {
                    if (fastBoard.getScoreboard().getTeam(playerData.getDefaultTeam()) == null)
                        fastBoard.getScoreboard().registerNewTeam(playerData.getDefaultTeam());
                    else
                        if (!fastBoard.getScoreboard().getTeam(playerData.getDefaultTeam()).hasEntry(playerData.getPlayerName()))
                            fastBoard.getScoreboard().getTeam(playerData.getDefaultTeam()).addEntry(playerData.getPlayerName());
                    fastBoard.getScoreboard().getTeam(playerData.getDefaultTeam()).setPrefix(ChatColor.GRAY.toString());

                } else {

                    if (fastBoard.getScoreboard().getTeam(teamName.getIdentifier()) == null)
                        fastBoard.getScoreboard().registerNewTeam(teamName.getIdentifier());
                    else if (!fastBoard.getScoreboard().getTeam(teamName.getIdentifier()).hasEntry(playerData.getPlayerName()))
                        fastBoard.getScoreboard().getTeam(teamName.getIdentifier()).addEntry(playerData.getPlayerName());
                    fastBoard.getScoreboard().getTeam(teamName.getIdentifier()).setPrefix(playerData.getTeam().getColorTeam().toString());
                }
            }
        });
    }

    public static void updateTitle(){
        for (PlayerBoard playerBoard : getBoardArrayList())
            if (playerBoard.getIScoreboard() != null && !playerBoard.getIScoreboard().isAnimated())
                playerBoard.updateTitle();
    }

    public static void updateBoard(PlayerBoard scoreBoard){
        List<String> replaceHolders = Style.translateLines_Holders(scoreBoard.getPlayerData().getPlayer(), scoreBoard.getIScoreboard().lines());

        scoreBoard.updateTitle();
        scoreBoard.updateLines(replaceHolders);
    }

}
