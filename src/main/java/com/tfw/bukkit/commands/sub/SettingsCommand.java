package com.tfw.bukkit.commands.sub;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.configuration.Style;
import com.tfw.game.GameManager;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.messages.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SettingsCommand extends CommandBase<TFW> {

    final String ONLY_IN_LOBBY = Messages.ONLY_IN_LOBBY.toString();
    final String BORDER_ACTIVATED = Messages.BORDER_ACTIVATED.toString();
    final String BORDER_DEACTIVATED = Messages.BORDER_DEACTIVATED.toString();
    final String CENTER_SAVED = Messages.CENTER_SAVED.toString();
    final String NUMBER_ERROR = Messages.NUMBER_ERROR.toString();
    final String BORDER_SIZE = Messages.BORDER_SIZE.toString();
    final String SETTINGS_UPDATED = Messages.SETTINGS_UPDATED.toString();

    public SettingsCommand(TFW plugin, String help) {
        super(plugin, help);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        if (args.length < 1) {
            for (String s : SETTINGS_USAGE)
                sender.sendMessage(Style.translate(s));
            return true;
        }

        if (!GameManager.GameStates.getGameStates().equals(GameManager.GameStates.LOBBY)) {
            sender.sendMessage(Style.translate(ONLY_IN_LOBBY));
            return true;
        }


        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "border":
                if (args.length >= 2) {
                    switch (args[1].toLowerCase(Locale.ROOT)) {
                        case "active":
                            TFWLoader.getGameManager().getWorldSettings().isWorldBorder = true;
                            sender.sendMessage(Style.translate(BORDER_ACTIVATED));
                            break;
                        case "disable":
                            TFWLoader.getGameManager().getWorldSettings().isWorldBorder = false;
                            sender.sendMessage(Style.translate(BORDER_DEACTIVATED));
                            break;
                        case "center":

                            if (args.length == 4) {
                                try {
                                    double x = Double.parseDouble(args[2]), z = Double.parseDouble(args[3]);
                                    TFWLoader.getGameManager().getWorldSettings().center.clear();
                                    TFWLoader.getGameManager().getWorldSettings().center.add(x);
                                    TFWLoader.getGameManager().getWorldSettings().center.add(z);
                                    sender.sendMessage(Style.translate(CENTER_SAVED));
                                } catch (NumberFormatException ignore) {
                                    sender.sendMessage(Style.translate(NUMBER_ERROR));
                                }
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
                            SETTINGS_USAGE_BOARDER.forEach(s -> sender.sendMessage(Style.translate(s)));
                            break;
                    }
                }
                break;
            case "update":
                TFWLoader.getGameManager().getWorldSettings().updateSettings();
                sender.sendMessage(Style.translate(SETTINGS_UPDATED));
                break;
            default:
                SETTINGS_USAGE.forEach(s -> sender.sendMessage(Style.translate(s)));
                break;
        }
        return true;
    }

    final List<String> SETTINGS_USAGE = Messages.SETTINGS_HELP.toArrayList();

    final List<String> SETTINGS_USAGE_BOARDER = Messages.SETTINGS_HELP_BOARDER.toArrayList();
}