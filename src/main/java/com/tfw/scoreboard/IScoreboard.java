package com.tfw.scoreboard;

import com.tfw.configuration.Style;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor@Getter@Setter
public class IScoreboard extends BukkitRunnable implements IScore{

    private final String name;
    private int index = 0;
    private String staticTitle;
    private List<String> animationTitle;
    private String[] lines;

    public IScoreboard(String name, String title, int scoreboardSize) {
        this.name = name;
        animationTitle = null;
        this.staticTitle = Style.translate(title.length() > 32 ? title.substring(0, 31) : title);
        this.lines = new String[scoreboardSize];
    }

    //Only used when scoreboard has animated title!
    public IScoreboard(String name, List<String> animationTitle, int size){
        this.name = name;
        staticTitle = null;
        this.animationTitle = new ArrayList<>(animationTitle);

        this.lines = new String[size];
    }


    /**
     * @param lines Scoreboard lines!
     */
    @Override
    public void createScoreboard(List<String> lines) {
        int x = 0;
        for (; x < this.lines.length; x++)
            this.lines[x] = lines.get(x);
    }

    @Override
    public List<String> lines() {
        return Style.translateLines(Arrays.asList(lines));
    }

    @Override
    public String animatedText(Player player) {
        return isAnimated() ? Style.translateLine_Holders(player, getAnimationTitle().get(index)) : "NO ANIMATED TITLE!";
    }

    @Override
    public boolean isAnimated() {
        return staticTitle == null;
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

    @Override
    public void run() {
        //if Animated Run this task!
        if ((index +1) == animationTitle.size())
            index = 0;
        else
            index++;
    }
}
