package com.tfw.manager.team.heart;

import com.tfw.configuration.Style;
import com.tfw.events.custom.HeartDestroyEvent;
import com.tfw.game.GameManager;
import com.tfw.main.TFW;
import com.tfw.manager.data.PlayerData;
import com.tfw.manager.team.ITeam;
import com.tfw.manager.team.Team;
import com.tfw.particleeffect.ParticleEffect;
import com.tfw.utils.CustomLocation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.material.Bed;

@RequiredArgsConstructor@Data
public class Heart implements IHeart{

    private final ITeam team;
    private final Material entity;
    private CustomLocation location; //#HEARTLOCATION
    private boolean destroyed = false;
    private World world;
    private BlockState BLOCK_HEAD;
    private BlockState BLOCK_FOOT;
    private ArmorStand armorStand;

    /**
     * Spawn the heart in the location!, using entity type! -> DONE
     * Add hologram to the top of the heart -> DONE
     */
    @Override
    public void spawnHeart() {
        //TODO: Animation
        final Location bukkitLocation = location.toBukkitLocation();

        Block block = bukkitLocation.getBlock();
        block.setType(Material.BED_BLOCK);
        BlockState bedFoot = block.getState();
        BLOCK_FOOT = bedFoot;
        if (bedFoot.getData() instanceof Bed) {
            BlockState bedHead = bedFoot.getBlock().getRelative(BlockFace.SOUTH).getState();
            BLOCK_HEAD = bedHead;
            bedFoot.setType(Material.BED_BLOCK);
            bedHead.setType(Material.BED_BLOCK);
            bedFoot.setRawData((byte) 0x0);
            bedHead.setRawData((byte) 0x8);
            bedFoot.update(true, false);
            bedHead.update(true, true);
        }

        /*Location fallingLocation = new Location(BukkitLocation.getWorld(), BukkitLocation.getX(), BukkitLocation.getY() + 10, BukkitLocation.getZ(), BukkitLocation.getYaw(), BukkitLocation.getPitch());

        BukkitLocation.getWorld().spawnFallingBlock(fallingLocation, Material.ANVIL, (byte) 0);*/

        armorStand = (ArmorStand) bukkitLocation.getWorld().spawnEntity(bukkitLocation, EntityType.ARMOR_STAND);

        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setCustomName(Style.translate(team.getColorTeam() + team.getTeam() + "'s &c&lHEART"));
        armorStand.setCustomNameVisible(true);

        getTeam().broadcastTeam(TFW.getPrefix() + ChatColor.GREEN + "YOUR HEART HAS BEEN SPAWNED!\n  §e§lPROTECT YOUR HEART,§c§l AND DESTROY THEIR HEART TO WIN THE GAME!");
    }

    /**
     * Destroy the heart
     * Play Effects -> Done
     * Broadcast who destroyed the heart! -> Done
     */
    @Override
    public void destroyHeart(PlayerData destroyer) {

        if (GameManager.GameStates.getGameStates().equals(GameManager.GameStates.INGAME)) {
            destroyEffect();
            getBLOCK_HEAD().getBlock().setType(Material.AIR);
            getBLOCK_FOOT().getBlock().setType(Material.AIR);
            HeartDestroyEvent heartDestroyEvent = new HeartDestroyEvent(destroyer, (Team) getTeam());
            Bukkit.getServer().getPluginManager().callEvent(heartDestroyEvent);
        }else
            destroyer.textPlayer("%prefix% " + ChatColor.RED + "Heart can not be destroyed in this state!");
    }

    @Override
    public void updateHeart(CustomLocation location) {
        setLocation(location);
        setWorld(Bukkit.getWorld(location.getWorld()));
    }

    /**
     * Effects shows when heart is spawned -> Done
     */
    @SneakyThrows
    @Override
    public void spawnEffect() {
        ParticleEffect.HEART.sendToPlayers(Bukkit.getOnlinePlayers(), getLocation().toBukkitLocation(), 1.0f, 1.0f, 1.0f, 1, 20);
        ParticleEffect.HEART.sendToPlayers(Bukkit.getOnlinePlayers(), getLocation().toBukkitLocation(), 1.2f, 0.5f, 0.2f, 1, 20);
        world.playEffect(getLocation().toBukkitLocation(), Effect.BLAZE_SHOOT, 1, 2);
        world.playEffect(getLocation().toBukkitLocation(), Effect.BLAZE_SHOOT, 1, 2);
    }

    /**
     * Effect shows when destroy the heart -> Done
     */
    @SneakyThrows
    @Override
    public void destroyEffect() {
        ParticleEffect.EXPLOSION_HUGE.sendToPlayers(Bukkit.getOnlinePlayers(), getLocation().toBukkitLocation(), 1.0f, 1.0f, 1.0f, 1, 20);
        ParticleEffect.EXPLOSION_HUGE.sendToPlayers(Bukkit.getOnlinePlayers(), getLocation().toBukkitLocation(), 1.2f, 0.5f, 0.2f, 1, 20);
        world.playEffect(getLocation().toBukkitLocation(), Effect.FLAME, 10, 2);
    }

}
