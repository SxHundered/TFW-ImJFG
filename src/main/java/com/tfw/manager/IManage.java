package com.tfw.manager;

import com.tfw.manager.data.PlayerData;
import org.bukkit.entity.Player;

public interface IManage {

    void addPlayer(Player player);

    void removePlayer(PlayerData playerData);

    PlayerData data(String playerName);

    void eliminatePlayer(PlayerData playerData);
}
