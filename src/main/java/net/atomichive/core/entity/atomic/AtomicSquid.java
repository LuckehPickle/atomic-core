package net.atomichive.core.entity.atomic;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Atomic Squid.
 */
public class AtomicSquid extends AtomicEntity {

    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) {
        return spawn(location, EntityType.SQUID);
    }

}
