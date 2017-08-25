package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;

/**
 * A custom shulker.
 */
@SuppressWarnings("unused")
public class AtomicShulker extends AtomicEntity {


    private String color;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        color = attributes.get(String.class, "color", null);

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
        return spawn(location, EntityType.SHULKER);
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
        Shulker shulker = (Shulker) entity;

        if (this.color != null) {

            // Attempt to apply color
            try {
                DyeColor color = DyeColor.valueOf(this.color);
                shulker.setColor(color);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown shulker color: '%s'.",
                        this.color
                ));
            }
        }

        return super.applyAttributes(shulker);

    }

}
