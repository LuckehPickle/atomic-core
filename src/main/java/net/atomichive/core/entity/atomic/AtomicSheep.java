package net.atomichive.core.entity.atomic;

import net.atomichive.core.util.SmartMap;
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
     * Spawn
     * Generates a new entity, and places it in the world.
     *
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
     *
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

        // ((EntityInsentient) NMSUtil.getNMSEntity(entity)).getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE).setValue(3.0d);

        return super.applyAttributes(sheep);

    }

}
