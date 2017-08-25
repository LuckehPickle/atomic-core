package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.nms.NMSUtil;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * A custom creeper.
 */
@SuppressWarnings("unused")
public class AtomicCreeper extends AtomicEntity {


    private boolean isCharged;
    private int fuseTicks;
    private int explosionRadius;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        isCharged = attributes.get(Boolean.class, "is_charged", false);
        fuseTicks = attributes.get(Integer.class, "fuse_ticks", 30);
        explosionRadius = attributes.get(Integer.class, "explosion_radius", 3);

        super.init(attributes);

    }


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location) throws CustomObjectException {
        return spawn(location, EntityType.CREEPER);
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
        Creeper creeper = (Creeper) entity;

        // Set attributes
        if (this.isCharged)
            creeper.setPowered(true);

        NMSUtil.setCreeperFuse(creeper, this.fuseTicks);
        NMSUtil.setCreeperRadius(creeper, this.explosionRadius);

        return super.applyAttributes(creeper);

    }

}
