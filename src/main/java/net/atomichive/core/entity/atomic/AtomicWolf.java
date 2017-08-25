package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

/**
 * A custom wolf.
 */
@SuppressWarnings("unused")
public class AtomicWolf extends AtomicAgeable {


    private boolean isTamed;
    private boolean isSitting;
    private boolean isAngry;
    private String collarColor;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        isTamed = attributes.get(Boolean.class, "is_tamed", false);
        isSitting = attributes.get(Boolean.class, "is_sitting", false);
        isAngry = attributes.get(Boolean.class, "is_angry", false);
        collarColor = attributes.get(String.class, "collar_color", null);

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
        return spawn(location, EntityType.WOLF);
    }


    /**
     * Applies everything defined in config to the entity.
     *
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) throws CustomObjectException {

        Wolf wolf = (Wolf) entity;

        wolf.setTamed(this.isTamed);
        wolf.setSitting(this.isSitting);

        if (this.collarColor != null) {
            // Attempt to apply collar color
            try {
                DyeColor color = DyeColor.valueOf(this.collarColor);
                wolf.setCollarColor(color);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown wolf collar color: '%s'.",
                        this.collarColor
                ));
            }
        }

        wolf.setAngry(this.isAngry);

        return super.applyAttributes(wolf);

    }

}
