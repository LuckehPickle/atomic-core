package net.atomichive.core.util;

import net.atomichive.core.exception.UnknownPlayerException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Various utilities for use within commands.
 */
public class CommandUtil {


    /**
     * Searches for a target player.
     *
     * @param arg Target player's name.
     * @return Bukkit player.
     * @throws UnknownPlayerException if the target is not found.
     */
    public static Player parseTarget (String arg) throws UnknownPlayerException {

        // Get player
        Player target = Bukkit.getPlayer(arg);

        // Ensure player exists
        if (target == null) {
            throw new UnknownPlayerException(arg);
        }

        return target;

    }


    /**
     * Replaces color codes such as &c with their
     * internal counterpart.
     *
     * @param message Message to format.
     * @return Formatted message.
     */
    public static String handleColorCodes (String message) {
        return message.replaceAll("(?i)&([a-f0-9])", "\u00A7$1");
    }

}
