package net.atomichive.core.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Atomic Iron Golem
 */
public class AtomicIronGolem extends AtomicEntity {


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location) {
        return spawn(location, EntityType.IRON_GOLEM);
    }


}
