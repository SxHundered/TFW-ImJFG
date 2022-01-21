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
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                Color color = null;
                try {
                    Field field = Class.forName("java.awt.Color").getField(team.getColor().toLowerCase(Locale.ROOT));
                    color = (Color)field.get(null);
                } catch (Exception ignore) {}

                if (color == null) {
                    Bukkit.getConsoleSender().sendMessage("NO COLOR HAS BEEN DEFINED FOR " + team.getColor());
                    return;
                }

                ParticleEffect.REDSTONE.sendColor(onlinePlayer, location, Color.getHSBColor(color.getRed(), color.getGreen(), color.getBlue()), true);
                ParticleEffect.REDSTONE.sendColor(onlinePlayer, location, Color.getHSBColor(color.getRed(), color.getGreen(), color.getBlue()), true);
                ParticleEffect.REDSTONE.sendColor(onlinePlayer, location, Color.getHSBColor(color.getRed(), color.getGreen(), color.getBlue()), true);
                ParticleEffect.REDSTONE.sendColor(onlinePlayer, location, Color.getHSBColor(color.getRed(), color.getGreen(), color.getBlue()), true);
                ParticleEffect.FIREWORKS_SPARK.sendToPlayers(Bukkit.getOnlinePlayers(), location, 1.0f, 1.0f, 1.0f, 1, 2);
                ParticleEffect.FIREWORKS_SPARK.sendToPlayers(Bukkit.getOnlinePlayers(), location, 1.0f, 1.0f, 1.0f, 1, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
