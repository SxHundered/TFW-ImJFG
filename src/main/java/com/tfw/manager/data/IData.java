package com.tfw.manager.data;

import com.tfw.manager.team.Team;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public interface IData {


    Player getPlayer();

    void preparePlayer();

    boolean isOnline();

    void textPlayer(String massage);

    void textPlayer(TextComponent massage);

    void updateStatus(PlayerStatus playerStatus);

    void clearPlayer();

    void selectTeam(Team iTeam);

    void generateScoreboard();

    void updateStats();

}
