package net.atomichive.core.util;

import net.atomichive.core.Main;
import org.bukkit.DyeColor;
import org.bukkit.entity.Villager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.StringJoiner;

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


    /**
     * To title case
     * Converts an underscored string to title case.
     * E.g. custom_zombie -> Custom Zombie
     * @param str Underscored string
     * @return Title cased string
     */
    public static String toTitleCase (String str) {

        // Split at underscores and down case
        String[] parts = str.toLowerCase().split("_");

        StringJoiner joiner = new StringJoiner(" ");

        // Flag which marks whether this is the first part
        boolean first = true;

        // Add all parts with length >= 1
        for (String part : parts) {
            if (part.length() > 1) {
                // Uppercase first character
                joiner.add(part.substring(0, 1).toUpperCase() + part.substring(1));
            } else if (part.length() == 1) {
                if (first) joiner.add(part.toUpperCase());
                else joiner.add(part);
            }

            first = false;
        }

        return joiner.toString();

    }


    /**
     * To camel case
     * Converts an underscored string to camel case.
     * E.g. custom_zombie -> customZombie.
     * @param capitaliseFirst Should the first letter be uppercase?
     * @param str             Underscored string to convert.
     * @return Camel case version of string.
     */
    @SuppressWarnings("SameParameterValue")
    public static String toCamelCase (boolean capitaliseFirst, String str) {

        // Down case and split at underscores
        String[] parts = str.toLowerCase().split("_");

        StringJoiner joiner = new StringJoiner("");

        // Iterate over parts
        for (int i = 0; i < parts.length; i++) {

            String part = parts[i];

            if (i == 0 && !capitaliseFirst)
                joiner.add(part);
            else
                joiner.add(part.substring(0, 1).toUpperCase() + part.substring(1));

        }

        return joiner.toString();

    }


    /**
     * Args joiner
     * Joins arguments together by spaces.
     * @param args       String array of arguments.
     * @return Joined args.
     */
    public static String argsJoiner (String[] args) {
        return argsJoiner(args, 0, -1);
    }

    /**
     * Args joiner
     * Joins arguments together by spaces.
     * @param args       String array of arguments.
     * @param startIndex Index to start at.
     * @return Joined args.
     */
    public static String argsJoiner (String[] args, int startIndex) {
        return argsJoiner(args, startIndex, -1);
    }

    /**
     * Args joiner
     * Joins arguments together by spaces.
     * @param args       String array of arguments.
     * @param startIndex Index to start at.
     * @param endIndex   Index to end at.
     * @return Joined args.
     */
    public static String argsJoiner (String[] args, int startIndex, int endIndex) {

        // Create a new string joiner
        StringJoiner joiner = new StringJoiner(" ");

        for (int i = startIndex; i < args.length; i++) {

            // Break if i is higher than end index
            if (i > endIndex && endIndex != -1) break;

            joiner.add(args[i]);
        }

        return joiner.toString();

    }


    /**
     * Get random int
     * @param min Minimum value.
     * @param max Maximum value.
     * @return Random integer within specified range.
     */
    public static int getRandomInt (int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }



    /**
     * Get dye color
     * @param color Name of dye color to retrieve.
     * @return COrresponding dye color enum value.
     */
    public static DyeColor getDyeColor (String color) {
        try {
            return DyeColor.valueOf(color);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }



    /**
     * Get villager profession
     * @param profession Name of profession.
     * @return Profession enum val or null.
     */
    public static Villager.Profession getVillagerProfession (String profession) {
        try {
            return Villager.Profession.valueOf(profession);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
