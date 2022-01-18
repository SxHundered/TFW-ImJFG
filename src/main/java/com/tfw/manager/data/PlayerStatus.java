package com.tfw.manager.data;

import lombok.Getter;

public enum PlayerStatus {

    LOBBY("@a"),PLAYING("@c"),STAFF("@s"),DEAD("@d");

    @Getter
    private final String prefix;
    PlayerStatus(String s) {
        this.prefix = s;
    }
}
