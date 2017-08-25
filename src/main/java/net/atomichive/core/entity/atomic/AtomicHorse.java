package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

/**
 * A custom horse.
 */
@SuppressWarnings("unused")
public class AtomicHorse extends AtomicAbstractHorse {


    private String color;
    private String style;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        color = attributes.get(String.class, "color", null);
        style = attributes.get(String.class, "style", null);

        super.init(attributes);

    }


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location) throws CustomObjectException {
        return spawn(location, EntityType.HORSE);
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
        Horse horse = (Horse) entity;

        if (this.color != null) {

            // Attempt to apply horse color
            try {
                Horse.Color horseColor = Horse.Color.valueOf(this.color);
                horse.setColor(horseColor);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown horse color: '%s'.",
                        this.color
                ));
            }
        }

        if (this.style != null) {

            // Attempt to apply horse style
            try {
                Horse.Style horseStyle = Horse.Style.valueOf(this.style);
                horse.setStyle(horseStyle);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown horse style: '%s'.",
                        this.style
                ));
            }
        }

        return super.applyAttributes(horse);

    }

}
