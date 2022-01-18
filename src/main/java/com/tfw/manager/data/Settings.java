package com.tfw.manager.data;


import lombok.Getter;
import lombok.Setter;

public enum Settings {

    PLAYER_SETTINGS(false, false);

    @Setter@Getter
    boolean staff;
    @Setter@Getter
    boolean refresh;
    Settings(boolean b, boolean b1) {
        this.staff = b;
        this.refresh = b1;
    }
}
