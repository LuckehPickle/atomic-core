package net.atomichive.core.entity.atomic;

import net.atomichive.core.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;

/**
 * Atomic Magma Cube
 */
public class AtomicMagmaCube extends AtomicSlime {


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) {
        return spawn(location, EntityType.MAGMA_CUBE);
    }


    /**
     * Apply attributes
     * Applies everything defined in config to the entity.
     *
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) {

        // Cast
        MagmaCube magmaCube = (MagmaCube) entity;

        // Apply
        if (getSize() == -1 && isRandomSize())
            magmaCube.setSize(Util.getRandomInt(1, 5));

        if (getSize() != -1)
            magmaCube.setSize(getSize());

        return magmaCube;

    }

}
