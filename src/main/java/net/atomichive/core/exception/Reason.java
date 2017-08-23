package net.atomichive.core.exception;

/**
 * Reasons used to provide more context in an exception.
 */
public enum Reason {


    GENERIC_ERROR            ("Error: %s"),
    INVALID_USAGE            ("Usage: %s"),
    INVALID_SENDER           ("Invalid sender: %s"),
    INVALID_NUMBER           ("Error: %s"),
    INSUFFICIENT_PERMISSIONS ("%s"),
    UNKNOWN_PLAYER           ("Unknown player: %s"),
    UNKNOWN_GAMEMODE         ("Unknown gamemode: %s");


    private final String format;


    /**
     * Constructs a new reason
     *
     * @param format Output format.
     */
    Reason (String format) {
        this.format = format;
    }

    public String getFormat () {
        return format;
    }

}
