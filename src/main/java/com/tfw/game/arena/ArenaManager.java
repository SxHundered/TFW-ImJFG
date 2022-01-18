package com.tfw.game.arena;

/**
 *     ######################################################
 *     #      THIS PROJECT HAS BEEN CREATED BY ABDULAZIZCR  #
 *     #              DISCORD: Az#7012                      #
 *     #              IGN: AbdulAzizCr                      #
 *     #                                                    #
 *     #              Date: 1/17/2022                       #
 *     ######################################################
 *
 *     Only handle arenas modifications!
 */

public class ArenaManager implements IArenas {

    @Setter@Getter
    private static Arena arena;

    @Getter@Setter
    private static String worldName;

    @Getter(AccessLevel.PRIVATE)
    String pathArena = "main_Arena";
    @Getter(AccessLevel.PRIVATE)
    ConfigFile arenasConfig;

    String[] chars = {"a", "a_heart", "b", "b_heart"};

    /**
     * @param javaPlugin MAIN CLASS
     * @throws ArenaExceptions WHEN ARENA FAILS TO BE GENERATED, IT THROWS THIS EXCEPTION!
     */
    @Override
    public void arenaSetup(JavaPlugin javaPlugin) throws ArenaExceptions {
        arenasConfig = new ConfigFile(javaPlugin, "arenas.yml");

        if (!arenasConfig.getYaml().contains(pathArena))
            throw new ArenaExceptions(Style.RED + Style.translate("&cCOULD NOT FIND ANY ARENA!, &aPLEASE SETUP USING &7/arena create &c&lmain"));
