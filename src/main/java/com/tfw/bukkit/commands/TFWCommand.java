package com.tfw.bukkit.commands;

import com.tfw.bukkit.commands.sub.*;
import com.tfw.bukkit.commands.sub.debug.ScoreboardCommand;
import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class TFWCommand {

    public static void init(TFW instance) {

        // Anonymous implementation of "/tfw" root command.
        CommandBase<TFW> checkCommand = new CommandBase<TFW>(TFW.getInstance()) {
            @Override
            public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
                HELP_OP.forEach(s -> sender.sendMessage(Style.translate(s)));
                return false;
            }
        };


        Arrays.asList(new HelpCommand(instance, "help"), new CheckCommand(instance, "check")
                , new TeamCommand(instance, "team")
                , new GameCommand(instance, "game"),
                new SettingsCommand(instance, "settings"),
                new StaffCommand(instance, "staff"), new ScoreboardCommand(instance, "iscore")).forEach(tfwCommandBase ->
                checkCommand.registerSubCommand(tfwCommandBase.getName(), tfwCommandBase));

        instance.getCommand("TFW").setExecutor(checkCommand);
    }

    @Getter
    static List<String> HELP_OP = Arrays.asList(
            "             &6&lTFW &5&lCOMMANDS",
            "&7/tfw&e help",
            "&7/tfw&e game",
            "&7/tfw&e settings",
            "&7/tfw&e team",
            "&7/tfw&e staff",
            "&7/tfw&e check &b<playername>",
            "",
            "&7Authors: &aAbdulAzizCr &8- &dF4res",
            "&eSource Code:&a&l https://github.com/SxHundered/TFW-ImJFG",
            "");
}