package com.tfw.scoreboard;

import com.tfw.configuration.ConfigFile;
import com.tfw.configuration.Style;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Locale;

public class IScoreboardManager {

    @Getter
    private final HashMap<ScoreboardTYPE, IScoreboard> scoreboardHashMap = new HashMap<>();

    //Load all scoreboard
    public void loadScoreboards(JavaPlugin javaPlugin){
        final String pathPrefix = "scoreboards";
        ConfigFile scoreboards_config = new ConfigFile(javaPlugin, "scoreboards.yml");

        ConfigurationSection configurationSection = scoreboards_config.getYaml().getConfigurationSection("scoreboards");

        for (String identifier : configurationSection.getKeys(false)) {

            IScoreboard iScoreboard;
            if (scoreboards_config.getBoolean(pathPrefix + "." + identifier + ".titleOptions.animated"))
                iScoreboard = new IScoreboard(identifier.toUpperCase(Locale.ROOT),
                        scoreboards_config.getStringList(pathPrefix + "." + identifier + ".titleOptions.title"), //TitleOptions
                        scoreboards_config.getStringList(pathPrefix + "." + identifier + ".lines").size()); //Lines
            else iScoreboard = new IScoreboard(identifier.toUpperCase(Locale.ROOT),
                    scoreboards_config.getStringList(pathPrefix + "." + identifier + ".titleOptions.title").get(0),
                    scoreboards_config.getStringList(pathPrefix + "." + identifier + ".lines").size());

            //Path
            iScoreboard.createScoreboard(Style.translateLines(scoreboards_config.getStringList(pathPrefix + "." + identifier + ".lines")));

            switch (identifier.toLowerCase(Locale.ROOT)){
                case "lobby":
                    scoreboardHashMap.put(ScoreboardTYPE.LOBBY, iScoreboard);
                    break;
                case "grace":
                    scoreboardHashMap.put(ScoreboardTYPE.GRACE, iScoreboard);
                    break;
                case "ingame":
                    scoreboardHashMap.put(ScoreboardTYPE.INGAME, iScoreboard);
                    break;
                case "staff":
                    scoreboardHashMap.put(ScoreboardTYPE.STAFF, iScoreboard);
            }
        }
    }

    public IScoreboard getScoreBoard(ScoreboardTYPE scoreboardTYPE) throws IScoreboardException{
        switch (scoreboardTYPE){
            case LOBBY:
            case GRACE:
            case INGAME:
            case STAFF:
                return scoreboardHashMap.get(scoreboardTYPE);
        }
        throw new IScoreboardException("%prefix% &cInvalid SCOREBOARD TYPE: &7(&aLOBBY&7,GRACE,INGAME,STAFF)");
    }

    public void reloadScoreboards(){
        //Reload all scoreboards!

    }

    public enum ScoreboardTYPE{
        LOBBY,GRACE,INGAME,STAFF
    }
}
