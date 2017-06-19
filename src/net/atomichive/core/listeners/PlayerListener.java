package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.Utils;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.player.AtomicPlayerDAO;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
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
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin (PlayerLoginEvent event) {

		// Get the atomic player
		Player player = event.getPlayer();
		AtomicPlayer atomicPlayer = getOrCreatePlayer(player);

		// Update most recent alias
		atomicPlayer.setUsername(player.getName());

		// Increment count and update username
		atomicPlayer.incrementLoginCount();

		// Log player join
		Main.getInstance().getLogger().log(Level.INFO, String.format(
				"%s has logged in %d times. Last seen %s",
				atomicPlayer.getUsername(),
				atomicPlayer.getLoginCount(),
				atomicPlayer.getLastSeen()
		));

		// Update last seen time
		atomicPlayer.setLastSeen(Utils.getCurrentTimestamp());
		AtomicPlayerDAO.updateAtomicPlayer(atomicPlayer);


		// Test if the player is new
		if (atomicPlayer.getLoginCount() <= 1) {

			String message = String.format(
					"Welcome to the server %s%s%s!",
					ChatColor.YELLOW,
					player.getDisplayName(),
					ChatColor.RESET
			);

			// Broadcast first join message
			Bukkit.broadcastMessage(message);
			player.sendMessage(message);

		}

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
