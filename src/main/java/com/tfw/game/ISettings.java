package com.tfw.game;

import com.tfw.game.arena.iarena.Arena;
import org.bukkit.plugin.java.JavaPlugin;

public interface ISettings {


    void initializeSettings(JavaPlugin javaPlugin);
    void gameSetup(JavaPlugin javaPlugin) throws WorldExceptions;
    Arena getArena();

}
