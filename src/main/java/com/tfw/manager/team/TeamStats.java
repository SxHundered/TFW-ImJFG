package com.tfw.manager.team;

import lombok.Getter;
import lombok.Setter;

public enum TeamStats {

    STATS(0);

    @Setter@Getter
    int kills;

    TeamStats(int i) {
        this.kills = i;
    }
}
