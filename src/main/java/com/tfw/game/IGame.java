package com.tfw.game;


import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.Team;
import org.bukkit.Sound;

import java.util.Set;

public interface IGame {

    void startGame();

    void teleportPlayers();

    void playSound(Sound sound);

    void notification(String message);

    void title_Notification(String title, String subTitle);

    void celebrate(Team team);

    void restartTheGame();

    String currentTime();

    void loadKits(Set<PlayerData> playerDataSet);

    void startGameTask();

    String game_info();
}
