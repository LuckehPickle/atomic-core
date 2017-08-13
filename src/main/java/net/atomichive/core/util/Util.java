package net.atomichive.core.util;

import net.atomichive.core.Main;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

/**
 * Util
 * A collection of utility functions.
 */
public class Util {


    private static final int MAX_TRACE_DISTANCE = 200;


    /**
     * Get current timestamp
     * Returns a timestamp object representing the current
     * time.
     *
     * @return The current timestamp.
     */
    public static Timestamp getCurrentTimestamp () {
        return new Timestamp(System.currentTimeMillis());
    }


    /**
     * Export resource
     *
     * @param resource Path to resource
     */
    public static void exportResource (String resource) throws Exception {

        // Target destination
        String target = Main.getInstance().getDataFolder().getPath();

        try (
                InputStream input = Util.class.getResourceAsStream("/" + resource);
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
     *
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
     *
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
     *
     * @param args String array of arguments.
     * @return Joined args.
     */
    public static String argsJoiner (String[] args) {
        return argsJoiner(args, 0, -1);
    }

    /**
     * Args joiner
     * Joins arguments together by spaces.
     *
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
     *
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
     *
     * @param min Minimum value.
     * @param max Maximum value.
     * @return Random integer within specified range.
     */
    public static int getRandomInt (int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }


    /**
     * Get random dye color
     *
     * @return Returns a random dye color.
     */
    public static DyeColor getRandomDyeColor () {

        final List<DyeColor> values = Arrays.asList(DyeColor.values());

        return values.get(new Random().nextInt(values.size()));

    }


    /**
     * Get enum value
     *
     * @param e     Enum class.
     * @param value Enum constant as a string.
     * @param <T>   An enum/enum constant.
     * @return Enum constant from string.
     */
    public static <T> T getEnumValue (Class<T> e, String value) {

        // Get enum constants
        T[] constants = e.getEnumConstants();

        // Iterate over constants
        for (T constant : constants) {
            if (constant.toString().equalsIgnoreCase(value))
                return constant;
        }

        return null;

    }


    /**
     * Trace
     * Perform a ray trace.
     *
     * @param player Location to ray trace from.
     * @return First location above the target block
     * that is not air.
     */
    public static Location trace (Player player) {

        // New block iterator
        BlockIterator iterator = new BlockIterator(player, MAX_TRACE_DISTANCE);
        Location location = null;

        while (iterator.hasNext()) {
            // Get next block in iterator
            Block block = iterator.next();

            // Test if block is empty
            if (!block.isEmpty()) {

                while (true) {
                    // Get block above
                    block = block.getRelative(0, 1, 0);


                    // Check if block is air
                    if (block.isEmpty()) {
                        location = block.getLocation();
                        break;
                    }
                }

                location.add(new Vector(0.5, 0, 0.5));
                break;

            }
        }

        return location;

    }


    /**
     * Format location
     *
     * @param location Location to format.
     * @return Formatted location.
     */
    public static String formatLocation (Location location) {
        return String.format(
                "%d, %d, %d",
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );
    }


    /**
     * Is integer
     * https://stackoverflow.com/a/237204
     *
     * @param str String to analyse.
     * @return Whether the string can be parsed as an integer.
     */
    public static boolean isInteger (String str) {

        if (str == null)
            return false;

        int length = str.length();

        if (length == 0)
            return false;

        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1)
                return false;
            i = 1;
        }

        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9')
                return false;
        }

        return true;

    }


    /**
     * Get closest
     *
     * @param entities Collection of entities.
     * @return Closest entity.
     */
    public static List<Entity> getClosest (Location location, List<Entity> entities) {

        double lowestDistance = -1;
        Entity closest = null;

        // Iterate over entities
        for (Entity entity : entities) {
            double dist = location.distanceSquared(entity.getLocation());
            if (lowestDistance == -1 || dist < lowestDistance) {
                lowestDistance = dist;
                closest = entity;
            }
        }

        entities.clear();

        // Add closest, if its not null
        if (closest != null)
            entities.add(closest);

        return entities;

    }


    /**
     * Get delta v
     *
     * @param source Source entity.
     * @param target Target entity.
     * @return Difference in location as a vector.
     */
    public static Vector getDeltaV (Entity source, Entity target) {
        return target.getLocation()
                .toVector()
                .subtract(source.getLocation().toVector());
    }
}
