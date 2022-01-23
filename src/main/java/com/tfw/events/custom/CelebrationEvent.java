package com.tfw.events.custom;

import com.tfw.main.TFW;
import com.tfw.main.TFWLoader;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.Team;
import com.tfw.scoreboard.IScoreboardException;
import com.tfw.scoreboard.IScoreboardManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class CelebrationEvent extends Event {

    @Getter
    @Setter
    private boolean canceled = false;

    @Getter
    private final Team winners;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public void start_Celebration() {

        final Location location = TFWLoader.getArenaManager().getSpawn().toBukkitLocation();
        //final Color color = Color.fromBGR(java.awt.Color.getColor(winners.getColor()).getBlue(), java.awt.Color.getColor(winners.getColor()).getGreen(), java.awt.Color.getColor(winners.getColor()).getRed());
        final Color color = Color.RED;

        new BukkitRunnable() {
            int counter = 0;

            @Override
            public void run() {
                counter++;
                //Spawns FireWorks!
                fireWorkLauncher(location, color);
                if (counter == 10) {
                    TFWLoader.getGameManager().restartTheGame();
                    cancel();
                }
            }
        }.runTaskTimer(TFW.getInstance(), 20L, 20L);
    }

    private static void fireWorkLauncher(Location location, Color color){
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.setPower(10);
        fireworkMeta.addEffect(FireworkEffect.builder().trail(true).withColor(color).withFade(Color.BLACK).build());
    }

    public void toggleScoreBoard(){
        Bukkit.getServer().getScheduler().runTaskAsynchronously(TFW.getInstance(), ()-> {
            for (PlayerData playerData : TFWLoader.getPlayerManager().filtered_online_players()) {
                if (playerData.isOnline())
                    try {
                        playerData.getFastBoard().setIScoreboard(
                                TFWLoader.getIScoreboardManager().getScoreBoard(IScoreboardManager.ScoreboardTYPE.WIN));
                    } catch (IScoreboardException e) {
                        e.printStackTrace();
                    }
            }
        });
    }
}