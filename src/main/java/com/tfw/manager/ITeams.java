package com.tfw.manager;

import com.tfw.configuration.ConfigFile;
import org.bukkit.plugin.java.JavaPlugin;

public interface ITeams {

    void teamSetup(JavaPlugin javaPlugin) throws TeamExceptions;
    void updateConfig(ConfigFile settings) throws TeamExceptions;
}
