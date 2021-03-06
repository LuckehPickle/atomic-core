package net.atomichive.core.entity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.MalformedJsonException;
import net.atomichive.core.JsonManager;
import net.atomichive.core.exception.CustomObjectException;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which tracks all custom entities, as well as
 * currently active entities.
 */
public class EntityManager extends JsonManager {

    private List<CustomEntity> customEntities = new ArrayList<>();
    private List<ActiveEntity> activeEntities = new ArrayList<>();


    /**
     * Constructor
     *
     * @param resource Path to JSON file.
     */
    public EntityManager (String resource) {
        super(resource, "entity", "entities");
    }


    /**
     * Attempts to reload entities from entities.json.
     *
     * @return Number of elements loaded.
     */
    @Override
    public int load () throws MalformedJsonException {
        customEntities.clear();
        return super.load();
    }


    /**
     * Converts a JsonElement to a custom entity.
     *
     * @param element A JsonElement.
     * @return A string representation of the element.
     */
    @Override
    protected String loadElement (JsonElement element)
            throws CustomObjectException {

        // Get entity from json
        Gson gson = new Gson();
        CustomEntity entity = gson.fromJson(element, CustomEntity.class);

        // Ensure entity has not already been defined.
        if (contains(entity)) {
            throw new CustomObjectException(String.format(
                    "An entity named '%s' already exists.",
                    entity.getName()
            ));
        }

        customEntities.add(entity);

        return entity.toString();

    }


    /**
     * Adds an active entity to the living entities array.
     *
     * @param entity Active entity to add.
     */
    public void add (ActiveEntity entity) {
        activeEntities.add(entity);
    }


    /**
     * Removes a Bukkit entity from the living entities array.
     *
     * @param entity Bukkit entity to remove.
     */
    public void remove (Entity entity) {
        remove(getActiveEntity(entity));
    }


    /**
     * Removes an active entity from the living entities array.
     *
     * @param entity Active entity to remove.
     */
    public void remove (ActiveEntity entity) {
        activeEntities.remove(entity);
    }


    /**
     * A simple search through existing custom entities
     * which compares the unique names.
     *
     * @param entity Entity to search for.
     * @return Whether this custom entity is already defined.
     */
    public boolean contains (CustomEntity entity) {

        // Iterate over custom entities
        for (CustomEntity e : customEntities) {
            if (e.getName().equalsIgnoreCase(entity.getName()))
                return true;
        }

        return false;

    }


    /**
     * Attempts to find a corresponding active entity.
     *
     * @param entity Bukkit entity.
     * @return Corresponding active entity or null.
     */
    public ActiveEntity getActiveEntity (Entity entity) {

        // Iterate over active entities
        for (ActiveEntity active : activeEntities) {
            if (active.is(entity))
                return active;
        }

        return null;

    }


    /**
     * Spawns a new custom entity
     *
     * @param location   Desired spawn location.
     * @param entityName Name of custom entity to spawn.
     * @param count      Number of entities to spawn.
     * @param owner      Owner of spawned entities. Can be null.
     */
    public void spawnEntity (Location location, String entityName, int count, Entity owner)
            throws CustomObjectException {

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
            throw new CustomObjectException(String.format(
                    "Entity '%s' could not be found.",
                    entityName
            ));
        }

        for (int i = 0; i < count; i++) {
            add(customEntity.spawn(location, owner));
        }

    }


    /*
        Getters and setters.
     */

    public List<CustomEntity> getCustomEntities () {
        return customEntities;
    }

    public List<ActiveEntity> getActiveEntities () {
        return activeEntities;
    }

}
