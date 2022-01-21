package com.tfw.bukkit.events;

import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageHandler implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent damageEvent){
        damageEvent.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageByEntityEvent event){

        if (!(event.getEntity() instanceof Player))
            return;

        PlayerData playerData = TFWLoader.getPlayerManager().data(event.getEntity().getName());
        if (playerData == null) {
            event.setCancelled(true);
            return;
        }

        if (event.getDamager() instanceof Player){

            final Player damager = (Player) event.getDamager();
            final PlayerData damagerData = TFWLoader.getPlayerManager().data(damager.getName());

            if (damagerData == null) {
                event.setCancelled(true);
                return;
            }
            if (playerData.getTeam() == null) {
                event.setCancelled(true);
                return;
            }

            if (playerData.getTeam().equals(damagerData.getTeam()))
                event.setCancelled(true);
        }else if (event.getDamager() instanceof Projectile){
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player){
                final Player damager = (Player) projectile.getShooter();
                final PlayerData damagerData = TFWLoader.getPlayerManager().data(damager.getName());

                if (damagerData == null) {
                    event.setCancelled(true);
                    return;
                }
                if (playerData.getTeam() == null) {
                    event.setCancelled(true);
                    return;
                }

                if (playerData.getTeam().equals(damagerData.getTeam()))
                    event.setCancelled(true);
            }
        }
    }
}
