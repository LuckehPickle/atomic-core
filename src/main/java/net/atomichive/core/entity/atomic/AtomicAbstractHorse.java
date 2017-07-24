package net.atomichive.core.entity.atomic;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.atomichive.core.entity.EntityAttributes;
import org.bukkit.Location;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Abstract Horse
 */
public class AtomicAbstractHorse extends AtomicAgeable {


    private double jumpStrength;
    private boolean isDomesticated;

    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {

        jumpStrength   = attributes.get(Double.class,  "jump_strength",   -1.0d);
        isDomesticated = attributes.get(Boolean.class, "is_domesticated", false);

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
        return super.spawn(location, EntityType.HORSE);
    }


    /**
     * Apply attributes
     * Applies everything defined in config to the entity.
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) {

        AbstractHorse horse = (AbstractHorse) entity;

        if (this.jumpStrength != -1)
            horse.setJumpStrength(this.jumpStrength);

        if (this.isDomesticated) {
            horse.setDomestication(horse.getMaxDomestication());
            horse.setTamed(true);
        }

        return super.applyAttributes(horse);

    }

}
