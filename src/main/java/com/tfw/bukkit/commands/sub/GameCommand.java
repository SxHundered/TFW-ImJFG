package com.tfw.bukkit.commands.sub;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.bukkit.commands.TFWCommand;
import com.tfw.configuration.Style;
import com.tfw.events.custom.PreparePlayersEvent;
import com.tfw.game.GameManager;
import com.tfw.game.arena.ArenaManager;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.TeamManager;
import com.tfw.manager.data.PlayerData;
import com.tfw.utils.CustomLocation;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class GameCommand extends CommandBase<TFW> {

    final String NOT_ENOUGH_TEAM_MEMBERS = "%prefix% There is not enough players!.";
    final String ALREADY_STARTED = "%prefix% the game is already started!";

    public GameCommand(TFW plugin, String help) {
        super(plugin, help);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        if (args.length == 0) {
            for (String s : CHECK_USAGE)
                sender.sendMessage(Style.translate(s));
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "spawn":
                TFWLoader.getArenaManager().setSpawn(CustomLocation.fromBukkitLocation(((Player) sender).getLocation()));
                break;
            case "game-info":
                sender.sendMessage(TFWLoader.getGameManager().game_info());
                break;
            case "start":
                //Starts the game!
                if (!GameManager.GameStates.getGameStates().equals(GameManager.GameStates.LOBBY)){
                    sender.sendMessage(Style.translate(ALREADY_STARTED));
                    return true;
                }

                //Checks if there is enough players in each team!
                if (TeamManager.getA().getPlayers().size() == 0 || TeamManager.getB().getPlayers().size() == 0){
                    sender.sendMessage(Style.translate(NOT_ENOUGH_TEAM_MEMBERS));
                    return true;
                }

                //Initialize the game
                Set<PlayerData> TEAMS = TFWLoader.getPlayerManager().onlyTeamPlayers();
                PreparePlayersEvent preparePlayersEvent = new PreparePlayersEvent(TEAMS);
                Bukkit.getServer().getPluginManager().callEvent(preparePlayersEvent);
                break;
        }

        return true;
    }

    final List<String> CHECK_USAGE = Arrays.asList(
            "/tfw game spawn | Updates spawn location!",
            "/tfw game summary | Displays some details related to team and the game state!",
            "/tfw game start | Starts the game"
    );
}