package net.atomichive.core.util;

import net.atomichive.core.exception.CommandException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Command Util
 * Various utilities for use within commands.
 */
public class CommandUtil {


    /**
     * Parse target
     *
     * @param arg Target player's name.
     * @return Bukkit player.
     */
    public static Player parseTarget (String arg) throws CommandException {

        // Get player
        Player target = Bukkit.getPlayer(arg);

        // Ensure player exists
        if (target == null)
            throw new CommandException("The player " + arg + " could not be found.");

        return target;

    }


    /**
     * Handle color codes
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
