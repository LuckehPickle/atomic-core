package net.atomichive.core.entity.atomic;

import net.atomichive.core.util.SmartMap;
import net.atomichive.core.util.Util;
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
    public void init (SmartMap attributes) {

        color = attributes.get(String.class, "color", null);
        style = attributes.get(String.class, "style", null);

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
            Horse.Color horseColor = Util.getEnumValue(Horse.Color.class, this.color);
            if (horseColor != null)
                horse.setColor(horseColor);
        }

        if (this.style != null) {
            Horse.Style horseStyle = Util.getEnumValue(Horse.Style.class, this.style);
            if (horseStyle != null)
                horse.setStyle(horseStyle);
        }

        return super.applyAttributes(horse);

    }

}
