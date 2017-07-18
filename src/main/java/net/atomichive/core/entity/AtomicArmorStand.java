package net.atomichive.core.entity;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Atomic Armor Stand
 */
public class AtomicArmorStand extends AtomicEntity {

    private boolean isSmall;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {
        this.isSmall = attributes.getBoolean("is_small", false);
    }


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location) {
        return spawn(location, EntityType.ARMOR_STAND);
    }


    /**
     * Apply attributes
     * Applies everything defined in config to the entity.
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) {

        // Cast to armor stand
        ArmorStand armorStand = (ArmorStand) entity;

        // Set options
        if (this.isSmall)
            armorStand.setSmall(true);

        return armorStand;

    }

}
