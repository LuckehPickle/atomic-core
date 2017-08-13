package net.atomichive.core.entity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.atomichive.core.JsonManager;
import net.atomichive.core.exception.AtomicEntityException;
import net.atomichive.core.exception.ElementAlreadyExistsException;
import net.atomichive.core.exception.Reason;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity Manager
 * A class which tracks all custom entities, as well as
 * currently active entities.
 */
public class EntityManager extends JsonManager {


    // All custom entity definitions
    private List<CustomEntity> customEntities = new ArrayList<>();

    // All currently active custom entities
    private List<ActiveEntity> livingEntities = new ArrayList<>();


    /**
     * Entity Manager
     *
     * @param resource Path to JSON file.
     */
    public EntityManager (String resource) {
        super(resource, "entity", "entities");
    }


    /**
     * Add
     * Adds an active entity to the living entities array.
     *
     * @param entity Active entity to add.
     */
    public void add (ActiveEntity entity) {
        livingEntities.add(entity);
    }


    /**
     * Remove
     * Removes a Bukkit entity from the living entities array.
     *
     * @param entity Bukkit entity to remove.
     */
    public void remove (Entity entity) {
        remove(getActiveEntity(entity));
    }


    /**
     * Remove
     * Removes an active entity from the living entities array.
     *
     * @param entity Active entity to remove.
     */
    public void remove (ActiveEntity entity) {
        livingEntities.remove(entity);
    }


    /**
     * Contains
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
     * Get active entity
     * Attempts to find a corresponding active entity.
     *
     * @param entity Bukkit entity.
     * @return Corresponding active entity or null.
     */
    public ActiveEntity getActiveEntity (Entity entity) {

        // Iterate over active entities
        for (ActiveEntity active : livingEntities) {
            if (active.is(entity))
                return active;
        }

        return null;

    }


    /**
     * Load element
     * Loads a particular element as defined
     * by the extending class.
     *
     * @param element A JsonElement.
     * @return A string representation of the element.
     */
    @Override
    protected String loadElement (JsonElement element)
            throws ElementAlreadyExistsException {

        // Get entity from json
        Gson gson = new Gson();
        CustomEntity entity = gson.fromJson(element, CustomEntity.class);
        entity.init();

        // Ensure entity has not already been defined.
        if (contains(entity))
            throw new ElementAlreadyExistsException(entity.getName());

        customEntities.add(entity);

        return entity.toString();

    }


    /**
     * Spawn entity
     * Spawns a new custom entity
     *
     * @param location   Desired spawn location.
     * @param entityName Name of custom entity to spawn.
     * @param count      Number of entities to spawn.
     * @throws AtomicEntityException if an exception is encountered. These
     *                               are safe to relay to players.
     */
    public void spawnEntity (Location location, String entityName, int count)
            throws AtomicEntityException {
        spawnEntity(location, entityName, count, null);
    }

    /**
     * Spawn entity
     * Spawns a new custom entity
     *
     * @param location   Desired spawn location.
     * @param entityName Name of custom entity to spawn.
     * @param count      Number of entities to spawn.
     * @param owner      Owner of spawned entities. Can be null.
     * @throws AtomicEntityException if an exception is encountered. These
     *                               are safe to relay to players.
     */
    public void spawnEntity (Location location, String entityName, int count, Entity owner)
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

    public List<CustomEntity> getAll () {
        return customEntities;
    }

    public List<ActiveEntity> getActiveEntities () {
        return livingEntities;
    }

}
