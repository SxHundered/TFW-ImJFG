package com.tfw.manager.data;

import lombok.Setter;

public enum Stats {

    STATS(0);

    @Setter
    int kills;

    Stats(int i) {
        this.kills = i;
    }
}
