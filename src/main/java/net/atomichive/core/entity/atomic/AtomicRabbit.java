package net.atomichive.core.entity.atomic;

import net.atomichive.core.entity.EntityAttributes;
import net.atomichive.core.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;

/**
 * Atomic Rabbit.
 */
public class AtomicRabbit extends AtomicAgeable {


    private String type;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {

        type = attributes.getString("type", null);

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
        return spawn(location, EntityType.RABBIT);
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
        Rabbit rabbit = (Rabbit) entity;

        // Apply
        if (this.type != null) {
            Rabbit.Type rabbitType = Util.getEnumValue(Rabbit.Type.class, this.type);
            if (rabbitType != null)
                rabbit.setRabbitType(rabbitType);
        }

        return super.applyAttributes(rabbit);

    }


}
