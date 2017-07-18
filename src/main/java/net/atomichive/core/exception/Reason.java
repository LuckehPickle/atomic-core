package net.atomichive.core.exception;

import org.bukkit.ChatColor;

/**
 * Exception Reason
 * Reasons used to alter an exception
 */
public enum Reason {

    ERROR          ("Error: %s"),
    ENTITY_ERROR   ("Entity error: %s"),
    INVALID_USAGE  ("Usage: %s"),
    INVALID_SENDER ("Invalid sender: %s"),
    INVALID_INPUT  ("Invalid input: %s"),
    UNKNOWN_CLASS  ("Unknown class: %s"),
    WARP_FAILED    ("Warp failed: %s");

    private final String format;

    Reason (String format) {
        this.format = format;
    }

    public String getFormat () {
        return ChatColor.RED + format;
    }

}
