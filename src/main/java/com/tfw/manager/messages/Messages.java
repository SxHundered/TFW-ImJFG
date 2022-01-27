package com.tfw.manager.messages;

import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Messages {

    GENERAL_HELP("TFW.HELP"),
    CHECK_USAGE("CHECK.USAGE"),
    OFFLINE_PLAYER("GENERAL.OFFLINEPLAYER"),
    CHECK_MESSAGE("GENERAL.CHECK.MESSAGE"),
    NOT_ENOUGH_TEAM_MEMBERS("GAME.NOT_ENOUGH_TEAM_MEMBERS"),
    ALREADY_STARTED("GAME.ALREADY_STARTED"),
    SPAWN_UPDATED("GAME.SPAWN_UPDATED"),
    GAME_USAGE("GAME.HELP"),
    A("CHECK.USAGE");

    final String path;

    Messages(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        String message = TFWLoader.getMessageManager().getMessage().getYaml().getString("Messages." + this.path);

        if (message == null || message.isEmpty())
            return "Could not load message!";

        return Style.translate(message.replace("%prefix%", TFW.getPrefix()));
    }

    public List<String> toArrayList() {
        List<String> message = TFWLoader.getMessageManager().getMessage().getYaml().getStringList("Messages." + this.path);

        if (message == null || message.isEmpty())
            return Collections.singletonList("Could not load message!");

        List<String> translated = new ArrayList<>();
        for (String msg : message)
            translated.add(Style.translate(msg));

        return translated;
    }

    public String getPath() {
        return this.path;
    }

}
