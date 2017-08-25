package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * A custom squid.
 */
@SuppressWarnings("unused")
public class AtomicSquid extends AtomicEntity {

    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) throws CustomObjectException {
        return spawn(location, EntityType.SQUID);
    }

}
