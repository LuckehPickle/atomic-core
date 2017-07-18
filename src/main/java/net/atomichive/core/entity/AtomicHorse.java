package net.atomichive.core.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

/**
 * Atomic Horse
 */
public class AtomicHorse extends AtomicAbstractHorse {


    private String color;
    private String style;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {

        color = attributes.getString("color", null);
        style = attributes.getString("style", null);

        super.init(attributes);

    }


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location) {
        return spawn(location, EntityType.HORSE);
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
        Horse horse = (Horse) entity;

        if (this.color != null) {
            Horse.Color horseColor = getColor(this.color);
            if (horseColor != null)
                horse.setColor(horseColor);
        }

        if (this.style != null) {
            Horse.Style horseStyle = getStyle(this.style);
            if (horseStyle != null)
                horse.setStyle(horseStyle);
        }

        return super.applyAttributes(horse);

    }


    /**
     * Get color
     * @param in Name of enum value
     * @return Corresponding enum or null.
     */
    private Horse.Color getColor (String in) {
        try {
            return Horse.Color.valueOf(in);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    /**
     * Get style
     * @param in Name of enum value
     * @return Corresponding enum value or null
     */
    private Horse.Style getStyle (String in) {
        try {
            return Horse.Style.valueOf(in);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
