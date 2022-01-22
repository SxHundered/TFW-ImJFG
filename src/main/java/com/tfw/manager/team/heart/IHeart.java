package com.tfw.manager.team.heart;

import com.tfw.utils.CustomLocation;

public interface IHeart {

    void spawnHeart();

    void destroyHeart(String destroyer);

    void updateHeart(CustomLocation location);

    void spawnEffect();

    void destroyEffect();
}
