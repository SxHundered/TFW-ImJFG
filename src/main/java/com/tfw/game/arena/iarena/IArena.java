package com.tfw.game.arena.iarena;


import com.tfw.manager.team.ITeam;

/**
 * Only Arena functions
 */
public interface IArena {

    void notification();
    void notification(ITeam team);

}
