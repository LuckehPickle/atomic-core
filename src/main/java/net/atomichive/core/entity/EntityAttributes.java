package net.atomichive.core.entity;

import net.atomichive.core.Main;

import java.util.Map;
import java.util.logging.Level;

/**
 * Entity Config
 * Keeps track of various configuration options
 * for entities.
 */
public class EntityAttributes {

    private Map<String, Object> attributes;

    public EntityAttributes (Map<String, Object> attributes) {
        this.attributes = attributes;
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

        // Ensure attributes aren't null
        if (attributes == null)
            return defaultValue;

        // Get object
        Object object = attributes.getOrDefault(key, defaultValue);

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
        Main.getInstance().log(Level.INFO, attributes.toString());
    }

}
