package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;

/**
 * A custom parrot.
 */
@SuppressWarnings("unused")
public class AtomicParrot extends AtomicAgeable {


    private String variant;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        variant = attributes.get(String.class, "variant", null);

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
        return super.spawn(location, EntityType.PARROT);
    }


    /**
     * Applies everything defined in config to the entity.
     *
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) throws CustomObjectException {

        Parrot parrot = (Parrot) entity;

        if (this.variant != null) {

            // Attempt to apply parrot variant
            try {
                Parrot.Variant parrotVariant = Parrot.Variant.valueOf(this.variant);
                parrot.setVariant(parrotVariant);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown parrot variant: '%s'.",
                        this.variant
                ));
            }

        }

        return super.applyAttributes(entity);

    }

}
