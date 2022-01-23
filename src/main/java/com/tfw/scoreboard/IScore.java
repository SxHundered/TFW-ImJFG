package com.tfw.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public interface IScore {

    void createScoreboard(List<String> lines);
    List<String> lines();
    String animatedText(Player player);
    boolean isAnimated();

    void info();
}
