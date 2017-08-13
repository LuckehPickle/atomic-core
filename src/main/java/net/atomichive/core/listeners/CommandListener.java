package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.player.AtomicPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Command Listener
 * An event which fires whenever a player sends a command.
 */
public class CommandListener extends BaseListener implements Listener {


    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent event) {

        // Get player and command
        Player sender = event.getPlayer();
        String command = event.getMessage();

        // Alert users with appropriate perms
        for (AtomicPlayer player : Main.getInstance().getPlayerManager().getAll()) {

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
