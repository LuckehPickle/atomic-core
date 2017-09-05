package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.util.Util;
import net.atomichive.core.util.tasks.BroadcastTask;
import net.atomichive.core.util.tasks.LoginTask;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.logging.Level;

/**
 * Handles player login events.
 */
public final class LoginListener extends BaseListener implements Listener {


    /**
     * An event which occurs whenever a player logs in.
     *
     * @param event Player login event object.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    void onLogin (PlayerLoginEvent event) {

        // Get atomic player and bukkit player
        Player player = event.getPlayer();
        AtomicPlayer atomic = Main.getInstance().getPlayerManager().addPlayer(player);

        handleUsername(atomic, player);

        atomic.incrementLoginCount();

        player.setCollidable(false);

        // Set experience
        atomic.updateExperience();
        player.setLevel(atomic.getLevel());
        player.setExp(atomic.getExperienceFloat());

        // Log player join
        Main.getInstance().getLogger().log(Level.INFO, String.format(
                "%s has logged in %d times. Last seen %s",
                atomic.getUsername(),
                atomic.getLoginCount(),
                atomic.getLastSeen()
        ));

        // Update last seen time
        atomic.setLastSeen(Util.getCurrentTimestamp());

        // Test if the player is new
        if (atomic.getLoginCount() <= 1) {

            // Broadcast first join message
            new BroadcastTask(String.format(
                    "Welcome to the server %s%s%s!",
                    ChatColor.YELLOW,
                    player.getDisplayName(),
                    ChatColor.RESET
            )).runTaskLater(Main.getInstance(), 20);

        }

        new LoginTask(player).runTaskLater(Main.getInstance(), 20);

    }


    /**
     * Updates the bukkit players custom display name,
     * and updates the last known alias in case it has changed.
     *
     * @param atomic Atomic player.
     * @param player Bukkit player.
     */
    private void handleUsername (AtomicPlayer atomic, Player player) {

        atomic.setUsername(player.getName());

        player.setPlayerListName(atomic.getDisplayName());
        player.setDisplayName(atomic.getDisplayName());

    }

}



