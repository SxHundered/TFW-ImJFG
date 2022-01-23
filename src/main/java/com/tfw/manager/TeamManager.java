package com.tfw.manager;

import com.tfw.configuration.ConfigFile;
import com.tfw.configuration.Style;
import com.tfw.game.arena.ArenaManager;
import com.tfw.main.TFWLoader;
import com.tfw.manager.team.Team;
import com.tfw.manager.team.kits.Kit;
import com.tfw.utils.CustomLocation;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

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
        String pathTeams = "Teams";

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

                A.heartSetUp(Material.BED, null);
                B.heartSetUp(Material.BED, null);

                //Kits loader
                Kit kitA = new Kit(A);
                kitA.loadKits(teamsConfig, pathTeams + "." + A.getIdentifier(), A.getIdentifier());
                A.setKit(kitA);

                Kit kitB = new Kit(B);
                kitB.loadKits(teamsConfig, pathTeams + "." + B.getIdentifier(), B.getIdentifier());
                B.setKit(kitB);

            }else
                throw new TeamExceptions(Style.translate("&a&lSORRY, &c&lTEAMS CAN NOT BE MORE THAN OR LESS THAN 2 TEAMS IN THE &7&lteams.yml!"));
        }else throw new TeamExceptions(Style.RED + "COULD NOT FIND ANY TEAM IN THE CONFIG!");
    }

    @Override
    public void updateConfig(ConfigFile arenasConfig) throws TeamExceptions {
        arenasConfig.getYaml().set("main_Arena.displayName", ArenaManager.getArena() != null ? ArenaManager.getArena().getName() : "THE GREAT WAR ARENA");

        arenasConfig.getYaml().set("main_Arena.spawn", TFWLoader.getArenaManager().getSpawn() == null ? null : CustomLocation.locationToString(TFWLoader.getArenaManager().getSpawn()));
        arenasConfig.getYaml().set("main_Arena.locations.a", CustomLocation.locationToString(getA().getSpawn()));
        arenasConfig.getYaml().set("main_Arena.locations.b", CustomLocation.locationToString(getB().getSpawn()));
        arenasConfig.getYaml().set("main_Arena.locations.a_heart", CustomLocation.locationToString(getA().getHeart().getLocation()));
        arenasConfig.getYaml().set("main_Arena.locations.b_heart", CustomLocation.locationToString(getB().getHeart().getLocation()));
        arenasConfig.save();

        arenasConfig.load();
    }

    public Team findTeam(String teamName){
        return getA().getIdentifier().equalsIgnoreCase(teamName) ? getA() : getB().getIdentifier().equalsIgnoreCase(teamName) ? getB() : null;
    }
}
