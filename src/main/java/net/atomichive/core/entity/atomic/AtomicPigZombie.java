package net.atomichive.core.entity.atomic;

import net.atomichive.core.entity.EntityAttributes;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;

/**
 * Atomic Pig Zombie
 */
public class AtomicPigZombie extends AtomicZombie {


    private boolean isAngry;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {

        isAngry = attributes.getBoolean("is_angry", false);

        super.init(attributes);
    }


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) {
        return super.spawn(location, EntityType.PIG_ZOMBIE);
    }


    /**
     * Apply attributes
     * Applies everything defined in config to the entity.
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) {

        // Cast
        PigZombie zombie = (PigZombie) entity;

        // Apply
        zombie.setAngry(this.isAngry);

        return super.applyAttributes(zombie);

    }

}
