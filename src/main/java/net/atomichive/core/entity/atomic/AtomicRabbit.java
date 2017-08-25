package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;

/**
 * A custom rabbit.
 */
@SuppressWarnings("unused")
public class AtomicRabbit extends AtomicAgeable {


    private String type;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        type = attributes.get(String.class, "type", null);

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
        return spawn(location, EntityType.RABBIT);
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
        Rabbit rabbit = (Rabbit) entity;

        // Apply
        if (this.type != null) {

            // Attempt to apply rabbit type
            try {
                Rabbit.Type rabbitType = Rabbit.Type.valueOf(this.type);
                rabbit.setRabbitType(rabbitType);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown rabbit type: '%s'.",
                        this.type
                ));
            }
        }

        return super.applyAttributes(rabbit);

    }


}
