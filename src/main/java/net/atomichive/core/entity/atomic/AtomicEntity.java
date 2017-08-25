package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Base class for all custom entities.
 */
public abstract class AtomicEntity {


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    public void init (SmartMap attributes) {

    }


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public abstract Entity spawn (Location location) throws CustomObjectException;


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @param type     Bukkit type of entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location, EntityType type) throws CustomObjectException {
        return this.applyAttributes(location.getWorld().spawnEntity(location, type));
    }


    /**
     * Applies everything defined in config to the entity.
     *
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    public Entity applyAttributes (Entity entity) throws CustomObjectException {
        return entity;
    }

}
