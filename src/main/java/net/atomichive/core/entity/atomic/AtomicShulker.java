package net.atomichive.core.entity.atomic;

import net.atomichive.core.util.SmartMap;
import net.atomichive.core.util.Util;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;

/**
 * Atomic Shulker
 */
public class AtomicShulker extends AtomicEntity {


    private String color;


    /**
     * Init
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
     * Spawn
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) {
        return spawn(location, EntityType.SHULKER);
    }


    /**
     * Apply attributes
     * Applies everything defined in config to the entity.
     *
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) {

        // Cast
        Shulker shulker = (Shulker) entity;

        // Apply
        if (this.color != null) {
            DyeColor sheepColor = Util.getEnumValue(DyeColor.class, this.color);
            if (sheepColor != null)
                shulker.setColor(sheepColor);
        }

        return shulker;

    }

}
