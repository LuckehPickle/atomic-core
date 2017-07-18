package net.atomichive.core.entity;

import net.atomichive.core.util.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;

/**
 * Atomic Slime
 */
public class AtomicSlime extends AtomicEntity {


    private boolean randomSize;
    private int size;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {
        randomSize = attributes.getBoolean("random_size", true);
        size = attributes.getInt("size", -1);
    }


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) {
        return spawn(location, EntityType.SLIME);
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
        Slime slime = (Slime) entity;

        // Apply
        if (this.size == -1 && this.randomSize)
            slime.setSize(Utils.getRandomInt(1, 3));

        if (this.size != -1)
            slime.setSize(this.size);

        return slime;

    }


    /*
        Getters.
     */

    public boolean isRandomSize () {
        return randomSize;
    }

    public int getSize () {
        return size;
    }

}
