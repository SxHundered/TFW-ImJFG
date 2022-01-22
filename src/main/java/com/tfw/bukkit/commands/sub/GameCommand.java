package com.tfw.bukkit.commands.sub;

import com.tfw.bukkit.commands.CommandBase;
import com.tfw.bukkit.commands.TFWCommand;
import com.tfw.configuration.Style;
import com.tfw.game.GameManager;
import com.tfw.game.arena.ArenaManager;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.utils.CustomLocation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GameCommand extends CommandBase<TFW> {

    public GameCommand(TFW plugin, String help) {
        super(plugin, help);
    }

    @Override
    public boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args) {
        if (args.length == 0){
            for (String s : CHECK_USAGE)
                sender.sendMessage(Style.translate(s));
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)){
            case "spawn":
                TFWLoader.getArenaManager().setSpawn(CustomLocation.fromBukkitLocation(((Player)sender).getLocation()));
                break;
        }

        return true;
    }

    final List<String> CHECK_USAGE = Arrays.asList(
            "/tfw team update",
            "/tfw team update"
    );
}
