package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;

/**
 * A custom magma cube.
 */
@SuppressWarnings("unused")
public class AtomicMagmaCube extends AtomicSlime {


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) throws CustomObjectException {
        return spawn(location, EntityType.MAGMA_CUBE);
    }

}
