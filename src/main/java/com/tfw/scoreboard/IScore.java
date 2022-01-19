package com.tfw.scoreboard;

import java.util.List;

public interface IScore {

    void createScoreboard(List<String> lines);
    List<String> lines();
    String animatedText();
    boolean isAnimated();

    void info();
}
