package com.tfw.particleeffect;


import com.tfw.manager.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Locale;

public class ParticlePacketUtil {

    public static void teamElimination_Effect(Location location, Team team){
        try {

            ParticleEffect.FIREWORKS_SPARK.sendToPlayers(Bukkit.getOnlinePlayers(), location, 1.0f, 1.0f, 1.0f, 1, 4);
            ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), location, 1.0f, 1.0f, 1.0f, 1, 5);
            ParticleEffect.FLAME.sendToPlayers(Bukkit.getOnlinePlayers(), location, 2.7f, 1.5f, 2.0f, 1, 10);
            ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), location, 1.0f, 1.0f, 1.0f, 1, 5);
            ParticleEffect.FIREWORKS_SPARK.sendToPlayers(Bukkit.getOnlinePlayers(), location, 1.0f, 1.0f, 1.0f, 1, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
