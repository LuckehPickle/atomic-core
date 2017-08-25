package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * A custom donkey.
 */
@SuppressWarnings("unused")
public class AtomicHorseDonkey extends AtomicAbstractHorse {


    private boolean isCarryingChest;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        isCarryingChest = attributes.get(Boolean.class, "is_carrying_chest", false);

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
        return spawn(location, EntityType.DONKEY);
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
        Donkey donkey = (Donkey) entity;
        donkey.setCarryingChest(this.isCarryingChest);

        return super.applyAttributes(donkey);

    }

}
