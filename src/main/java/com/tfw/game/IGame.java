package com.tfw.game;


import org.bukkit.plugin.java.JavaPlugin;

public interface IGame {

    void startGame();

    void teleportPlayers();

    void playSound();

    void notification(String message);

    void celebrate();

    void restartTheGame();

    int gameTime();

    String currentTime();

}
