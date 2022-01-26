package com.tfw.bukkit.commands.sub.debug;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import com.tfw.scoreboard.IScoreboardException;
import com.tfw.scoreboard.IScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ScoreboardCommand extends CommandBase<TFW> {

    final String CHECK_USAGE = "%prefix% &7/&dTFW&e iscore &c<scoreboard> &8| &7Display Scoreboard!";
    final String OFFLINE_PLAYER = "%prefix% %playerName% is currently OFFLINE";
    final String ERROR = "%prefix% &cThe requested scoreboard has an invalid setting!";
    final String SUCCESSFULLY = "%prefix% &aYou've sucessfully got the scoreboard!";

    public ScoreboardCommand(TFW plugin, String check) {
        super(plugin, check);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {

        if (args.length != 2) {
            sender.sendMessage(Style.translate(CHECK_USAGE));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null){
            sender.sendMessage(Style.translate(
                    OFFLINE_PLAYER.replace("%playerName%", args[0])));
            return true;
        }

        PlayerData playerData = TFWLoader.getPlayerManager().data(target.getUniqueId());
        if (playerData == null) {
            sender.sendMessage(Style.translate(
                    OFFLINE_PLAYER.replace("%playerName%", args[0])));
            return true;
        }

        String SBName = args[1];

        try {
            switch (SBName.toLowerCase(Locale.ROOT)) {
                case "reload":
                    //TODO: RELOADING ALL SCOREBOARD FROM CONFIG AGAIN!
                    break;
                case "staff":
                    playerData.getFastBoard().setIScoreboard(TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.STAFF));
                    sender.sendMessage(Style.translate(SUCCESSFULLY));
                    break;
                case "grace":
                    playerData.getFastBoard().setIScoreboard(TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.GRACE));
                    sender.sendMessage(Style.translate(SUCCESSFULLY));
                    break;
                case "ingame":
                    playerData.getFastBoard().setIScoreboard(TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.INGAME));
                    sender.sendMessage(Style.translate(SUCCESSFULLY));
                    break;
                case "lobby":
                    playerData.getFastBoard().setIScoreboard(TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.LOBBY));
                    sender.sendMessage(Style.translate(SUCCESSFULLY));
                    break;
            }
        } catch (IScoreboardException iScoreboardException){
            sender.sendMessage(Style.translate(ERROR));
        }

        return true;
    }

}
