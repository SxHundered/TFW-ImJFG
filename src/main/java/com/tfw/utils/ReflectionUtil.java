package com.tfw.utils;


import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;


public class ReflectionUtil {


    public static void sendTitle(Player player, String title, String subtitle, int in, int stay, int out) throws Exception {

        Class<?> chatSerializer = Objects.requireNonNull(ReflectionUtil.getNMSClass("IChatBaseComponent")).getClasses()[0];
        Method method = ReflectionUtil.getMethod(chatSerializer, "a", String.class);
        Object chatSubTitle = ReflectionUtil.invokeMethod(chatSerializer, method, ChatColor.translateAlternateColorCodes('&', "{\"text\": \"" + subtitle + "\"}"));

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");

        Class<?> packetClass = ReflectionUtil.getNMSClass("PacketPlayOutTitle");
        assert packetClass != null;
        Class<?> enumPacket = packetClass.getClasses()[0];

        Constructor<?> packetPlayOutCons = ReflectionUtil.getConstructor(packetClass, enumPacket, ReflectionUtil.getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
        Object enumTitle = Arrays.stream(enumPacket.getEnumConstants()).filter(a -> a.toString().equalsIgnoreCase("TITLE")).collect(Collectors.toList()).get(0);
        Object enumSubTitle = Arrays.stream(enumPacket.getEnumConstants()).filter(a -> a.toString().equalsIgnoreCase("SUBTITLE")).collect(Collectors.toList()).get(0);
        Object enumTimes = Arrays.stream(enumPacket.getEnumConstants()).filter(a -> a.toString().equalsIgnoreCase("TIMES")).collect(Collectors.toList()).get(0);

        Object packetTimes = ReflectionUtil.newConstructorInstance(packetPlayOutCons, enumTimes, chatTitle, in, stay, out);
        Object packetTitle = ReflectionUtil.newConstructorInstance(packetPlayOutCons, enumTitle, chatTitle, in, stay, out);
        Object packetSubTitle = ReflectionUtil.newConstructorInstance(packetPlayOutCons, enumSubTitle, chatSubTitle, in, stay, out);


       sendPacket(player, packetTimes);
       sendPacket(player, packetTitle);
       sendPacket(player, packetSubTitle);
    }

    /**
     * Gets net.minecraft.server... class
     * @param name
     * @return Class
     */
    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get class constructor by classname and ClassTypes parms
     * @param className
     * @param parms
     * @return
     * @throws NoSuchMethodException
     */
    public static Constructor<?> getConstructor(Class<?> className, Class<?>... parms) throws NoSuchMethodException{
        return className.getConstructor(parms);
    }

    /**
     * Get new Instance class by constructor and ClassTypes of parms
     * @param constructor
     * @param parms
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static Object newConstructorInstance(Constructor<?> constructor, Object... parms) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException{
        return constructor.newInstance(parms);
    }

    /**
     * Get new Instance class by empty constructor
     * @param constructor
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @see #newConstructorInstance(Constructor, Object...)
     */
    public static Object newConstructorInstance(Constructor<?> constructor) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException{
        return constructor.newInstance();
    }

    /**
     * Get Method by className, methodName and ClassTypes of parms
     * @param className
     * @param name
     * @param parms
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getMethod(Class<?> className, String name, Class<?>... parms) throws NoSuchMethodException{
        return className.getMethod(name,parms);
    }

    /**
     * Get Method by className, methodName and ClassTypes of parms
     * @param className
     * @param name
     * @return
     * @throws NoSuchMethodException
     * @see #getConstructor(Class, Class[])
     */
    public static Method getMethod(Class<?> className, String name) throws NoSuchMethodException{
        return className.getMethod(name);
    }

    /**
     * Get Object of invoking method given by Class Instance object, method, and ObjectTypes parms
     * @param instance
     * @param method
     * @param parms
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static Object invokeMethod(Object instance,Method method, Object... parms) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        return method.invoke(instance,parms);
    }

    /**
     * Get Object of invoking method given by Class Instance object, method
     * @param instance
     * @param method
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @see #invokeMethod(Object, Method, Object...)
     */
    public static Object invokeMethod(Object instance,Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        return method.invoke(instance);
    }

    /**
     * Get class's field by className and fieldMethod
     * @param className
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    public static Field getField(Object className,String fieldName) throws NoSuchFieldException{
        Field field = className.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    /**
     * Get class's field value by Object classname, and field name with given Field
     * @param className
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @see #getField(Object, String)
     */
    public static Object getFieldValue(Object className, String fieldName) throws NoSuchFieldException,IllegalAccessException {
        return getField(className,fieldName).get(className);
    }

    /**
     * Set field value in class by field name, classname and newValue
     * @param field
     * @param className
     * @param value
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @see #getField(Object, String)
     */
    public static void setFieldValue(String field, Object className,Object value) throws NoSuchFieldException,IllegalAccessException{
        getField(className,field).set(className, value);
    }

    /**
     * Get EntityPlayer class
     * @param player
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static Object getHandle(Player player) throws NoSuchMethodException,IllegalAccessException,InvocationTargetException{
        return invokeMethod(player, getMethod(player.getClass(),"getHandle"));
    }


    /**
     * Set header and footer
     * @param header
     * @param footer
     * @param p
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    public static void setHeaderAndFooter(String header, String footer, Player p) throws NoSuchMethodException,IllegalAccessException,InvocationTargetException,InstantiationException,NoSuchFieldException{

        Class<?> chatSerializer = getNMSClass("IChatBaseComponent").getClasses()[0];

        Object headerObj = chatSerializer.getMethod("a",String.class).invoke(chatSerializer,"{\"text\":\" "+ header +" \"}");
        Object footerObj = chatSerializer.getMethod("a",String.class).invoke(chatSerializer,"{\"text\":\" "+ footer +" \"}");

        Object packet = newConstructorInstance(getConstructor(Objects.requireNonNull(getNMSClass("PacketPlayOutPlayerListHeaderFooter"))));
        setFieldValue("a",packet,headerObj);
        setFieldValue("b",packet,footerObj);

        sendPacket(p,packet);

    }

    /**
     * Send packet by reflection
     * @param player
     * @param packet
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static void sendPacket(Player player, Object packet) throws NoSuchFieldException,NoSuchMethodException,IllegalAccessException,InvocationTargetException{
        Object handle = player.getClass().getMethod("getHandle").invoke(player);
        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
    }

    public static void sendActionBar(Player p, String message) {

        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        CraftPlayer craftplayer = (CraftPlayer) p;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
        connection.sendPacket(ppoc);
    }

    public static void sendTabHF(Player player, String header, String footer) {

        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent headerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent footerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, headerJSON);
            headerField.setAccessible(!headerField.isAccessible());

            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, footerJSON);
            footerField.setAccessible(!footerField.isAccessible());
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.sendPacket(packet);

    }


}