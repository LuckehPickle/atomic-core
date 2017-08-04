package net.atomichive.core.util;

import net.atomichive.core.Main;

import java.util.Map;
import java.util.logging.Level;

/**
 * Smart Map
 * A wrapper for Java maps which lets you safely check
 * for a particular type.
 */
public class SmartMap {

    private Map map;


    /**
     * Smart Map
     * @param map Java map to wrap.
     */
    public SmartMap (Map map) {
        this.map = map;
    }


    /**
     * Get integer
     * Use this method to retrieve Integers.
     * This is needed because JSON automatically
     * treats all numbers as doubles/floats.
     * @param key Attribute key
     * @param defaultValue Default value.
     * @return Retrieved integer or default value.
     */
    public int getInteger (String key, int defaultValue) {
        return get(Double.class, key, (double) defaultValue).intValue();
    }


    /**
     * Get
     * @param clazz Class of object to return
     * @param key Attribute key
     * @return Retrieved object or default value.
     */
    public <T> T get (Class<T> clazz, String key) {
        return get(clazz, key, null);
    }


    /**
     * Get
     * @param clazz Class of object to return
     * @param key Attribute key
     * @param defaultValue Default value
     * @param <T> Type of object
     * @return Retrieved object or default value.
     */
    public <T> T get (Class<T> clazz, String key, T defaultValue) {

        // Ensure map isn't null
        if (map == null)
            return defaultValue;

        // Get object
        Object object = map.get(key);

        if (clazz.isInstance(object))
            return clazz.cast(object);

        if (object != null) {
            Main.getInstance().log(
                    Level.WARNING,
                    "Attribute with key: '" + key + "' could not be cast to " + clazz.getSimpleName() + "."
            );
        }

        return defaultValue;

    }


    /**
     * Log
     * Logs all attributes. For debug purposes.
     */
    public void log () {
        Main.getInstance().log(Level.INFO, map.toString());
    }

}
