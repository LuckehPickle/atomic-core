package net.atomichive.core.player;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Player manager.
 * Keeps track of all currently online players.
 */
public class PlayerManager {


    private static List<AtomicPlayer> players = new ArrayList<>();


    /**
     * Add player
     * @param player Bukkit player to add.
     * @return Bukkit player converted to Atomic player.
     */
    public static AtomicPlayer addPlayer (Player player) {

        // Convert to atomic player
        AtomicPlayer atomicPlayer = getOrCreate(player);

        // Add to player list
        addPlayer(atomicPlayer);

        // Return atomic player
        return atomicPlayer;

    }


    /**
     * Add player
     * @param player Atomic player to add.
     */
    public static void addPlayer (AtomicPlayer player) {
        players.add(player);
    }


    /**
     * Remove player
     * @param player Atomic player to remove.
     */
    public static void removePlayer (AtomicPlayer player) {
        players.remove(player);
    }


    /**
     * Remove all
     * Remove all players from the player manager.
     */
    public static void removeAll () {
        AtomicPlayerDAO.update(players);
        players.clear();
    }


    /**
     * Get or create
     * Returns a corresponding atomic player object, or
     * creates a new one if none exists.
     * @param player Bukkit player.
     * @return Atomic player.
     */
    public static AtomicPlayer getOrCreate (Player player) {

        // Iterate over all stored players.
        for (AtomicPlayer atomicPlayer : players) {
            if (atomicPlayer.is(player)) {
                return atomicPlayer;
            }
        }

        // Attempt to load player from db
        List<AtomicPlayer> players = AtomicPlayerDAO.findByIdentifier(player.getUniqueId());

        if (!players.isEmpty()) {
            // Return the first matching player.
            return players.get(0);
        }

        // Create new atomic player
        AtomicPlayer atomicPlayer = new AtomicPlayer(
                player.getUniqueId(),
                player.getName()
        );

        // Insert player
        AtomicPlayerDAO.insert(atomicPlayer);

        return atomicPlayer;

    }

}
