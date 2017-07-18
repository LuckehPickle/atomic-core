package net.atomichive.core.entity;

import org.bukkit.Location;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Atomic Horse Donkey
 */
public class AtomicHorseDonkey extends AtomicAbstractHorse {


    private boolean isCarryingChest;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {
        isCarryingChest = attributes.getBoolean("is_carrying_chest", false);
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
        return spawn(location, EntityType.DONKEY);
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
        Donkey donkey = (Donkey) entity;

        // Apply
        donkey.setCarryingChest(this.isCarryingChest);

        return super.applyAttributes(donkey);

    }

}
