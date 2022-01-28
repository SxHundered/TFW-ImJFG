package com.tfw.manager.messages;

import com.tfw.configuration.Style;
import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Messages {

    NO_PERMISSIONS("GENERAL.NO_PERMISSIONS"),
    GENERAL_HELP("TFW.HELP"),
    CHECK_USAGE("CHECK.USAGE"),
    OFFLINE_PLAYER("GENERAL.OFFLINEPLAYER"),
    CHECK_MESSAGE("GENERAL.CHECK.MESSAGE"),
    NOT_ENOUGH_TEAM_MEMBERS("GAME.NOT_ENOUGH_TEAM_MEMBERS"),
    ALREADY_STARTED("GAME.ALREADY_STARTED"),
    SPAWN_UPDATED("GAME.SPAWN_UPDATED"),
    GAME_USAGE("GAME.HELP"),
    GAME_STARTED("GAME.STARTED"),
    GAME_STARTEDTITLE("GAME.STARTEDTITLE"),
    GAME_STARTEDSUUBTITLE("GAME.STARTEDSUBTITLE"),
    CAN_NOT_BREAK_HEART("GAME.CAN_NOT_BREAK_HEART"),


    ONLY_IN_LOBBY("SETTINGS.ONLY_IN_LOBBY"),
    BORDER_ACTIVATED("SETTINGS.ONLY_IN_LOBBY"),
    BORDER_DEACTIVATED("SETTINGS.ONLY_IN_LOBBY"),
    CENTER_SAVED("SETTINGS.ONLY_IN_LOBBY"),
    NUMBER_ERROR("SETTINGS.ONLY_IN_LOBBY"),
    BORDER_SIZE("SETTINGS.ONLY_IN_LOBBY"),
    SETTINGS_UPDATED("SETTINGS.ONLY_IN_LOBBY"),
    SETTINGS_HELP_BOARDER("SETTINGS.HELP.BORDER"),
    SETTINGS_HELP("SETTINGS.HELP.USAGE"),
    STAFF_HELP("STAFF.HELP.USAGE"),

    STAFF_PLAYER("TEAM.STAFF_PLAYER"),
    ALREADY_IN_TEAM("TEAM.ALREADY_IN_TEAM"),
    ALREADY_NOT_IN_TEAM("TEAM.ALREADY_NOT_IN_TEAM"),
    TEAM_NOT_FOUND("TEAM.TEAM_NOT_FOUND"),
    PLAYER_ADDED_TO_TEAM("TEAM.PLAYER_ADDED_TO_TEAM"),
    YOU_HAVE_BEEN_ADDED("TEAM.YOU_HAVE_BEEN_ADDED"),
    REMOVED_FROM_TEAM("TEAM.REMOVED_FROM_TEAM"),
    YOU_HAVE_BEEN_REMOVED("TEAM.YOU_HAVE_BEEN_REMOVED"),
    TEAM_COLOR("TEAM.TEAM_COLOR"),
    TEAM_SCORE("TEAM.TEAM_SCORE"),
    TEAM_SPAWN("TEAM.TEAM_SPAWN"),
    TEAM_HEART("TEAM.TEAM_HEART"),
    CONFIGURATION_SAVED("TEAM.CONFIGURATION_SAVED"),
    TEAM_HELP("TEAM.HELP"),
    ISCORE_USAGE("ISCORE.USAGE"),
    ISCORE_ERROR("ISCORE.ERROR"),
    ISCORE_SUCCESSFULLY("ISCORE.SUCCESSFULLY"),


    CAN_NOT_JOIN("GENERAL.CAN_NOT_JOIN"),
    ONLY_STAFF_JOIN("GENERAL.ONLY_STAFF_JOIN"),
    NOT_IN_TEAM("CHAT.NOT_IN_TEAM"),
    IN_TEAM("CHAT.IN_TEAM"),
    STAFF("CHAT.STAFF"),
    WINNING_MESSAGE("WINNING.MESSAGE"),
    CAN_NOT_ADDED("STAFF.CAN_NOT_ADDED"),
    ALREADY_STAFF("STAFF.ALREADY_STAFF"),
    ADDED_TO_STAFF("STAFF.ADDED_TO_STAFF"),
    IS_PLAYING("STAFF.IS_PLAYING"),
    YOU_ARE_NOW_STAFF("STAFF.YOU_ARE_NOW_STAFF"),
    IS_NOT_STAFF("STAFF.IS_NOT_STAFF"),
    REMOVED_FROM_STAFF("STAFF.REMOVED_FROM_STAFF"),
    ALREADY_NOT_STAFF("STAFF.ALREADY_NOT_STAFF"),
    REJOIN("STAFF.REJOIN"),

    STARTINGTITLE("STARTING.TITLE"),
    STARTINGSUBTITLE("STARTING.SUBTITLE"),
    STARTINGMESSAGE("STARTING.MESSAGE"),

    HEARTHOLOGRAM("HEART.HOLOGRAM"),

    HEART_SPAWNED_MESSAGE("HEART.SPAWNED"),
    HEART_CAN_NOT_DESTROYED("HEART.CAN_NOT_DESTROYED"),
    RECEIVED_KIT("GAME.RECEIVED_KIT"),
    JOINED_TEAM("TEAM.JOINED"),
    LEFT_TEAM("TEAM.LEFT"),
    HAS_BEEN_KILLED("TEAM.PLAYER_KILLED"),
    LOST_TOURNAMENT("GENERAL.LOST_TOURNAMENT"),
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
