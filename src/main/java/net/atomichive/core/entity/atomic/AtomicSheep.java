package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import net.atomichive.core.util.Util;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

/**
 * A custom sheep.
 */
@SuppressWarnings("unused")
public class AtomicSheep extends AtomicAgeable {


    private boolean isSheared;
    private boolean randomColor;
    private String color;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        isSheared = attributes.get(Boolean.class, "is_sheared", false);
        randomColor = attributes.get(Boolean.class, "random_color", true);
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
        return spawn(location, EntityType.SHEEP);
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
        Sheep sheep = (Sheep) entity;
        DyeColor color = null;

        // Apply
        sheep.setSheared(this.isSheared);

        if (this.color == null && randomColor)
            color = Util.getRandomDyeColor();

        if (this.color != null) {
            try {
                color = DyeColor.valueOf(this.color);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown sheep color: '%s'.",
                        this.color
                ));
            }
        }

        if (color != null)
            sheep.setColor(color);

        return super.applyAttributes(sheep);

    }

}
