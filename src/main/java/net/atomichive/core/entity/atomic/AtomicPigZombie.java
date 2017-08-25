package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;

/**
 * A custom zombie pig man.
 */
@SuppressWarnings("unused")
public class AtomicPigZombie extends AtomicZombie {


    private boolean isAngry;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        isAngry = attributes.get(Boolean.class, "is_angry", false);

        super.init(attributes);
    }


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) throws CustomObjectException {
        return super.spawn(location, EntityType.PIG_ZOMBIE);
    }


    /**
     * Applies everything defined in config to the entity.
     *
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) throws CustomObjectException {

        PigZombie zombie = (PigZombie) entity;
        zombie.setAngry(this.isAngry);
        return super.applyAttributes(zombie);

    }

}
