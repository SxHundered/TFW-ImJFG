package com.tfw.main;

import com.tfw.configuration.Style;
import lombok.Getter;
import org.bukkit.Bukkit;
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
        depencendies.add(PLUGINS.MULTIVERSE_PLUGIN);
        depencendies.add(PLUGINS.SLIME_WORLD_MANAGER);

        //Now load them
        for (PLUGINS depencendy : depencendies)
            if (javaPlugin.getServer().getPluginManager().getPlugin(depencendy.pluginName) != null)
                if (depencendy.checkCompatibility(javaPlugin.getServer().getPluginManager().getPlugin(depencendy.pluginName).getDescription().getVersion(), depencendy.limited_version))
                    depencendy.updatePlugin();
        else
            throw new Exception(Style.RED + "Sorry, InCompatible version for " + Style.YELLOW + depencendy.pluginName + Style.RED +
                    " has to be at least " + Style.GREEN + depencendy.limited_version + Style.RED + " or higher!");
        else if (!depencendy.soft)
                throw new Exception(Style.RED + "Sorry, I can not find " + Style.GREEN + depencendy.pluginName + "");

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