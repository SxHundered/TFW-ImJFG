package com.tfw.bukkit.commands.sub;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.messages.Messages;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CheckCommand extends CommandBase<TFW> {

    final String CHECK_USAGE = Messages.CHECK_USAGE.toString();
    final String OFFLINE_PLAYER = Messages.OFFLINE_PLAYER.toString();

    public CheckCommand(TFW plugin, String check) {
        super(plugin, check);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {

        if (args.length != 1) {
            sender.sendMessage(Style.translate(CHECK_USAGE));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null){
            sender.sendMessage(Style.translate(
                    OFFLINE_PLAYER.replace("%player_name%", args[0])));
            return true;
        }

        PlayerData playerData = TFWLoader.getPlayerManager().data(target.getUniqueId());
        if (playerData == null) {
            sender.sendMessage(Style.translate(
                    OFFLINE_PLAYER.replace("%player_name%", args[0])));
            return true;
        }

        for (String detail : DETAILS)
            sender.sendMessage(Style.translate(detail.replace("%player_name%", target.getName())
                    .replace("%states%", playerData.getPlayerStatus().getCurrentStatus()).replace("%team%",
                            playerData.getTeam() == null ? "&c&lNONE" : playerData.getTeam().getColorTeam() + playerData.getTeam().getTeam())));
        return true;
    }

    final List<String> DETAILS = Messages.CHECK_MESSAGE.toArrayList();
}
