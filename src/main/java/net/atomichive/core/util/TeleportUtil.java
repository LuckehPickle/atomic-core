package net.atomichive.core.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Teleport Util
 * A utility for teleporting players and sending
 * them appropriate contextual information.
 */
public class TeleportUtil {


    /**
     * Teleport
     *
     * @param source      Player being teleported.
     * @param destination Player to teleport to.
     */
    public static void teleport (Player source, Player destination) {
        teleport(source, destination, false);
    }


    /**
     * Teleport
     *
     * @param source      Player being teleported.
     * @param destination Player to teleport to.
     * @param silent      Whether to make the teleport silent.
     */
    public static void teleport (Player source, Player destination, boolean silent) {

        source.teleport(destination);

        // Send contextual information
        source.sendMessage(String.format(
                !silent ? "Teleported to %s." : "Silently teleported to %s.",
                ChatColor.YELLOW + destination.getDisplayName() + ChatColor.RESET
        ));

        // Only message destination of the teleport is not silent.
        if (!silent) {
            destination.sendMessage(String.format(
                    "%s teleported to you.",
                    ChatColor.YELLOW + source.getDisplayName() + ChatColor.RESET
            ));
        }

    }


    /**
     * Teleport Here
     *
     * @param source      Player to teleport to.
     * @param destination Player being teleported.
     */
    public static void teleportHere (Player source, Player destination) {

        source.teleport(destination);

        // Send contextual information
        source.sendMessage(String.format(
                "You've been teleported to %s.",
                ChatColor.YELLOW + destination.getDisplayName() + ChatColor.RESET
        ));

        destination.sendMessage(String.format(
                "You teleported %s to you.",
                ChatColor.YELLOW + source.getDisplayName() + ChatColor.RESET
        ));

    }


}
