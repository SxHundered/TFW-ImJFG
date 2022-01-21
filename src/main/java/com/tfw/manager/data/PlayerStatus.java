package com.tfw.manager.data;

import com.tfw.configuration.Style;
import lombok.Getter;

public enum PlayerStatus {

    LOBBY("@a", Style.YELLOW + "LOBBY"), PLAYING("@c", Style.GREEN + "PLAYING"), STAFF("@s", Style.RED + "STAFF"), DEAD("@d", Style.DARK_RED + "DEAD");

    @Getter
    private final String prefix;
    @Getter
    private final String currentStatus;

    PlayerStatus(String s, String statsName) {
        this.prefix = s;
        this.currentStatus = statsName;
    }
}