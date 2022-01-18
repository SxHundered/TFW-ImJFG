package com.tfw.game.arena.iarena;

import com.tfw.game.arena.ArenaExceptions;
import com.tfw.manager.TeamManager;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.ITeam;
import com.tfw.utils.CustomLocation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *
 *     Arena details class!
 */
@RequiredArgsConstructor@Getter@Setter
public class Arena implements IArena{

    private final String name;

    private CustomLocation a;
    private CustomLocation b;
    private CustomLocation a_heart;
    private CustomLocation b_heart;

    @Override
    public void notification() {
        //All teams recieve massage!
    }

    /**
     * @param team Specify team to send notification
     */
    @Override
    public void notification(ITeam team) {
        for (PlayerData player : team.getMembers()) {

        }
    }

    @Override
    public int arena_Players() {
        return TeamManager.getA().currentAlive() + TeamManager.getB().currentAlive();
    }

    @Override
    public boolean prepareLocations(CustomLocation a, CustomLocation b, CustomLocation a_heart, CustomLocation b_heart) throws ArenaExceptions {
        //Here prepare locations Needed!
        this.a = a;
        this.b = b;
        this.a_heart = a_heart;
        this.b_heart = b_heart;
        return true;
    }
}
