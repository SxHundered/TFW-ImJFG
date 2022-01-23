package com.tfw.events.custom;

import com.tfw.main.TFW;
import com.tfw.manager.data.PlayerData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

@RequiredArgsConstructor@Getter
public class PreparePlayersEvent extends Event {

    @Setter
    private boolean canceled = false;

    private final Set<PlayerData> playerDataSet;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public void startTeleportation() {
        for (PlayerData playerData : playerDataSet) {
            if (playerData.isOnline()) {

                playerData.clearPlayer();

                playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20, 3));
                playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 3));
                playerData.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 3));

                playerData.getPlayer().teleport(playerData.getTeam().getSpawn().toBukkitLocation());
            }
        }
    }
}