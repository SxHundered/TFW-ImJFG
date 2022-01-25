package com.tfw.bukkit.commands.sub;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.configuration.Style;
import com.tfw.game.GameManager;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SettingsCommand extends CommandBase<TFW> {

    final String ALREADY_STARTED = "%prefix% settings works only in lobby state!";
    final String BORDER_ACTIVATED = "%prefix% the boarder has been activated successfully!";
    final String CENTER_SAVED = "%prefix% the boarder center has been saved!";
    final String NUMBER_ERROR = "%prefix% You must provide an invalid number!";
    final String BORDER_SIZE = "%prefix% The border size has been changed to %size%";
    final String SETTINGS_UPDATED = "%prefix% The settings has been updated successfully!";

    public SettingsCommand(TFW plugin, String help) {
        super(plugin, help);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        if (args.length == 0) {
            for (String s : CHECK_USAGE)
                sender.sendMessage(Style.translate(s));
            return true;
        }

        if (!GameManager.GameStates.getGameStates().equals(GameManager.GameStates.LOBBY)) {
            sender.sendMessage(Style.translate(ALREADY_STARTED));
            return true;
        }
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "border":
                switch (args[1].toLowerCase(Locale.ROOT)) {
                    case "active":
                        TFWLoader.getGameManager().getWorldSettings().isWorldBorder = true;
                        sender.sendMessage(Style.translate(BORDER_ACTIVATED));
                        break;
                    case "center":
                        try {
                            double x = Double.parseDouble(args[2]), z = Double.parseDouble(args[3]);
                            TFWLoader.getGameManager().getWorldSettings().center.clear();
                            TFWLoader.getGameManager().getWorldSettings().center.add(x);
                            TFWLoader.getGameManager().getWorldSettings().center.add(z);
                            sender.sendMessage(Style.translate(CENTER_SAVED));
                        } catch (NumberFormatException ignore) {
                            sender.sendMessage(Style.translate(NUMBER_ERROR));
                        }
                        break;
                    case "size":
                        try {
                            TFWLoader.getGameManager().getWorldSettings().size = Integer.parseInt(args[2]);
                            sender.sendMessage(Style.translate(BORDER_SIZE.replace("%size%", "" + Integer.parseInt(args[2]))));
                        } catch (NumberFormatException ignore) {
                            sender.sendMessage(Style.translate(NUMBER_ERROR));
                        }
                        break;
                    default:
                        CHECK_USAGE_BRODER.forEach(s -> sender.sendMessage(Style.translate(s)));
                        break;
                }
                break;
            case "update":
                TFWLoader.getGameManager().getWorldSettings().updateSettings();
                sender.sendMessage(Style.translate(SETTINGS_UPDATED));
                break;
            default:
                CHECK_USAGE.forEach(s -> sender.sendMessage(Style.translate(s)));
                break;
        }
        return true;
    }

    final List<String> CHECK_USAGE = Arrays.asList(
            "&7/tfw&e settings&b border &8-&7 Border settings",
            "&7/tfw&e settings&b update &8-&7 Update the border settings file"
    );

    final List<String> CHECK_USAGE_BRODER = Arrays.asList(
            "&7/tfw&e settings&b border&6 active &8-&7 Active the border",
            "&7/tfw&e settings&b border&6 center &8-&7 Change the border center point",
            "&7/tfw&e settings&b border&6 &size &8-&7 Change the border size"
    );
}