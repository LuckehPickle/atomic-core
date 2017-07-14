package net.atomichive.core.util;

import net.atomichive.core.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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


    /**
     * Export resource
     * @param resource Path to resource
     */
    public static void exportResource (String resource) throws Exception {

        // Target destination
        String target = Main.getInstance().getDataFolder().getPath();

        try (
                InputStream input = Utils.class.getResourceAsStream("/" + resource);
                OutputStream output = new FileOutputStream(target + File.separator + resource)
        ) {
            // Ensure stream is not null
            if (input == null)
                throw new Exception("Cannot get resource '" + resource + "' from jar.");

            // Copy resource contents
            int readBytes;
            byte[] buffer = new byte[4096];
            while ((readBytes = input.read(buffer)) > 0) {
                output.write(buffer, 0, readBytes);
            }
        }

    }


}
