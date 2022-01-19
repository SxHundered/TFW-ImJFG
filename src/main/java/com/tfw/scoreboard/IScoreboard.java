package com.tfw.scoreboard;

import com.tfw.configuration.Style;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public void info() {
        Bukkit.getConsoleSender().sendMessage(Style.translate("§9IScoreBoard{"));
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "name: " + name);
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "index: " + index);
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "staticTitle: " + (staticTitle == null ? "§cNO STATIC TITLE" : staticTitle) );
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "AnimationList: " + (isAnimated() ? Arrays.toString(Style.translateLines(getAnimationTitle()).toArray()) : "§cNO ANIMATED TITLE!") + "");
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "lines:");
        for (String line : lines)
            Bukkit.getConsoleSender().sendMessage(Style.translate(line));
        Bukkit.getConsoleSender().sendMessage(Style.BLUE + "}");
    }
}
