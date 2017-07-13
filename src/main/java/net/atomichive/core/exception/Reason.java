package net.atomichive.core.exception;

import org.bukkit.ChatColor;

/**
 * Exception Reason
 * Reasons used to alter an exception
 */
public enum Reason {

    ERROR          ("Error: %s"),
    INVALID_USAGE  ("Usage: %s"),
    INVALID_SENDER ("Invalid sender: %s"),
    UNKNOWN_ENTITY ("Entity error: %s"),
    UNKNOWN_CLASS  ("Entity error: %s");

    private final String format;

    Reason (String format) {
        this.format = format;
    }

    public String getFormat () {
        return ChatColor.RED + format;
    }

}
