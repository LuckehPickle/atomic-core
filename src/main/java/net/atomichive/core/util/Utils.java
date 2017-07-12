package net.atomichive.core.util;

import java.sql.Timestamp;

/**
 * Utils
 * A collection of utility functions.
 */
public class Utils {


    /**
     * Get current timestamp
     * Returns a timestamp object representing the current
     * time.
     * @return The current timestamp.
     */
    public static Timestamp getCurrentTimestamp () {
        return new Timestamp(System.currentTimeMillis());
    }

}
