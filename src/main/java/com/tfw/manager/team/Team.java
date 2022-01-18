package com.tfw.manager.team;

import com.tfw.configuration.Style;
import com.tfw.events.custom.TeamEliminationEvent;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.data.PlayerStatus;
import com.tfw.manager.team.heart.Heart;
import com.tfw.utils.CustomLocation;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *
 *     TEAM DATA DETAILS, functions, constructed when enabling the server!
 */

@RequiredArgsConstructor@Getter@Setter
public class Team implements ITeam{

    //TODO: UPDATE STATS TEAM METHODS! F4RES

    @Getter
    private final List<PlayerData> players = new ArrayList<>();

    private Heart heart;

    @Getter
    private final String identifier;
    private final String name;
    private final String chat_format;
    private final String color;
    private String teamOrder;
    private TeamStats stats = TeamStats.STATS;

    /**
     * @return Team name
     */
    @Override
    public String getTeam() {
        return name;
    }

    /**
     * @return Alive players in current team as a list!
     */
    @Override
    public List<PlayerData> alive_members() {
        return players.stream().filter(Objects::nonNull)
                .filter(playerData -> playerData.getPlayerStatus().equals(PlayerStatus.PLAYING))
                .collect(Collectors.toList());
    }

    /**
     * @return current online members
     */
    @Override
    public List<PlayerData> getMembers() {
        return players.stream().filter(Objects::nonNull).filter(PlayerData::isOnline).collect(Collectors.toList());
    }

    /**
     * @param target Is target already added in the Team list?
     * @return True is in the list false is not!
     */
    @Override
    public boolean isInSameTeam(@NonNull PlayerData target) {
        return players.contains(target);
    }

    /**
     * @param message Message wants to be sent to the team!
     */
    @Override
    public void broadcastTeam(String message) {
        for (PlayerData player : players)
            player.textPlayer(message);
    }

    /**
     * @return How many alive players left in your team
     */
    @Override
    public int currentAlive() {
        return (int) players.stream().filter(Objects::nonNull).filter(playerData -> playerData.getPlayerStatus().equals(PlayerStatus.PLAYING)).count();
    }

    /**
     * @return How many players are joined in your team!
     */
    @Override
    public int totalTeamMembers() {
        return players.size();
    }

    /**
     * @param target Add the target to team list
     * @return returns whether the player has been added or not!
     */
    @Override
    public boolean addTeamPlayer(PlayerData target) {
        //TODO
        if (players.contains(target))
            return false;

        players.add(target);
        broadcastTeam(getColorTeam().toString() + target.getPlayerName() + ChatColor.YELLOW + " has joined your team!");
        return true;
    }

    /**
     * Removes a team player from the game!
     * Play sound effects, for all players
     * If playing and the team is empty it means the game has ended!
     *
     * @param target Removes the target player from the list given!
     */
    @Override
    public void removeTeamPlayer(@NonNull PlayerData target, String reason){
        players.remove(target);
        target.setTeam(null);

        if (target.getPlayerStatus().equals(PlayerStatus.PLAYING)) {
            switch (reason.toLowerCase(Locale.ROOT)) {
                case "leave":
                    broadcastTeam(getColorTeam().toString() + target.getPlayerName() + ChatColor.RED + " has left your team!");
                    break;
                case "killed":
                    broadcastTeam(getColorTeam().toString() + target.getPlayerName() + ChatColor.RED + " has been killed!");
                    break;
            }
            TFWLoader.getGameManager().playSound();

            //Checks if the game is started!
            //here we check whether the team has no members left to end the game!
            if (currentAlive() == 0){
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TFW.getInstance(), ()-> {
                    TeamEliminationEvent teamEliminationEvent = new TeamEliminationEvent(this);
                    Bukkit.getServer().getPluginManager().callEvent(teamEliminationEvent);
                });
            }
        }

        //LOBBY STATE NO CHANGES REQUIRED!
    }

    /**
     * @return returns the chatcolor enum!
     */
    @Override
    public ChatColor getColorTeam() {
        return ChatColor.valueOf(color);
    }

    /**
     * @return returns chat formatted of the player!, including the prefix of the team
     */
    @Override
    public String chat_Format() {
        return Style.translate(chat_format);
    }

    public void updateStats(){
        stats.setKills(stats.kills + 1);
    }

    @Override
    public Heart getHeart() {
        return heart;
    }

    @Override
    public void generateHeart(EntityType entityType, CustomLocation location) {
        heart = new Heart(this, entityType, location);

        heart.setWorld(Bukkit.getWorld(location.getWorld()));
        heart.spawnEffect();
        heart.spawnHeart();
        broadcastTeam(TFW.getPrefix() + ChatColor.GREEN.toString() + "YOUR HEART HAS BEEN SPAWNED!\n  §e§lPROTECT YOUR HEART,§c§l AND DESTROY THEIR HEART TO WIN THE GAME!");
    }
}
