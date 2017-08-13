package net.atomichive.core.entity.atomic;

import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowman;

/**
 * Atomic Snowman
 */
public class AtomicSnowman extends AtomicEntity {


    private boolean isDerp;


    /**
     * Init
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        isDerp = attributes.get(Boolean.class, "is_derp", false);

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
        return spawn(location, EntityType.SNOWMAN);
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
        Snowman man = (Snowman) entity;

        man.setDerp(this.isDerp);

        return super.applyAttributes(man);

    }
}
