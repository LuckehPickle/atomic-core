package net.atomichive.core.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;

/**
 * Atomic Parrot
 */
public class AtomicParrot extends AtomicAgeable {


    private String variant;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {

        variant = attributes.getString("variant", null);

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
        return super.spawn(location, EntityType.PARROT);
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
        Parrot parrot = (Parrot) entity;

        // Apply
        if (this.variant != null) {
            Parrot.Variant parrotVariant = getVariant(this.variant);

            if (parrotVariant != null)
                parrot.setVariant(parrotVariant);
        }

        return super.applyAttributes(entity);
    }


    /**
     * Get variant
     * @param variant Name of desired parrot variant.
     * @return Corresponding parrot variant or null.
     */
    private Parrot.Variant getVariant (String variant) {
        try {
            return Parrot.Variant.valueOf(variant);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
