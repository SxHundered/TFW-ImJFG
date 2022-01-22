
package com.tfw.configuration;

import com.tfw.main.TFW;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class Style {

	public static final String API_FAILED =
			ChatColor.RED.toString() + "The API failed to retrieve your information. Try again later.";
	public static final String DEPENDENCIES_FAILED = ChatColor.RED + "One of the Dependencies failed to be found. Add missing dependencies first!, then try again!";
	public static final String BLUE = ChatColor.BLUE.toString();
	public static final String AQUA = ChatColor.AQUA.toString();
	public static final String YELLOW = ChatColor.YELLOW.toString();
	public static final String RED = ChatColor.RED.toString();
	public static final String GRAY = ChatColor.GRAY.toString();
	public static final String GOLD = ChatColor.GOLD.toString();
	public static final String GREEN = ChatColor.GREEN.toString();
	public static final String WHITE = ChatColor.WHITE.toString();
	public static final String BLACK = ChatColor.BLACK.toString();
	public static final String BOLD = ChatColor.BOLD.toString();
	public static final String ITALIC = ChatColor.ITALIC.toString();
	public static final String UNDER_LINE = ChatColor.UNDERLINE.toString();
	public static final String STRIKE_THROUGH = ChatColor.STRIKETHROUGH.toString();
	public static final String RESET = ChatColor.RESET.toString();
	public static final String MAGIC = ChatColor.MAGIC.toString();
	public static final String DARK_BLUE = ChatColor.DARK_BLUE.toString();
	public static final String DARK_AQUA = ChatColor.DARK_AQUA.toString();
	public static final String DARK_GRAY = ChatColor.DARK_GRAY.toString();
	public static final String DARK_GREEN = ChatColor.DARK_GREEN.toString();
	public static final String DARK_PURPLE = ChatColor.DARK_PURPLE.toString();
	public static final String DARK_RED = ChatColor.DARK_RED.toString();
	public static final String PINK = ChatColor.LIGHT_PURPLE.toString();
	public static final String BLANK_LINE = "§a §b §c §d §e §f §0 §r";
	public static final String BORDER_LINE_SCOREBOARD = Style.GRAY + Style.STRIKE_THROUGH + "----------------------";
	public static final String UNICODE_VERTICAL_BAR = Style.GRAY + StringEscapeUtils.unescapeJava("\u2503");
	public static final String UNICODE_CAUTION = StringEscapeUtils.unescapeJava("\u26a0");
	public static final String UNICODE_ARROW_LEFT = StringEscapeUtils.unescapeJava("\u25C0");
	public static final String UNICODE_ARROW_RIGHT = StringEscapeUtils.unescapeJava("\u25B6");
	public static final String UNICODE_ARROWS_LEFT = StringEscapeUtils.unescapeJava("\u00AB");
	public static final String UNICODE_ARROWS_RIGHT = StringEscapeUtils.unescapeJava("\u00BB");
	public static final String UNICODE_HEART = StringEscapeUtils.unescapeJava("\u2764");
	private static final String MAX_LENGTH = "11111111111111111111111111111111111111111111111111111";

	private Style() {
		throw new RuntimeException("Cannot instantiate a utility class.");
	}

	public static String strip(String in) {
		return ChatColor.stripColor(translate(in));
	}

	public static String translatePre(String in) {
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', in);
	}

	public static String translate(String in) {
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', in).replace("%prefix%", TFW.getPrefix());
	}

	public static List<String> translateLines_Holders(Player player, List<String> lines) {

		List<String> toReturn = new ArrayList<>();

		for (String line : lines) {
			line = PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', line.replace("%prefix%", TFW.getPrefix())));
			line = line.length() > 30 ? line.substring(0, 29) : line;
			toReturn.add(line);
		}

		return toReturn;
	}

	public static List<String> translateLines(List<String> lines) {

		List<String> toReturn = new ArrayList<>();

		for (String line : lines)
			toReturn.add(ChatColor.translateAlternateColorCodes('&', line.replace("%prefix%", TFW.getPrefix())));

		return toReturn;
	}

	public static String formatPlayerNotFoundMessage(String player) {
		return Style.RED + "Couldn't find a player with the name " + Style.RESET + player +
		       Style.RED + ". Have they joined the network?";
	}

	public static String formatBrokenProfileMessage(String player) {
		return Style.RED + "Couldn't load " + Style.RESET + player + Style.RED + "'s profile. Try again later.";
	}


}
