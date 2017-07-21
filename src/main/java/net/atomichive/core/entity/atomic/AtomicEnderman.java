package net.atomichive.core.entity.atomic;

import net.atomichive.core.entity.EntityAttributes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;

/**
 * Atomic Enderman
 */
public class AtomicEnderman extends AtomicEntity {

    private String carrying;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {
        carrying = attributes.getString("carrying", null);
    }


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    public Entity spawn (Location location) {
        return spawn(location, EntityType.ENDERMAN);
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
        Enderman enderman = (Enderman) entity;

        if (carrying != null) {

            Material material = Material.getMaterial(carrying);

            if (material != null)
                enderman.setCarriedMaterial(new MaterialData(material));

        }

        return enderman;
    }

}
