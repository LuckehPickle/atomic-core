package net.atomichive.core.entity.atomic;

import net.atomichive.core.Main;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Abstract Horse
 */
public class AtomicAbstractHorse extends AtomicAgeable {


    private double jumpStrength;
    private boolean isDomesticated;
    private boolean isRideable;



    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        jumpStrength   = attributes.get(Double.class,  "jump_strength",   -1.0d);
        isDomesticated = attributes.get(Boolean.class, "is_domesticated", false);
        isRideable     = attributes.get(Boolean.class, "is_rideable",     true);

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

        horse.setMetadata(
                "is_rideable",
                new FixedMetadataValue(Main.getInstance(), this.isRideable)
        );

        return super.applyAttributes(horse);

    }

}
