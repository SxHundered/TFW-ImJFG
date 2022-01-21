package com.tfw.manager;

import com.tfw.configuration.ConfigFile;
import com.tfw.configuration.Style;
import com.tfw.manager.team.Team;
import com.tfw.manager.team.kits.Kit;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *
 *     Only used to modify team class!
 */

public class TeamManager implements ITeams{

    @Getter(AccessLevel.PRIVATE)
    static ConfigFile teamsConfig;

    private String pathTeams;

    @Getter
    private static Team A;
    @Getter
    private static Team B;

    /**
     * @param javaPlugin MAIN CLASS
     * @throws TeamExceptions IF TEAMS FAILED TO BE GENERATED
     */
    @Override
    public void teamSetup(JavaPlugin javaPlugin) throws TeamExceptions {
        teamsConfig = new ConfigFile(javaPlugin, "teams.yml");
        pathTeams = "Teams";

        ConfigurationSection configurationSection = teamsConfig.getYaml().getConfigurationSection(pathTeams);
        if (configurationSection != null){
            if (configurationSection.getKeys(false).size() == 2) {
                for (String key : configurationSection.getKeys(false)) {
                    if (teamsConfig.getString(configurationSection.getCurrentPath() + "." + key + ".TEAM_TYPE").equalsIgnoreCase("A"))
                        A = new Team(key, teamsConfig.getString(configurationSection.getCurrentPath() + "." + key + ".display"),
                                teamsConfig.getString(configurationSection.getCurrentPath() + "." + key + ".chat_format"),
                                teamsConfig.getString(configurationSection.getCurrentPath() + "." + key + ".color"));
                    else
                        B = new Team(key, teamsConfig.getString(configurationSection.getCurrentPath() + "." + key + ".display"),
                                teamsConfig.getString(configurationSection.getCurrentPath() + "." + key + ".chat_format"),
                                teamsConfig.getString(configurationSection.getCurrentPath() + "." + key + ".color"));
                }
                //Kits loader
                Kit kitA = new Kit(A);
                kitA.loadKits(teamsConfig, pathTeams + "." + A.getIdentifier());
                A.setKit(kitA);

                Kit kitB = new Kit(B);
                kitB.loadKits(teamsConfig, pathTeams + "." + B.getIdentifier());
                B.setKit(kitB);

            }else
                throw new TeamExceptions(Style.translate("&a&lSORRY, &c&lTEAMS CAN NOT BE MORE THAN OR LESS THAN 2 TEAMS IN THE &7&lteams.yml!"));
        }else throw new TeamExceptions(Style.RED + "COULD NOT FIND ANY TEAM IN THE CONFIG!");
    }
}
