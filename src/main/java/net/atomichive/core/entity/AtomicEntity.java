package net.atomichive.core.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Atomic Entity
 * Base class for all Atomic Entities.
 */
public abstract class AtomicEntity {


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    public void init (EntityAttributes attributes) {

    }


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public abstract Entity spawn (Location location);


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     * @param location to spawn entity.
     * @param type Bukkit type of entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location, EntityType type) {
        return this.applyAttributes(location.getWorld().spawnEntity(location, type));
    }


    /**
     * Apply attributes
     * Applies everything defined in config to the entity.
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    public Entity applyAttributes (Entity entity) {
        return entity;
    }

}
