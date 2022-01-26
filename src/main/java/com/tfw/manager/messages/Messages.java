package com.tfw.manager.messages;

import com.tfw.configuration.Style;
import com.tfw.main.TFWLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Messages {

    PREFIX("PREFIX"),
    NO_PERMISSION("NO_PERMISSION"),
    GENERAL_HELP("GENERAL_HELP"),
    CHECK_USAGE("CHECK_USAGE"),
    Offline_Player("OFFLINE_PLAYER"),
    NOT_ENOUGH_TEAM_MEMBERS("NOT_ENOUGH_TEAM_MEMBERS"),
    ALREADY_STARTED("ALREADY_STARTED"),
    SPAWN_UPDATED("SPAWN_UPDATED"),
    GAME_HELP("GAME_HELP"),
    BORDER_ACTIVATED("BORDER_ACTIVATED"),
    BORDER_DEACTIVATED("BORDER_DEACTIVATED"),
    CENTER_SAVED("CENTER_SAVED"),
    NUMBER_ERROR("NUMBER_ERROR"),
    BORDER_SIZE("BORDER_SIZE"),
    SETTINGS_UPDATED("SETTINGS_UPDATED"),
    STAFF_HELP("STAFF_HELP"),







    ;


    final String path;

    Messages(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        String message = TFWLoader.getMessageManager().getMessage().getYaml().getString("Messages." + this.path);

        if (message == null || message.isEmpty())
            return "Could not load message!";

        return Style.translate(message);
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
