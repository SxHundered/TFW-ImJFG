package com.tfw.manager.messages;

import com.tfw.configuration.ConfigFile;
import org.bukkit.plugin.java.JavaPlugin;

public interface IMessage {

    void messageSetup(JavaPlugin javaPlugin);

    ConfigFile getMessage();

}
