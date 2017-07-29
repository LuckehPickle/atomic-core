package net.atomichive.core.entity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;
import net.atomichive.core.JsonManager;
import net.atomichive.core.Main;
import net.atomichive.core.exception.AtomicEntityException;
import net.atomichive.core.exception.Reason;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Entity Manager
 * A class which tracks all custom entities, as well as
 * currently active entities.
 */
public class EntityManager extends JsonManager {


    // All custom entity definitions
    private static List<CustomEntity> customEntities = new ArrayList<>();

    // All currently active custom entities
    private static List<ActiveEntity> livingEntities = new ArrayList<>();


    /**
     * Add
     * Adds an active entity to the living entities array.
     * @param entity Active entity to add.
     */
    public static void add (ActiveEntity entity) {
        livingEntities.add(entity);
    }


    /**
     * Get active entity
     * Attempts to find a corresponding active entity.
     * @param entity Bukkit entity.
     * @return Corresponding active entity or null.
     */
    public static ActiveEntity getActiveEntity (Entity entity) {

        // Iterate over active entities
        for (ActiveEntity active : livingEntities) {
            if (active.is(entity))
                return active;
        }

        return null;

    }


    /**
     * Load
     * Loads entities from entities.json
     * @return Number of entities loaded.
     * @throws MalformedJsonException if the JSON if malformed.
     */
    public static int load () throws MalformedJsonException {

        // Load file
        load("entities.json");

        // Attempt to load entities
        try {
            return loadCustomEntities(new FileReader(getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }


    /**
     * Load custom entities
     * Loads an instance of all custom entities.
     * @param json JSON entities reader.
     */
    public static int loadCustomEntities (FileReader json)
            throws MalformedJsonException {

        // Parse JSON
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(json).getAsJsonArray();

        Main.getInstance().log(Level.INFO, "Found " + array.size() + " custom entities.");
        customEntities.clear();

        // Iterate over added entities
        for (int i = 0; i < array.size(); i++) {

            // Get entity from JSON
            CustomEntity entity = gson.fromJson(array.get(i), CustomEntity.class);
            entity.init();

            // Ensure entity is unique
            if (customEntities.contains(entity)) {
                Main.getInstance().log(
                        Level.WARNING,
                        "Entity is already defined with the name " + entity.getName() + ". Ignoring."
                );
                continue;
            }

            // Add entity to list
            Main.getInstance().log(Level.INFO, "Loaded " + entity.getName());
            customEntities.add(entity);

        }

        return array.size();

    }


    /**
     * Spawn entity
     * Spawns a new custom entity
     * @param location Desired spawn location.
     * @param entityName Name of custom entity to spawn.
     * @param count Number of entities to spawn.
     * @throws AtomicEntityException if an exception is encountered. These
     *                         are safe to relay to players.
     */
    public static void spawnEntity (Location location, String entityName, int count)
            throws AtomicEntityException {
        spawnEntity(location, entityName, count, null);
    }


    /**
     * Spawn entity
     * Spawns a new custom entity
     * @param location Desired spawn location.
     * @param entityName Name of custom entity to spawn.
     * @param count Number of entities to spawn.
     * @param owner Owner of spawned entities. Can be null.
     * @throws AtomicEntityException if an exception is encountered. These
     *                         are safe to relay to players.
     */
    public static void spawnEntity (Location location, String entityName, int count, Entity owner)
            throws AtomicEntityException {

        CustomEntity customEntity = null;

        // Determine which entity needs to be spawned
        for (CustomEntity e : customEntities) {
            if (e.getName().equalsIgnoreCase(entityName)) {
                customEntity = e;
                break;
            }
        }

        // Ensure entity was found
        if (customEntity == null) {
            throw new AtomicEntityException(
                    Reason.ENTITY_ERROR,
                    "Entity '" + entityName + "' could not be found."
            );
        }

        for (int i = 0; i < count; i++)
            add(customEntity.spawn(location, owner));

    }

    /*
        Getters and setters.
     */


    public static List<CustomEntity> getAll () {
        return customEntities;
    }

    public static List<ActiveEntity> getActiveEntities () {
        return livingEntities;
    }

}
