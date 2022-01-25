package com.tfw.utils.autoSkinModifier;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.tfw.main.TFW;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SkinModifier {

    public void skinChanger(Player player, UUID playerName) {
        try {
            final Class<?> craftPlayer = NMSReflections.getCraftBukkitClass("entity.CraftPlayer");
            if (craftPlayer == null)
                throw new NullPointerException("Error while trying to nick player! The CraftPlayer is null!");

            final Method playerHandle = craftPlayer.getMethod("getHandle");
            final Object entityPlayer = playerHandle.invoke(player);

            Object newEntityPlayer = Array.newInstance(entityPlayer.getClass(), 1);
            Array.set(newEntityPlayer, 0, entityPlayer);

            int currentFieldModifier = 2;
            Object playOutTitle = Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo"))
                    .getDeclaredClasses()[currentFieldModifier].getField("REMOVE_PLAYER").get(null);
            Class<?> constructorArray = Array.newInstance(NMSReflections.getNMSClass("EntityPlayer"), 0)
                    .getClass();
            Constructor<?> packetConstructor = Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo")).getConstructor(Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo")).getDeclaredClasses()[currentFieldModifier], constructorArray);

            Object playerRemovePacket = packetConstructor.newInstance(playOutTitle, newEntityPlayer);
            Object playOutTitleAdd = Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo")).getDeclaredClasses()[currentFieldModifier].getField("ADD_PLAYER").get(null);
            Class<?> constructorArrayAdd = Array.newInstance(NMSReflections.getNMSClass("EntityPlayer"), 0).getClass();
            Constructor<?> packetConstructorAdd = Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo")).getConstructor(Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo")).getDeclaredClasses()[currentFieldModifier], constructorArrayAdd);
            Object playerAddPacket = packetConstructorAdd.newInstance(playOutTitleAdd, newEntityPlayer);

            sendPacket(playerRemovePacket);
            sendPacket(playerAddPacket);
        } catch (NoSuchFieldError | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }

        gameProfileModifier(player, playerName);
    }

    private void gameProfileModifier(Player player, UUID playerName) {
        try {
            GameProfile gameProfile = (GameProfile) player.getClass().getMethod("getProfile").invoke(player);
            gameProfile.getProperties().removeAll("textures");

            GameProfile skinGameProfile = GameProfileBuilder.fetch(playerName);
            Collection<Property> props = skinGameProfile.getProperties().get("textures");

            gameProfile.getProperties().putAll("textures", props);

            final Class<?> craftPlayer = NMSReflections.getCraftBukkitClass("entity.CraftPlayer");
            assert craftPlayer != null;
            final Method playerHandle = craftPlayer.getMethod("getHandle");
            final Object entityPlayer = playerHandle.invoke(player);

            Object newEntityPlayer = Array.newInstance(entityPlayer.getClass(), 1);
            Array.set(newEntityPlayer, 0, entityPlayer);

            Bukkit.getOnlinePlayers().forEach(currentPlayer -> {
                if (!(currentPlayer.getName().equals(player.getName()))) {
                    try {
                        sendPacket(currentPlayer, Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutEntityDestroy"))
                                .getConstructor(int[].class).newInstance((Object) new int[]{player.getEntityId()}));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                        ex.printStackTrace();
                    }
                }
            });


            final String version = NMSReflections.getVersion();
            Object playerRemovePacket;
            Object playerAddPacket;

            int v1_1 = (version.startsWith("v1_1") && !(version.equals("1_10_R1"))) ? 1
                    : 2;
            Object playOutTitle = Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo"))
                    .getDeclaredClasses()[v1_1].getField("REMOVE_PLAYER").get(null);
            Class<?> constructorArray = Array.newInstance(NMSReflections.getNMSClass("EntityPlayer"), 0)
                    .getClass();
            Constructor<?> packetConstructor = Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo"))
                    .getConstructor(
                            Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo")).getDeclaredClasses()[
                                    v1_1],
                            constructorArray);
            playerRemovePacket = packetConstructor.newInstance(playOutTitle, newEntityPlayer);

            Object playOutTitleAdd = Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo"))
                    .getDeclaredClasses()[v1_1].getField("ADD_PLAYER").get(null);
            Class<?> constructorArrayAdd = Array
                    .newInstance(NMSReflections.getNMSClass("EntityPlayer"), 0).getClass();
            Constructor<?> packetConstructorAdd = Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo"))
                    .getConstructor(
                            Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutPlayerInfo")).getDeclaredClasses()[
                                    v1_1],
                            constructorArrayAdd);
            playerAddPacket = packetConstructorAdd.newInstance(playOutTitleAdd, newEntityPlayer);

            sendPacket(playerRemovePacket);

            new BukkitRunnable() {
                public void run() {
                    try {
                        sendPacket(playerAddPacket);

                        Object entitySpawnPacket = Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutNamedEntitySpawn"))
                                .getConstructor(NMSReflections.getNMSClass("EntityHuman"))
                                .newInstance(entityPlayer);

                        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                            if (!(onlinePlayer.getName().equals(player.getName())))
                                sendPacket(onlinePlayer, entitySpawnPacket);

                        int heldItemSlot = player.getInventory().getHeldItemSlot();
                        int food = player.getFoodLevel();
                        double health = player.getHealth();
                        float xp = player.getExp();
                        int level = player.getLevel();
                        boolean flying = player.isFlying();

                        final Class<?> craftPlayer = NMSReflections.getCraftBukkitClass("entity.CraftPlayer");

                        if (craftPlayer == null)
                            return;

                        final Method playerHandle = craftPlayer.getMethod("getHandle");
                        final Object entityPlayer = playerHandle.invoke(player);

                        Object worldClient = entityPlayer.getClass().getMethod("getWorld").invoke(entityPlayer);
                        Object worldData = worldClient.getClass().getMethod("getWorldData").invoke(worldClient);
                        Object interactManager = entityPlayer.getClass().getField("playerInteractManager").get(entityPlayer);

                        Class<?> enumGameMode = Objects.requireNonNull(NMSReflections.getNMSClass("WorldSettings")).getDeclaredClasses()[0];

                        Object respawnPacket = Objects.requireNonNull(NMSReflections.getNMSClass("PacketPlayOutRespawn"))
                                .getConstructor(int.class, NMSReflections.getNMSClass("EnumDifficulty")
                                        , NMSReflections.getNMSClass("WorldType"), enumGameMode)
                                .newInstance(player.getWorld().getEnvironment().getId(),
                                        worldClient.getClass().getMethod("getDifficulty").invoke(worldClient),
                                        worldData.getClass().getMethod("getType").invoke(worldData),
                                        interactManager.getClass().getMethod("getGameMode").invoke(interactManager));

                        sendPacket(player, respawnPacket);

                        player.updateInventory();
                        player.getInventory().setHeldItemSlot(heldItemSlot);
                        player.setHealth(health);
                        player.setExp(xp);
                        player.setLevel(level);
                        player.setFoodLevel(food);
                        player.setFlying(flying);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | InstantiationException | NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            }.runTaskLater(TFW.getInstance(), 3L);
        } catch (NoSuchFieldError | NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | InstantiationException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    private void sendPacket(Object packet) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers())
            sendPacket(onlinePlayer, packet);
    }

    private void sendPacket(Player player, Object packet) {
        try {
            final Object playerHandle = player.getClass().getMethod("getHandle").invoke(player);
            final Object playerConnection = playerHandle.getClass().getDeclaredField("playerConnection").get(playerHandle);
            playerConnection.getClass().getMethod("sendPacket", NMSReflections.getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }
}