package net.atomichive.core.entity.atomic;

import net.atomichive.core.entity.EntityAttributes;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mule;

/**
 * Atomic Horse Mule
 */
public class AtomicHorseMule extends AtomicAbstractHorse {


    private boolean isCarryingChest;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {

        isCarryingChest = attributes.get(Boolean.class, "is_carrying_chest", false);

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
        return spawn(location, EntityType.MULE);
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
        Mule mule = (Mule) entity;

        // Apply
        mule.setCarryingChest(this.isCarryingChest);

        return super.applyAttributes(mule);

    }

}
