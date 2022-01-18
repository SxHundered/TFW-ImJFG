package com.tfw.configuration;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigFile {

    private final JavaPlugin plugin;
    @Getter
    private File file;
    @Getter private YamlConfiguration yaml;

    /**
     * 	Generate config instance remember this only generated once per yaml file!
     *
     * @param plugin JavaPlugin Class
     * @param name File Name without .yml!
     */
    public ConfigFile(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), name);

        if (!file.getParentFile().exists())
            file.getParentFile().mkdir();

        plugin.saveResource(name, false);

        yaml = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * @param path for double in config.
     * @return if path doesn't exist it will return 0.
     */
    public double getDouble(String path) {
        if (yaml.contains(path)) {
            return yaml.getDouble(path);
        }
        return 0;
    }

    public int getInt(String path) {
        if (yaml.contains(path)) {
            return yaml.getInt(path);
        }
        return 0;
    }

    public boolean getBoolean(String path) {
        if (yaml.contains(path)) {
            return yaml.getBoolean(path);
        }
        return false;
    }

    public String getString(String path) {
        if (yaml.contains(path)) {
            return ChatColor.translateAlternateColorCodes('&', yaml.getString(path));
        }
        return "ERROR: STRING NOT FOUND";
    }

    /**
     * @param path path of the string using x.x.*
     * @param callback returned message
     * @param colorize you want to change colors &
     * @return return callback message if not exist meaning callback can be written as : cannot find string required! or even the path its self
     */
    public String getString(String path, String callback, boolean colorize) {
        if (yaml.contains(path)) {
            if (colorize) {
                return ChatColor.translateAlternateColorCodes('&', yaml.getString(path));
            } else {
                return yaml.getString(path);
            }
        }
        return callback;
    }

    /**
     *  Return reversedString list there are no comparator just indexes
     *
     * @param path path of the list using x.x.*
     * @return return the reversedList if not exist just return single list!
     */
    public List<String> getReversedStringList(String path) {
        List<String> list = getStringList(path);
        if (list != null) {
            int size = list.size();
            List<String> toReturn = new ArrayList<>();
            for (int i = size - 1; i >= 0; i--) {
                toReturn.add(list.get(i));
            }
            return toReturn;
        }
        return Collections.singletonList("ERROR: STRING LIST NOT FOUND!");
    }

    /**
     *  Finding StringList other wise return SingleList with massage error contains
     *  Casting colors by default we do not get & :P
     *
     * @param path path of the list using x.x.*
     * @return String list
     */
    public List<String> getStringList(String path) {
        if (yaml.contains(path)) {
            ArrayList<String> strings = new ArrayList<>();
            for (String string : yaml.getStringList(path)) {
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            }
            return strings;
        }
        return Collections.singletonList("ERROR: STRING LIST NOT FOUND!");
    }

    /**
     *  Finding StringList other wise return empty string list!
     *  Casting colors by default we do not get & :P
     *
     * @param path path of the string using x.x.*
     * @param toReturn emtpy array we provide to be returned if nothing exists in the config!
     * @return the array list we wanted
     *
     */
    public List<String> getStringListOrDefault(String path, List<String> toReturn) {
        if (yaml.contains(path)) {
            ArrayList<String> strings = new ArrayList<>();
            for (String string : yaml.getStringList(path)) {
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            }
            return strings;
        }
        return toReturn;
    }

    /**
     * 	Load Yaml yaml
     */
    public void load() {
        try {
            final String previousFileName = file.getName() + ".yml";
            file = new File(plugin.getDataFolder(), previousFileName);
            yaml = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 	Save current yaml
     */
    public void save() {
        try {
            getYaml().save(file);
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
