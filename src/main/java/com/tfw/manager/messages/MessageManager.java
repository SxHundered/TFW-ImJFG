package com.tfw.manager.messages;

import com.tfw.configuration.ConfigFile;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class MessageManager implements IMessage{

    @Getter(AccessLevel.PRIVATE)
    static ConfigFile messagesConfig;

    @Override
    public void messageSetup(JavaPlugin javaPlugin) {
        messagesConfig = new ConfigFile(javaPlugin, "messages.yml");


    }

    public ConfigFile getMessage(){
        return messagesConfig;
    }

}
