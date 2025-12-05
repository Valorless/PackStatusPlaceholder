package valorless.packstatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import valorless.valorlessutils.ValorlessUtils.Log;
import valorless.valorlessutils.utils.Utils;

/**
 * Language and formatting utilities for RareSpawns.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Resolve PlaceholderAPI placeholders (if hooked)</li>
 *   <li>Apply legacy color codes (&amp; or §) and hex colors (#RRGGBB)</li>
 *   <li>Replace simple built-in placeholders like %player%</li>
 *   <li>Strip formatting when needed</li>
 * </ul>
 */
public class Lang {
	
	/**
	 * Parses a message by applying placeholders and color formatting.
	 * <p>
	 * Operations performed (in order):
	 * <ol>
	 *   <li>PlaceholderAPI resolution when available (using the provided player, or a fallback)</li>
	 *   <li>Convert hex colors of the form #RRGGBB to Minecraft color sequences</li>
	 *   <li>Translate legacy color codes (&amp; to §)</li>
	 *   <li>Unescape newlines ("\\n" to newline)</li>
	 *   <li>Replace %player% with the player's name when a player is provided</li>
	 * </ol>
	 * If player is null, an arbitrary offline player is used as a best-effort fallback for
	 * PlaceholderAPI parsing; if that fails, placeholders are resolved with null.
	 * </p>
	 *
	 * @param text   the raw message to parse; if null/empty, returns as-is
	 * @param player the player context for placeholder resolution; may be null
	 * @return the formatted and placeholder-resolved message
	 */
	public static String parse(String text, OfflinePlayer player) {
		if(!Utils.IsStringNullOrEmpty(text)) {
			if(player != null) {
				text = parsePlaceholderAPI(text, player);
			} else {
				OfflinePlayer[] offp = Bukkit.getOfflinePlayers();
				// Choose random player as placeholder to parse strings, without a defined player.
				try {
					text = parsePlaceholderAPI(text, offp[0]);
				}catch(Exception e) {
					text = parsePlaceholderAPI(text, null);
				}
			}
		
			text = hex(text);
			text = text.replace("&", "§");
			text = text.replace("\\n", "\n");
			if(player != null) {
				text = text.replace("%player%", player.getName());
			}
		}
		return text;
	}
	
	/**
	 * Converts hex color codes (#RRGGBB) into legacy color sequences understood by Bukkit,
	 * then applies {@link ChatColor#translateAlternateColorCodes(char, String)}.
	 *
	 * @param message the message possibly containing hex color codes
	 * @return the message with hex and legacy color codes translated
	 */
	public static String hex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
           
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }
           
            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
	
	/**
	 * Resolves PlaceholderAPI placeholders when the hook is available.
	 * <p>
	 * Curly braces are converted to percent tokens before delegation, e.g. {foo_bar}
	 * becomes %foo_bar%.
	 * </p>
	 *
	 * @param text   the text possibly containing PlaceholderAPI placeholders
	 * @param player the player context used for placeholder resolution; may be null
	 * @return the text with placeholders resolved, or the original text on failure/not hooked
	 */
	public static String parsePlaceholderAPI(String text, OfflinePlayer player) {
		try {
			String t = "";
			text = text.replace("{", "%");
			text = text.replace("}", "%");
			try {
				t =  PlaceholderAPI.setPlaceholders(player, text);  
        	}catch (Exception e) {
        		Log.Error(Main.plugin, "Failed to get PlaceholderAPI. Is it up to date?");
        		t = text;
        	}
			return t;
		}catch(Exception e) {
			return text;
		}
	}
	
	/**
	 * Removes all color and formatting codes, including hex sequences, from the text.
	 *
	 * @param text the text to sanitize
	 * @return the text without any § formatting codes
	 */
	public static String removeColorFormatting(String text) {
		if(!Utils.IsStringNullOrEmpty(text)) {
			text = text.replace("§§", "§");
			text = removeHex(text);
			text = text.replace("§0", "");
			text = text.replace("§1", "");
			text = text.replace("§2", "");
			text = text.replace("§3", "");
			text = text.replace("§4", "");
			text = text.replace("§5", "");
			text = text.replace("§6", "");
			text = text.replace("§7", "");
			text = text.replace("§8", "");
			text = text.replace("§9", "");
			text = text.replace("§0", "");
			text = text.replace("§a", "");
			text = text.replace("§b", "");
			text = text.replace("§c", "");
			text = text.replace("§d", "");
			text = text.replace("§e", "");
			text = text.replace("§f", "");
			text = text.replace("§o", "");
			text = text.replace("§l", "");
			text = text.replace("§k", "");
			text = text.replace("§m", "");
			text = text.replace("§n", "");
			text = text.replace("§r", "");
			text = text.replace("§A", "");
			text = text.replace("§B", "");
			text = text.replace("§C", "");
			text = text.replace("§D", "");
			text = text.replace("§E", "");
			text = text.replace("§F", "");
			text = text.replace("§x", "");
			text = text.replace("§", "");
		}
		return text;
	}
	
	/**
	 * Removes hex color sequences of the form §x§R§R§G§G§B§B from the text.
	 *
	 * @param text the text to sanitize
	 * @return the text without any §x hex color sequences
	 */
	public static String removeHex(String text) {
        // Regex to match the pattern of hex color codes
    	//Log.Info(Main.plugin, text);
        String hexColorRegex = "§x(§[0-9A-Fa-f]){6}";
        return text.replaceAll(hexColorRegex, "");
    }
}