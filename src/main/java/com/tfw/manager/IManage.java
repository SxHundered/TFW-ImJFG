package com.tfw.manager;

import com.tfw.manager.data.PlayerData;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Stream;

public interface IManage {

    void addPlayer(Player player);

    void removePlayer(PlayerData playerData);

    PlayerData data(String playerName);

    void eliminatePlayer(PlayerData playerData);

    Set<PlayerData> filtered_online_players();

    Set<PlayerData> exceptStaff();

    Set<PlayerData> onlyTeamPlayers();

    TextComponent getStaffAsString();
}
