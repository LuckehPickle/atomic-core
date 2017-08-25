package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

/**
 * A custom zombie.
 */
public class AtomicZombie extends AtomicEntity {


    private boolean isBaby;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        isBaby = attributes.get(Boolean.class, "is_baby", false);

        super.init(attributes);

    }


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location) throws CustomObjectException {
        return spawn(location, EntityType.ZOMBIE);
    }


    /**
     * Applies everything defined in config to the entity.
     *
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) throws CustomObjectException {

        // Cast
        Zombie zombie = (Zombie) entity;

        if (this.isBaby) {
            zombie.setBaby(true);
        }

        return zombie;

    }

}
