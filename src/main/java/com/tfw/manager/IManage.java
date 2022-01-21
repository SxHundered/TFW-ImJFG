package com.tfw.manager;

import com.tfw.manager.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Stream;

public interface IManage {

    void addPlayer(Player player);

    void removePlayer(PlayerData playerData);

    PlayerData data(String playerName);

    void eliminatePlayer(PlayerData playerData);

    Stream<PlayerData> filtered_online_players();

    Set<PlayerData> exceptStaff();
}
