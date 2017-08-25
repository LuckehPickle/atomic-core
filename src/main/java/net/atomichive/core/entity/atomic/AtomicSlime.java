package net.atomichive.core.entity.atomic;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import net.atomichive.core.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * A custom slime.
 */
public class AtomicSlime extends AtomicEntity {


    private boolean randomSize;
    private int size;
    private boolean preventSplit;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        randomSize = attributes.get(Boolean.class, "random_size", true);
        size = attributes.getInteger("size", -1);
        preventSplit = attributes.get(Boolean.class, "prevent_split", false);

        super.init(attributes);
    }


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) throws CustomObjectException {
        return spawn(location, EntityType.SLIME);
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
        Slime slime = (Slime) entity;

        if (this.size == -1 && this.randomSize) {
            slime.setSize(Util.getRandomInt(1, 5));
        } else if (this.size != -1) {
            slime.setSize(this.size);
        }

        slime.setMetadata(
                "prevent_split",
                new FixedMetadataValue(Main.getInstance(), this.preventSplit)
        );

        return super.applyAttributes(slime);

    }

}
