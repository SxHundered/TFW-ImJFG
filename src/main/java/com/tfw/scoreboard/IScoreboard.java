package com.tfw.scoreboard;

import com.tfw.configuration.Style;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Scoreboard generator
@RequiredArgsConstructor@Data
public class IScoreboard implements IScore{

    private final String name;
    private int index = 0;
    private String staticTitle;
    private List<String> animationTitle;
    private String[] lines;

    public IScoreboard(String name, String title, int scoreboardSize) {
        this.name = name;
        animationTitle = null;
        this.staticTitle = title.length() > 32 ? title.substring(0, 31) : title;
        this.lines = new String[scoreboardSize];
    }

    //Only used when scoreboard has animated title!
    public IScoreboard(String name, List<String> animationTitle, int size){
        this.name = name;
        staticTitle = null;
        animationTitle = new ArrayList<>(animationTitle);
        this.lines = new String[size];
    }


    /**
     * @param name scoreboardName!
     * @param lines
     */
    @Override
    public void createScoreboard(String name, List<String> lines) {

    }

    @Override
    public List<String> lines() {
        return Style.translateLines(Arrays.asList(lines));
    }

    @Override
    public String animatedText() {
        return isAnimated() ? getAnimationTitle().get(index) : staticTitle;
    }

    @Override
    public boolean isAnimated() {
        return animationTitle != null;
    }
}
