package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.Utils;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.player.PlayerManager;
import org.bukkit.Bukkit;
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
        AtomicPlayer atomicPlayer = PlayerManager.addPlayer(bukkitPlayer);

        // Update most recent alias
        atomicPlayer.setUsername(bukkitPlayer.getName());

        // Increment login count and update username
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

        // Test if the player is new
        if (atomicPlayer.getLoginCount() <= 1) {

            String message = String.format(
                    "Welcome to the server %s%s%s!",
                    ChatColor.YELLOW,
                    bukkitPlayer.getDisplayName(),
                    ChatColor.RESET
            );

            // Broadcast first join message
            Bukkit.broadcastMessage(message);
            bukkitPlayer.sendMessage(message);

        }

    }

}
