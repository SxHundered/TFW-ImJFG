package com.tfw.bukkit.commands.sub;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.configuration.Style;
import com.tfw.events.custom.TeamJoinEvent;
import com.tfw.events.custom.TeamLeaveEvent;
import com.tfw.game.arena.ArenaManager;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamExceptions;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.data.PlayerStatus;
import com.tfw.manager.messages.Messages;
import com.tfw.manager.team.Team;
import com.tfw.utils.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

public class TeamCommand extends CommandBase<TFW> {

    final String OFFLINE_PLAYER = Messages.OFFLINE_PLAYER.toString();
    final String STAFF_PLAYER = Messages.STAFF_PLAYER.toString();
    final String ALREADY_IN_TEAM = Messages.ALREADY_IN_TEAM.toString();
    final String ALREADY_NOT_IN_TEAM = Messages.ALREADY_NOT_IN_TEAM.toString();
    final String TEAM_NOT_FOUND = Messages.TEAM_NOT_FOUND.toString();
    final String PLAYER_ADDED_TO_TEAM = Messages.PLAYER_ADDED_TO_TEAM.toString();
    final String YOU_HAVE_BEEN_ADDED = Messages.YOU_HAVE_BEEN_ADDED.toString();
    final String REMOVED_FROM_TEAM = Messages.REMOVED_FROM_TEAM.toString();
    final String YOU_HAVE_BEEN_REMOVED = Messages.YOU_HAVE_BEEN_REMOVED.toString();
    final String TEAM_COLOR = Messages.TEAM_COLOR.toString();
    final String TEAM_SCORE = Messages.TEAM_SCORE.toString();
    final String TEAM_SPAWN = Messages.TEAM_SPAWN.toString();
    final String TEAM_HEART = Messages.TEAM_HEART.toString();
    final String CONFIGURATION_SAVED = Messages.CONFIGURATION_SAVED.toString();

    public TeamCommand(TFW plugin, String check) {
        super(plugin, check);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        if (args.length == 0) {
            for (String s : TEAM_USAGE)
                sender.sendMessage(Style.translate(s));
            return true;
        }

        String teamName = null;
        Team team = null;
        CustomLocation customLocation = null;
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "addplayer":
            case "removeplayer":
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Style.translate(
                            OFFLINE_PLAYER.replace("%player_name%", args[1])));
                    return true;
                }
                PlayerData playerData = TFWLoader.getPlayerManager().data(target.getUniqueId());
                if (playerData == null) {
                    sender.sendMessage(Style.translate(
                            OFFLINE_PLAYER.replace("%player_name%", args[1])));
                    return true;
                }else if (playerData.getPlayerStatus().equals(PlayerStatus.STAFF)){
                    sender.sendMessage(Style.translate(
                            STAFF_PLAYER.replace("%player_name%", args[1])));
                    return true;
                }
                if (args[0].toLowerCase(Locale.ROOT).equalsIgnoreCase("addplayer")) {
                    team = playerData.getTeam();
                    if (team != null) {
                        sender.sendMessage(Style.translate(ALREADY_IN_TEAM.replace("%player_name%", args[1])));
                        return true;
                    } else {
                        if (args.length == 3) {
                            teamName = args[2];
                            Team team1 = TFWLoader.getTeamManager().findTeam(teamName);
                            if (team1 == null) {
                                sender.sendMessage(Style.translate(TEAM_NOT_FOUND.replace("%team_name%", args[2])));
                                return true;
                            } else {
                                TeamJoinEvent teamJoinEvent = new TeamJoinEvent(playerData, team1);
                                Bukkit.getServer().getPluginManager().callEvent(teamJoinEvent);

                                sender.sendMessage(Style.translate(PLAYER_ADDED_TO_TEAM.replace("%team_name%", args[2]).replace("%player_name%", args[1])));
                                playerData.textPlayer(Style.translate(YOU_HAVE_BEEN_ADDED.replace("%team_name%", args[2]).replace("%operator%", sender.getName())));
                            }
                        } else
                            for (String s : TEAM_USAGE)
                                sender.sendMessage(Style.translate(s));
                    }
                }else {
                    team = playerData.getTeam();
                    if (team == null) {
                        sender.sendMessage(Style.translate(ALREADY_NOT_IN_TEAM.replace("%player_name%", args[1])));
                        return true;
                    } else {
                        sender.sendMessage(Style.translate(REMOVED_FROM_TEAM.replace("%player_name%", args[1]).replace("%team_name%", args[2])));
                        playerData.textPlayer(Style.translate(YOU_HAVE_BEEN_REMOVED.replace("%team_name%", args[2]).replace("%operator%", sender.getName())));

                        TeamLeaveEvent teamLeaveEvent = new TeamLeaveEvent(playerData, "leave");
                        Bukkit.getServer().getPluginManager().callEvent(teamLeaveEvent);
                    }
                }
                break;

            case "color":
            case "score":
            case "members":
            case "spawn":
            case "heart":
                teamName = args[1];
                team = TFWLoader.getTeamManager().findTeam(teamName);
                if (team == null) {
                    sender.sendMessage(Style.translate(TEAM_NOT_FOUND.replace("%team_name%", args[2])));
                    return true;
                } else {
                    switch (args[0].toLowerCase(Locale.ROOT)) {
                        case "color":
                            sender.sendMessage(
                                    Style.translate(
                                            TEAM_COLOR.replace("%team_name%", args[0]).replace("%team_color%", team.getColor())));
                            break;
                        case "members":
                            sender.sendMessage(Style.translate(team.getMembersAsString().toLegacyText()));
                            break;
                        case "score":
                            sender.sendMessage(Style.translate(TEAM_SCORE.replace("%team_name%", args[1]).replace("%team_score%", "" +
                                    team.getKills())));
                            break;
                        case "spawn":
                            customLocation = CustomLocation.fromBukkitLocation(((Player) sender).getLocation());
                            team.setSpawn(customLocation);
                            sender.sendMessage(Style.translate(TEAM_SPAWN.replace("%team_name%", args[1])));
                            break;
                        case "heart":
                            customLocation = CustomLocation.fromBukkitLocation(((Player) sender).getLocation());
                            team.getHeart().setLocation(customLocation);
                            sender.sendMessage(Style.translate(TEAM_HEART.replace("%team_name%", args[1])));
                            break;
                    }
                }
                break;
            case "update":
                try {
                    TFWLoader.getTeamManager().updateConfig(ArenaManager.getArenasConfig());
                    sender.sendMessage(Style.translate(CONFIGURATION_SAVED));
                } catch (TeamExceptions ignore) {}
                break;
        }
        return true;
    }

    final List<String> TEAM_USAGE = Messages.TEAM_HELP.toArrayList();
}