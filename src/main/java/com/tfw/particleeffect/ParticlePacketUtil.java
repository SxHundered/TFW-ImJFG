package com.tfw.particleeffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ParticlePacketUtil {

    public static void teamElimination_Effect(Location location) {
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
