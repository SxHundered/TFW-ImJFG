package com.tfw.bukkit.commands.sub;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.configuration.Style;
import com.tfw.events.custom.TeamJoinEvent;
import com.tfw.events.custom.TeamLeaveEvent;
import com.tfw.game.GameManager;
import com.tfw.game.arena.ArenaManager;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamExceptions;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.data.PlayerStatus;
import com.tfw.manager.team.Team;
import com.tfw.utils.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TeamCommand extends CommandBase<TFW> {


    final String OFFLINE_PLAYER = "%prefix% %playerName% &e&lis currently &4&lOFFLINE";
    final String STAFF_PLAYER = "%prefix% &c&lERROR&7, &e%playerName% &7is on &bStaff-Mode";
    final String ALREADY_IN_TEAM = "%prefix% %player_name% is already in team, use /TFW team removeplayer <playername>.";
    final String ALREADY_NOT_IN_TEAM = "%prefix% %player_name% is not in team, use /TFW team addplayer <playername> <teamname>.";
    final String TEAM_NOT_FOUND = "%prefix% Could not find %team_name% team!";
    final String PLAYER_ADDED_TO_TEAM = "%prefix% Sucessfully added %player_name% to %team_name%";
    final String YOU_HAVE_BEEN_ADDED = "%prefix% You've been added to %team_name% by %operator%";
    final String REMOVED_FROM_TEAM = "%prefix% Sucessfully removed %player_name% from %team_name%";
    final String YOU_HAVE_BEEN_REMOVED = "%prefix% You've been removed from %team_name% by %operator%";
    final String TEAM_COLOR = "%prefix% %team_name%'s color is %team_color%";
    final String TEAM_SCORE = "%prefix% %team_name%'s kills is %team_score%";
    final String TEAM_SPAWN = "%prefix% %team_name%'s spawn location saved!";
    final String TEAM_HEART = "%prefix% %team_name%'s heart location saved!";
    final String CONFIGURATION_SAVED = "%prefix% Team's locations have been updated successfully!";

    public TeamCommand(TFW plugin, String check) {
        super(plugin, check);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        if (args.length == 0) {
            for (String s : CHECK_USAGE)
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
                            STAFF_PLAYER.replace("%playerName%", args[1])));
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
                            for (String s : CHECK_USAGE)
                                sender.sendMessage(Style.translate(s));
                    }
                } else {
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
                } catch (TeamExceptions ignore) {
                }
                break;
        }
        return true;
    }

    final List<String> CHECK_USAGE = Arrays.asList(

            "&7/tfw&e team&b addPlayer&6 <playerName>&c <team_name>",
            "&7/tfw&e team&b removePlayer&6 <playerName>",

            "&7/tfw&e team&b spawn&6 <team_name>",
            "&7/tfw&e team&b heart&6 <team_name>",
            "&7/tfw&e team&b color&6 <team_name>",
            "&7/tfw&e team&b members&6 <team_name>",
            "&7/tfw&e team&b score&6 <team_name>",
            "&7/tfw&e team&b update"
    );
}