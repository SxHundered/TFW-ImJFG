package com.tfw.game.arena.iarena;


import com.tfw.game.arena.ArenaExceptions;
import com.tfw.manager.team.ITeam;
import com.tfw.utils.CustomLocation;
import lombok.Getter;
import lombok.Setter;

/**
 * Only Arena functions
 */
public interface IArena {

    void notification();
    void notification(ITeam team);
    int arena_Players();

    boolean prepareLocations(CustomLocation a, CustomLocation b, CustomLocation a_heart, CustomLocation b_heart) throws ArenaExceptions;

    static enum ARENA_STATUS{
        LOBBY,IN_GAME,RESTARTING;

        @Getter
        @Setter
        private static ARENA_STATUS arena_status;
    }
}
