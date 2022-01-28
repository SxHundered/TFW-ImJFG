package com.tfw.bukkit.commands;

import com.tfw.bukkit.commands.sub.*;
import com.tfw.bukkit.commands.sub.debug.ScoreboardCommand;
import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import com.tfw.manager.messages.Messages;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
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
                , new GameCommand(instance, "game")
                , new StaffCommand(instance, "staff")
                , new SettingsCommand(instance, "settings")
                , new ScoreboardCommand(instance, "iscore")).forEach(tfwCommandBase ->
                checkCommand.registerSubCommand(tfwCommandBase.getName(), tfwCommandBase));

        instance.getCommand("TFW").setExecutor(checkCommand);


        HELP_OP.addAll(Messages.GENERAL_HELP.toArrayList());
        HELP_OP.add("&7Authors: &eAbdulAzizCr &7- &eF4res");
        HELP_OP.add("&eSource Code: &bhttps://github.com/SxHundered/TFW-ImJFG");
    }

    @Getter
    static List<String> HELP_OP = new ArrayList<>();
}