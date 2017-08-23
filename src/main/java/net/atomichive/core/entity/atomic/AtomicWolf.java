package net.atomichive.core.entity.atomic;

import net.atomichive.core.util.SmartMap;
import net.atomichive.core.util.Util;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

/**
 * Atomic Wolf
 */
public class AtomicWolf extends AtomicAgeable {


    private boolean isTamed;
    private boolean isSitting;
    private boolean isAngry;
    private String collarColor;


    /**
     * Init
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
     * Spawn
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) {
        return spawn(location, EntityType.WOLF);
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
        Wolf wolf = (Wolf) entity;

        // Apply
        wolf.setTamed(this.isTamed);
        wolf.setSitting(this.isSitting);

        if (this.collarColor != null) {
            DyeColor color = Util.getEnumValue(DyeColor.class, this.collarColor);
            if (color != null)
                wolf.setCollarColor(color);
        }

        wolf.setAngry(this.isAngry);

        return super.applyAttributes(wolf);

    }

}
