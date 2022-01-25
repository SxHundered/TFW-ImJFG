package com.tfw.bukkit.commands.sub;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.configuration.Style;
import com.tfw.events.custom.PreparePlayersEvent;
import com.tfw.events.custom.StaffAddEvent;
import com.tfw.events.custom.StaffRemoveEvent;
import com.tfw.game.GameManager;
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

public class StaffCommand extends CommandBase<TFW> {


    public StaffCommand(TFW plugin, String help) {
        super(plugin, help);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        if (args.length == 0) {
            for (String s : CHECK_USAGE)
                sender.sendMessage(Style.translate(s));
            return true;
        }

            if (args[0].toLowerCase(Locale.ROOT).equalsIgnoreCase("staff")) {
                PlayerData playerData;

                if (args.length != 3) {
                        for (String s : CHECK_USAGE)
                            sender.sendMessage(Style.translate(s));
                        return true;
                }
                switch (args[1].toLowerCase(Locale.ROOT)) {
                    case "add":
                        playerData = TFWLoader.getPlayerManager().data(((Player) sender).getUniqueId());
                        assert playerData != null;
                        StaffAddEvent staffAddEvent = new StaffAddEvent(args[2], playerData);
                        Bukkit.getServer().getPluginManager().callEvent(staffAddEvent);
                        break;
                    case "remove":
                        playerData = TFWLoader.getPlayerManager().data(((Player) sender).getUniqueId());
                        assert playerData != null;
                        StaffRemoveEvent staffRemoveEvent = new StaffRemoveEvent(args[2], playerData);
                        Bukkit.getServer().getPluginManager().callEvent(staffRemoveEvent);
                        break;
                    case "list":
                        ((Player) sender).spigot().sendMessage(TFWLoader.getPlayerManager().getStaffAsString());
                        break;
                }
            }

        return true;
    }

    final List<String> CHECK_USAGE = Arrays.asList(
            "&7/tfw&e staff&b add &e<playername> &8-&7 Add a player to staff mode",
            "&7/tfw&e staff&b remove &e<playername> &8-&7 Removes a player to staff mode",
            "&7/tfw&e staff&b list &8-&7 Staff list!"
    );
}