package com.tfw.manager.team.heart;

import com.tfw.configuration.Style;
import com.tfw.manager.team.ITeam;
import com.tfw.utils.CustomLocation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

@RequiredArgsConstructor@Data
public class Heart implements IHeart{

    //TODO: F4RES DO IT PLEASE

    private final ITeam team;
    private final EntityType entity;
    private final CustomLocation location;
    private boolean destroyed = false;
    private World world;

    /**
     * Spawn the heart in the location!, using entity type! -> DONE
     * Add hologram to the top of the heart -> DO IT Abdul PLEASE
     */
    @Override
    public void spawnHeart() {
        world.spawnEntity(location.toBukkitLocation(), entity);
        //ADD THE HOLOGRAM
    }

    /**
     * Destroy the heart
     * Play Effects -> Done
     * Broadcast who destroyed the heart! -> Done
     */
    @Override
    public void destroyHeart(String destroyer) {
        Bukkit.broadcastMessage(Style.translate(destroyer + " has destroied " + getTeam() + "'s HEART!"));
        world.playEffect(getLocation().toBukkitLocation(), Effect.BLAZE_SHOOT, 1, 1);
    }

    /**
     * Effects shows when heart is spawned -> Done
     */
    @Override
    public void spawnEffect() {
        world.playEffect(getLocation().toBukkitLocation(), Effect.BLAZE_SHOOT, 1, 1);
    }

    /**
     * Effect shows when destroy the heart -> Done
     */
    @Override
    public void destroyEffect() {
        world.playEffect(getLocation().toBukkitLocation(), Effect.BLAZE_SHOOT, 1, 1);
    }
}
