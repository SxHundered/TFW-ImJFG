package com.tfw.scoreboard.boards;

/*
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
*/
/**
 * @author zyuiop
 *
 * Updated by Weiiswurst on 21/05/2020 to use the ProtocolLib API
 * instead of reflection, which allows cross-version compatibility.
 *
 * Updated by Giovanni75 on 10/07/2020 for some code cleanup.
 * Reviewed and shared to Spigot by Aurelien30000 on 10/07/2020.
 */

public class ScoreboardSign {
/*
    /*

        private static final ProtocolManager pm = ProtocolLibrary.getProtocolManager();

        private final Player player;
        private String objectiveName;

        private boolean created;
        private final VirtualTeam[] lines = new VirtualTeam[15];

        /**
         * Create a scoreboard sign for a given player and using a specific objective name.
         *
         * @param player        the player viewing it
         * @param objectiveName its name (displayed at the top of the scoreboard)
         *//*
    public ScoreboardSign(final Player player, final String objectiveName) {
        this.player = player;
        this.objectiveName = objectiveName;
    }
/*
    private void sendPacket(final PacketContainer pc) {
        try {
            pm.sendServerPacket(player, pc);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
/*
    /**
     * Send the initial creation packets for this scoreboard sign. Must be called at least once.
     *//*
    public void create() {
        if (created) return;

        sendPacket(createObjectivePacket(0, objectiveName));
        sendPacket(setObjectiveSlot());

        for (int i = 0; i < lines.length; i++)
            sendLine(i);

        created = true;
    }
/*
    /**
     * Send the packets to remove this scoreboard sign. A destroyed scoreboard sign must
     * be recreated using {@link ScoreboardSign#create()} in order to be used again.
     *//*
    public void destroy() {
        if (!created) return;

        sendPacket(createObjectivePacket(1, null));
        for (VirtualTeam team : lines)
            if (team != null)
                sendPacket(team.removeTeam());

        created = false;
    }
/*
    /**
     * Change the name of the objective. The name is displayed at the top of the scoreboard.
     *
     * @param name the name of the objective - max 32 characters
     *//*
    public void setObjectiveName(final String name) {
        this.objectiveName = name;
        if (created)
            sendPacket(createObjectivePacket(2, name));
    }
/*
    /**
     * Change a scoreboard line and send the packets to the player. Can be called asynchronously.
     *
     * @param line  the number of the line - between 0 and 14
     * @param value the new value for the scoreboard line
     *//*
    public void setLine(final int line, final String value) {
        final VirtualTeam team = getOrCreateTeam(line);
        final String old = team.getCurrentPlayer();

        if (value.equals(old)) return;

        if (old != null && created)
            sendPacket(removeLine(old));

        team.setValue(value);
        sendLine(line);
    }
    /*
        /**
         * Set all scoreboard lines to the list and send these to the player.
         *
         * @param list the list of the new scoreboard lines
         *//*
    public void setLines(final Iterable<String> list) {
        int i = 0;
        for (String s : list) {
            setLine(i, s);
            i++;
        }
    }
/*
    /**
     * Remove a given scoreboard line.
     *
     * @param line the line to remove
     *//*
    public void removeLine(final int line) {
        final VirtualTeam team = getOrCreateTeam(line);
        final String old = team.getCurrentPlayer();

        if (old != null && created) {
            sendPacket(removeLine(old));
            sendPacket(team.removeTeam());
        }

        lines[line] = null;
    }
    /*
        /**
         * Get the current value for a line.
         *
         * @param line the line
         * @return its content
         *//*
    public String getLine(final int line) {
        return line < 0 || line > 14 ? null : getOrCreateTeam(line).getValue();
    }
    /*
        /**
         * Get the team assigned to a line.
         *
         * @return the {@link VirtualTeam} used to display this line
         *//*
    public VirtualTeam getTeam(final int line) {
        return line < 0 || line > 14 ? null : getOrCreateTeam(line);
    }

    private void sendLine(final int line) {
        if (line < 0 || line > 14 || !created) return;

        final VirtualTeam team = getOrCreateTeam(line);
        for (PacketContainer pc : team.sendLine())
            sendPacket(pc);

        sendPacket(sendScore(team.getCurrentPlayer(), line));
        team.reset();
    }

    private VirtualTeam getOrCreateTeam(final int line) {
        if (lines[line] == null)
            lines[line] = new VirtualTeam("__fakeScore" + line, "", "");
        return lines[line];
    }
/*
    // 0 : Create
    // 1 : Delete
    // 2 : Update
    private PacketContainer createObjectivePacket(final int mode, final String displayName) {
        final PacketContainer pc = pm.createPacket(PacketType.Play.Server.SCOREBOARD_OBJECTIVE, true);

        pc.getIntegers().write(0, mode);
        pc.getStrings().write(0, player.getName());

        if (mode == 0 || mode == 2)
            pc.getStrings().write(1, displayName);

        return pc;
    }

    private PacketContainer setObjectiveSlot() {
        final PacketContainer pc = pm.createPacket(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);

        pc.getIntegers().write(0, 1);
        pc.getStrings().write(0, player.getName());

        return pc;
    }

    private PacketContainer sendScore(final String line, final int score) {
        final PacketContainer pc = pm.createPacket(PacketType.Play.Server.SCOREBOARD_SCORE);

        pc.getIntegers().write(0, score);
        pc.getScoreboardActions().write(0, EnumWrappers.ScoreboardAction.CHANGE);
        pc.getStrings().write(0, line).write(1, player.getName());

        return pc;
    }
/*
    private PacketContainer removeLine(final String line) {
        final PacketContainer pc = pm.createPacket(PacketType.Play.Server.SCOREBOARD_SCORE);

        pc.getScoreboardActions().write(0, EnumWrappers.ScoreboardAction.REMOVE);
        pc.getStrings().write(0, line);

        return pc;
    }
    /**
     * This class is used to manage the content of a line. Advanced users can use it as they want, but they are
     * encouraged to read and understand the code before doing so. Thus, use these methods at your own risk.
     */
    /*
    public static class VirtualTeam {

        private final String name;

        private String currentPlayer, oldPlayer;
        private String prefix, suffix;

        private boolean playerChanged, prefixChanged, suffixChanged;
        private boolean first = true;

        // Virtual team

        private VirtualTeam(final String name, final String prefix, final String suffix) {
            this.name = name;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public void reset() {
            prefixChanged = false;
            suffixChanged = false;
            playerChanged = false;
            oldPlayer = null;
        }

        public String getName() {
            return name;
        }

        // Prefix

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(final String prefix) {
            if (this.prefix == null || !this.prefix.equals(prefix))
                this.prefixChanged = true;
            this.prefix = prefix;
        }

        // Suffix

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(final String suffix) {
            if (this.suffix == null || !this.suffix.equals(prefix))
                this.suffixChanged = true;
            this.suffix = suffix;
        }

        // Packets

        private PacketContainer createPacket(final int mode) {
            final PacketContainer pc = pm.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM, true);

            pc.getIntegers().write(0, 0).write(1, mode).write(2, 0);
            pc.getStrings().write(0, name).write(1, "").write(2, prefix).write(3, suffix).write(4, "always");

            return pc;
        }

        public Iterable<PacketContainer> sendLine() {
            final List<PacketContainer> packets = new ArrayList<>();

            if (first) {
                first = false;
                packets.add(createTeam());
            } else if (prefixChanged || suffixChanged) {
                packets.add(updateTeam());
            }

            if (first || playerChanged) {
                if (oldPlayer != null)
                    packets.add(addOrRemovePlayer(4, oldPlayer));
                packets.add(changePlayer());
            }

            return packets;
        }

        // Team

        public PacketContainer createTeam() {
            return createPacket(0);
        }

        public PacketContainer removeTeam() {
            final PacketContainer pc = pm.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);

            pc.getIntegers().write(1, 1);
            pc.getStrings().write(0, name);
            first = true;

            return pc;
        }

        public PacketContainer updateTeam() {
            return createPacket(2);
        }

        // Player

        public PacketContainer addOrRemovePlayer(final int mode, final String playerName) {
            final PacketContainer pc = pm.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);

            pc.getIntegers().write(1, mode);
            pc.getSpecificModifier(Collection.class).write(0, Lists.newArrayList(playerName));
            pc.getStrings().write(0, name);

            return pc;
        }

        public PacketContainer changePlayer() {
            return addOrRemovePlayer(3, currentPlayer);
        }

        public String getCurrentPlayer() {
            return currentPlayer;
        }

        public void setPlayer(final String name) {
            if (this.currentPlayer == null || !this.currentPlayer.equals(name))
                this.playerChanged = true;
            this.oldPlayer = this.currentPlayer;
            this.currentPlayer = name;
        }

        // Value

        public String getValue() {
            return getPrefix() + getCurrentPlayer() + getSuffix();
        }

        public void setValue(final String value) {
            final int length = value.length();
            if (length <= 16) {
                setPrefix("");
                setPlayer(value);
                setSuffix("");
            } else if (length <= 32) {
                setPrefix(value.substring(0, 16));
                setPlayer(value.substring(16));
                setSuffix("");
            } else if (length <= 48) {
                setPrefix(value.substring(0, 16));
                setPlayer(value.substring(16, 32));
                setSuffix(value.substring(32));
            } else {
                throw new IllegalArgumentException("Too long virtual team value (" + length + " > 48 characters)");
            }
        }
    }
     */
}