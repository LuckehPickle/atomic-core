package net.atomichive.core.entity.atomic;

import net.atomichive.core.entity.atomic.AtomicEntity;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Atomic Guardian Elder
 */
public class AtomicGuardianElder extends AtomicEntity {


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location) {
        return spawn(location, EntityType.ELDER_GUARDIAN);
    }


}