package com.tfw.manager.team.heart;

import com.tfw.manager.data.PlayerData;
import com.tfw.utils.CustomLocation;

public interface IHeart {

    void spawnHeart();

    void destroyHeart(PlayerData destroyer);

    void updateHeart(CustomLocation location);

    void spawnEffect();

    void destroyEffect();
}
