package net.atomichive.core.entity.atomic;

import net.atomichive.core.Main;
import net.atomichive.core.entity.EntityAttributes;
import net.atomichive.core.entity.atomic.AtomicEntity;
import net.atomichive.core.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Atomic Slime
 */
public class AtomicSlime extends AtomicEntity {


    private boolean randomSize;
    private int size;
    private boolean preventSplit;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {

        randomSize   = attributes.get(Boolean.class, "random_size",   true);
        size         = attributes.get(Integer.class, "size",          -1);
        preventSplit = attributes.get(Boolean.class, "prevent_split", false);

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
            slime.setSize(Util.getRandomInt(1, 5));

        if (this.size != -1)
            slime.setSize(this.size);

        slime.setMetadata(
                "prevent_split",
                new FixedMetadataValue(Main.getInstance(), this.preventSplit)
        );

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
