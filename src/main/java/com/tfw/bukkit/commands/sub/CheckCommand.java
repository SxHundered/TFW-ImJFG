package com.tfw.bukkit.commands.sub;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CheckCommand extends CommandBase<TFW> {

    final String CHECK_USAGE = "&7/tfw&e check&b <playerName> &8| &7Displays player current info!";
    final String OFFLINE_PLAYER = "%prefix% %playerName% &e&lis currently &4&lOFFLINE";

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
                    OFFLINE_PLAYER.replace("%playerName%", args[0])));
            return true;
        }

        PlayerData playerData = TFWLoader.getPlayerManager().data(target.getUniqueId());
        if (playerData == null) {
            sender.sendMessage(Style.translate(
                    OFFLINE_PLAYER.replace("%playerName%", args[0])));
            return true;
        }

        for (String detail : DETAILS)
            sender.sendMessage(Style.translate(detail.replace("%playerName%", target.getName())
                    .replace("%states%", playerData.getPlayerStatus().getCurrentStatus()).replace("%team%",
                            playerData.getTeam() == null ? "&c&lNONE" : playerData.getTeam().getColorTeam() + playerData.getTeam().getTeam())));
        return true;
    }

    final List<String> DETAILS = Arrays.asList(
            "&a%playerName% &7details:",
            "&bCurrent State: %states%",
            "&bJoined Team: %team%",
            "");
}
