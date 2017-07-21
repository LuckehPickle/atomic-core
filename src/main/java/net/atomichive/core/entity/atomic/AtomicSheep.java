package net.atomichive.core.entity.atomic;

import net.atomichive.core.entity.EntityAttributes;
import net.atomichive.core.util.Util;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

/**
 * Atomic Sheep
 */
public class AtomicSheep extends AtomicAgeable {


    private boolean isSheared;
    private boolean randomColor;
    private String color;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {

        isSheared   = attributes.getBoolean("is_sheared", false);
        randomColor = attributes.getBoolean("random_color", true);
        color       = attributes.getString("color", null);

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
        return spawn(location, EntityType.SHEEP);
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
        Sheep sheep = (Sheep) entity;
        DyeColor color = null;

        // Apply
        sheep.setSheared(this.isSheared);

        if (this.color == null && randomColor)
            color = Util.getRandomDyeColor();

        if (this.color != null)
            color = Util.getEnumValue(DyeColor.class, this.color);

        if (color != null)
            sheep.setColor(color);

        return super.applyAttributes(sheep);

    }

}
