package com.tfw.main;

import com.tfw.configuration.Style;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DependencyLoader {

    /**
     * @param javaPlugin Main Class
     * @return true if all dependencies have been loaded!
     * @throws Exception if we can not find the dependency in plugin list!
     */
    public static boolean load(JavaPlugin javaPlugin) throws Exception {

        //Now load them
        for (final PLUGINS dependency : PLUGINS.values())
            if (javaPlugin.getServer().getPluginManager().getPlugin(dependency.pluginName) != null)
                if (dependency.checkCompatibility(javaPlugin.getServer().getPluginManager().getPlugin(dependency.pluginName).getDescription().getVersion(), dependency.limited_version))
                    dependency.updatePlugin();
        else
            throw new Exception(Style.RED + "Sorry, InCompatible version for " + Style.YELLOW + dependency.pluginName + Style.RED +
                    " has to be at least " + Style.GREEN + dependency.limited_version + Style.RED + " or higher!");
        else if (!dependency.soft)
                throw new Exception(Style.RED + "Sorry, I can not find " + Style.GREEN + dependency.pluginName + "");

        return true;
    }

    /**
     * Enums for all dependencies
     * We have these attributes: PluginName,Version,Loaded!
     */
    public enum PLUGINS {

        /** LIST DEPENDENCIES */
        PLACEHOLDER_API("PlaceholderAPI", "2.11", false, false),
        /* END */

        /** List of SOFT DEPENDENCIES */
        SLIME_WORLD_MANAGER("SlimeWorldManager", "2.2.1", true, false),
        MULTIVERSE_PLUGIN("Multiverse-Core", "2.5", true, false);
        /* END */

        @Getter
        final String pluginName;
        String version;
        final String limited_version;
        final boolean soft;
        @Getter
        boolean loaded;

        PLUGINS(String plugin, String limited_version, boolean soft, boolean b) {
            this.pluginName = plugin;
            this.limited_version = limited_version;
            this.soft = soft;
            this.loaded = b;
        }

        boolean checkCompatibility(String version, String limited_version){
            return version.startsWith(limited_version);
        }
        void updatePlugin() {
            version = TFW.getInstance().getServer().getPluginManager().getPlugin(pluginName).getDescription().getVersion();
            Bukkit.getConsoleSender().sendMessage(Style.translate(
                    (soft ? "&eSOFT &cDEPENDENCY" : "&cDEPENDENCY") + "&b " + pluginName + "&6 version&7(&e" + version + "&7)&a has been loaded successfully!"));
            loaded = true;
        }
    }
}