package com.tfw.events;

import com.tfw.configuration.Style;
import com.tfw.main.DependencyLoader;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SlimeWorldEvents implements Listener {


    public SlimeWorldEvents(JavaPlugin javaPlugin){

        final DependencyLoader.PLUGINS plugin = DependencyLoader.depencendies.stream().filter(plugins -> plugins.getPluginName().equalsIgnoreCase("SlimeWorldManager")).findFirst().orElse(null);
        if (plugin != null)
            if (plugin.isLoaded()) {
                javaPlugin.getServer().getPluginManager().registerEvents(this, javaPlugin);
                Bukkit.getServer().getConsoleSender().sendMessage(Style.translate(
                        "&aAll SlimeWorldEvents have been triggered!, &e&lSUCCESSFULLY!"));
            }
    }



}
