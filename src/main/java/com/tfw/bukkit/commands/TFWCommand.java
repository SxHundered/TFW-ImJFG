package com.tfw.bukkit.commands;

import com.tfw.bukkit.commands.sub.CheckCommand;
import com.tfw.bukkit.commands.sub.GameCommand;
import com.tfw.bukkit.commands.sub.HelpCommand;
import com.tfw.bukkit.commands.sub.TeamCommand;
import com.tfw.bukkit.commands.sub.debug.ScoreboardCommand;
import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class TFWCommand {

    public static void init(TFW instance){

        // Anonymous implementation of "/check" root command.
        CommandBase<TFW> checkCommand = new CommandBase<TFW>(TFW.getInstance()) {
            @Override
            public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
                HELP_OP.forEach(s -> sender.sendMessage(Style.translate(s)));
                return false;
            }
        };

        Arrays.asList(new HelpCommand(instance, "help"), new CheckCommand(instance, "check")
                , new TeamCommand(instance, "team")
                , new GameCommand(instance, "game"), new ScoreboardCommand(instance, "iscore")).forEach(tfwCommandBase ->
                checkCommand.registerSubCommand(tfwCommandBase.getName(), tfwCommandBase));

        instance.getCommand("TFW").setExecutor(checkCommand);
    }
     @Getter
    static List<String> HELP_OP = Arrays.asList(
            "%prefix% &5Commands",
            "",
            "&7/tfw &echeck &b<playername>",
            "&7/tfw &ehelp",
             "&7Authors: &aAbdulAzizCr &7& &eF4res",
             "&eSource Code:&a&l https://github.com/SxHundered/TFW-ImJFG");
}