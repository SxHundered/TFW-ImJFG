package com.tfw.main;

import com.tfw.configuration.Style;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashSet;
import java.util.Set;

public class DependencyLoader {


    public static Set<PLUGINS> depencendies = new LinkedHashSet<>();

    /**
     * @param javaPlugin Main Class
     * @return true if all dependencies have been loaded!
     * @throws Exception if we can not find the dependency in plugin list!
     */
    public static boolean load(JavaPlugin javaPlugin) throws Exception {
        depencendies.add(PLUGINS.PLACEHOLDER_API);

        //Now load them
        for (PLUGINS depencendy : depencendies)
            if (!depencendy.soft) {
                if (javaPlugin.getServer().getPluginManager().getPlugin(depencendy.pluginName) != null)
                    depencendy.updatePlugin();
                else
                    throw new Exception(ChatColor.RED + "Sorry, I can not find " + ChatColor.GREEN + depencendy.pluginName + "");
            } else {
                if (javaPlugin.getServer().getPluginManager().getPlugin("SlimeWorldManager") != null)
                    depencendy.updatePlugin();
            }
        return true;
    }

    /**
     * Enums for all dependencies
     * We have these attributes: PluginName,Version,Loaded!
     */
    public enum PLUGINS {

        PLACEHOLDER_API("PlaceholderAPI", false, false),

        SLIME_WORLD_MANAGER("SlimeWorldManager", true, false);

        @Getter
        final String pluginName;
        String version;
        final boolean soft;
        @Getter
        boolean loaded;

        PLUGINS(String plugin, boolean soft, boolean b) {
            this.pluginName = plugin;
            this.soft = soft;
            this.loaded = b;
        }

        void updatePlugin() {
            version = TFW.getInstance().getServer().getPluginManager().getPlugin(pluginName).getDescription().getVersion();
            Bukkit.getConsoleSender().sendMessage(Style.translate(
                    (soft ? "&eSOFT &cDEPENDENCY" : "&cDEPENDENCY") + "&b " + pluginName + "&6 version&7(&e" + version + "&7)&a has been loaded successfully!"));
            loaded = true;
        }
    }
}