package net.atomichive.core.listeners;

import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Command Listener
 */
public class CommandListener extends BaseListener implements Listener {


    /**
     * On command
     */
    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent event) {

        // Get player and command
        Player sender = event.getPlayer();
        String command = event.getMessage();

        // Alert users with appropriate perms
        for (AtomicPlayer player : PlayerManager.getAll()) {

            // Ensure player is not sender
            if (player.is(sender))
                continue;

            // Whether to send the message or not
            boolean send = player.getVerbosity() == 1 && !sender.hasPermission("atomic-core.staff");
            send = send || player.getVerbosity() == 2;

            if (send) {
                Player p = Bukkit.getPlayer(player.getIdentifier());
                p.sendMessage(String.format(
                        "%s: %s",
                        ChatColor.GRAY + sender.getDisplayName(),
                        command
                ));
            }
        }

    }

}
