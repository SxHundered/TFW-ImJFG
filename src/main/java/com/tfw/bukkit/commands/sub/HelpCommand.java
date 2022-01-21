package com.tfw.bukkit.commands.sub;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.bukkit.commands.TFWCommand;
import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpCommand  extends CommandBase<TFW> {

    public HelpCommand(TFW plugin, String help) {
        super(plugin, help);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        TFWCommand.getHELP_OP().forEach(s -> sender.sendMessage(Style.translate(s)));
        return false;
    }
}
