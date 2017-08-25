package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * A custom armour stand.
 */
@SuppressWarnings("unused")
public class AtomicArmorStand extends AtomicEntity {


    private boolean isSmall;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        this.isSmall = attributes.get(Boolean.class, "is_small", false);

        super.init(attributes);

    }


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location) throws CustomObjectException {
        return spawn(location, EntityType.ARMOR_STAND);
    }


    /**
     * Applies everything defined in config to the entity.
     *
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) throws CustomObjectException {

        // Cast to armor stand
        ArmorStand armorStand = (ArmorStand) entity;

        // Set options
        if (this.isSmall)
            armorStand.setSmall(true);

        return super.applyAttributes(armorStand);

    }

}
