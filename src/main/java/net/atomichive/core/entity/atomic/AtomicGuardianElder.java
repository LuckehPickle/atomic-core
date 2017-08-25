package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * A custom elder guardian,
 */
@SuppressWarnings("unused")
public class AtomicGuardianElder extends AtomicEntity {


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location) throws CustomObjectException {
        return spawn(location, EntityType.ELDER_GUARDIAN);
    }


}
