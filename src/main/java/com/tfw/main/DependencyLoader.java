package com.tfw.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashSet;
import java.util.Set;

public class DependencyLoader {

    static Set<PLUGINS> depencendies = new LinkedHashSet<>();

    /**
     * @param javaPlugin Main Class
     * @return true if all dependencies have been loaded!
     * @throws Exception if we can not find the dependency in plugin list!
     */
    public static boolean load(JavaPlugin javaPlugin) throws Exception {
        depencendies.add(PLUGINS.PLACEHOLDER_API);

        //Now load them
        for (PLUGINS depencendy : depencendies)
            if (javaPlugin.getServer().getPluginManager().getPlugin(depencendy.pluginName) != null)
                depencendy.updatePlugin();
            else
                throw new Exception(ChatColor.RED + "Sorry, I can not find " + ChatColor.GREEN + depencendy.pluginName + "");

        return true;
    }

    /**
     * Enums for all dependencies
     * We have these attributes: PluginName,Version,Loaded!
     */
    enum PLUGINS {
        PLACEHOLDER_API("PlaceholderAPI", false);

        final String pluginName;
        String version;
        boolean loaded;

        PLUGINS(String plugin, boolean b) {
            this.pluginName = plugin;
            this.loaded = b;
        }

        void updatePlugin() {
            version = TFW.getInstance().getServer().getPluginManager().getPlugin(pluginName).getDescription().getVersion();
            Bukkit.getConsoleSender().sendMessage("§b" + pluginName + "§6 version§7(§e" + version + "§7)§a has been loaded correctly!");
            loaded = true;
        }
    }
}