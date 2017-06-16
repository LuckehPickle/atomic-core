package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.Utils;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.player.AtomicPlayerDAO;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Player Listener
 * Handles various player related events.
 */
public final class PlayerListener extends BaseListener implements Listener {

	public PlayerListener () {
		super();
	}


	/**
	 * On login
	 * An event which occurs whenever a player logs in.
	 * @param event Player login event object.
	 */
	@EventHandler
	public void onLogin (PlayerLoginEvent event) {

		AtomicPlayer player = getOrCreatePlayer(event.getPlayer());

		// Increment count and update username
		player.incrementLoginCount();
		player.setUsername(event.getPlayer().getName());

		// Log player join
		Main.getInstance().getLogger().log(Level.INFO, String.format(
				"%s has logged in %d times. Last seen %s",
				player.getUsername(),
				player.getLogins(),
				player.getLastSeen()
		));

		// Update last seen time
		player.setLastSeen(Utils.getCurrentTimestamp());
		AtomicPlayerDAO.updateAtomicPlayer(player);

	}



	/**
	 * On quit event
	 * An event which occurs whenever a player logs out.
	 * @param event Player quit event object.
	 */
	@EventHandler
	public void onQuit (PlayerQuitEvent event) {

		// Get player
		AtomicPlayer player = getOrCreatePlayer(event.getPlayer());

		// Update last seen value
		player.setLastSeen(Utils.getCurrentTimestamp());
		AtomicPlayerDAO.updateAtomicPlayer(player);

	}



	/**
	 * Get or create player
	 * Retrieves a player from the database. If they have
	 * not been saved yet, a new Atomic Player is created.
	 * @param player Player to get or create.
	 * @return Retrieved or created player.
	 */
	private AtomicPlayer getOrCreatePlayer (Player player) {

		// Load matching players from db
		List<AtomicPlayer> players;
		players = AtomicPlayerDAO.findByIdentifier(player.getUniqueId());

		if (players.isEmpty()) {

			// Broadcast first join message
			Bukkit.broadcastMessage(String.format(
					"Welcome to server %s%s%s!",
					ChatColor.YELLOW,
					player.getDisplayName(),
					ChatColor.RESET
			));

			// Create a new atomic player
			AtomicPlayer atomicPlayer = new AtomicPlayer(
					player.getUniqueId(),
					player.getName()
			);

			// Insert atomic player
			AtomicPlayerDAO.insertAtomicPlayer(atomicPlayer);

			return atomicPlayer;

		} else {
			return players.get(0);
		}
	}

}
