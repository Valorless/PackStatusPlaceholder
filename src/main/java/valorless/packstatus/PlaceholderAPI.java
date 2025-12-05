package valorless.packstatus;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import valorless.valorlessutils.ValorlessUtils.Log;

public class PlaceholderAPI extends PlaceholderExpansion{
	
	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public String getAuthor() {
		return Main.plugin.getDescription().getAuthors().toString();
	}

	@Override
	public String getIdentifier() {
		return "pack";
	}

	@Override
	public String getVersion() {
		return Main.plugin.getDescription().getVersion();
	}

	/**
	 * This is the method called when a placeholder with our identifier is found and
	 * needs a value. <br>
	 * We specify the value identifier in this method. <br>
	 * Since version 2.9.1 can you use OfflinePlayers in your requests.
	 *
	 * @param player     An {@link org.bukkit.OfflinePlayer OfflinePlayer}.
	 * @param identifier A String containing the identifier/value.
	 *
	 * @return possibly-null String of the requested identifier.
	 */
	@Override
	public String onRequest(OfflinePlayer player, @NotNull String identifier) {
		if(identifier.equalsIgnoreCase("status")) {
			return "" + PackListener.isLoaded.getOrDefault(player.getPlayer(), false);
		}

		// We return null if an invalid placeholder (f.e. %someplugin_placeholder3%)
		// was provided
		return null;
	}

}
