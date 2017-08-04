package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.util.BroadcastTask;
import net.atomichive.core.util.Util;
import net.atomichive.core.player.AtomicPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.logging.Level;

/**
 * Login listener
 * Handles player login events.
 * TODO: Clean up this mess...
 */
public final class LoginListener extends BaseListener implements Listener {


    /**
     * On login
     * An event which occurs whenever a player logs in.
     * @param event Player login event object.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin (PlayerLoginEvent event) {

        // Get atomic player and bukkit player
        Player bukkitPlayer = event.getPlayer();
        AtomicPlayer atomicPlayer = Main.getInstance().getPlayerManager().addPlayer(bukkitPlayer);

        // Update most recent alias
        atomicPlayer.setUsername(bukkitPlayer.getName());

        // Increment login count and update username
        atomicPlayer.incrementLoginCount();

        // Set display name
        bukkitPlayer.setPlayerListName(atomicPlayer.getDisplayName());
        bukkitPlayer.setDisplayName(atomicPlayer.getDisplayName());

        // Log player join
        Main.getInstance().getLogger().log(Level.INFO, String.format(
                "%s has logged in %d times. Last seen %s",
                atomicPlayer.getUsername(),
                atomicPlayer.getLoginCount(),
                atomicPlayer.getLastSeen()
        ));

        // Update last seen time
        atomicPlayer.setLastSeen(Util.getCurrentTimestamp());

        // Test if the player is new
        if (atomicPlayer.getLoginCount() <= 1) {

            // Broadcast first join message
            new BroadcastTask(String.format(
                    "Welcome to the server %s%s%s!",
                    ChatColor.YELLOW,
                    bukkitPlayer.getDisplayName(),
                    ChatColor.RESET
            )).runTaskLater(Main.getInstance(), 20);

        }

    }

}



