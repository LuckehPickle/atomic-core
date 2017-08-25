package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Llama;

/**
 * A custom Llama.
 */
@SuppressWarnings("unused")
public class AtomicLlama extends AtomicAbstractHorse {


    private boolean isCarryingChest;
    private int strength;
    private String color;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        isCarryingChest = attributes.get(Boolean.class, "is_carrying_chest", false);
        strength = attributes.get(Integer.class, "strength", -1);
        color = attributes.get(String.class, "color", null);

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
        return spawn(location, EntityType.LLAMA);
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
        Llama llama = (Llama) entity;

        // Apply
        llama.setCarryingChest(this.isCarryingChest);

        if (this.strength != -1) {
            // Clamp value between 1 and 5
            this.strength = Math.max(1, Math.min(5, this.strength));
            llama.setStrength(this.strength);
        }

        if (this.color != null) {
            // Attempt to apply llama color
            try {
                Llama.Color llamaColor = Llama.Color.valueOf(this.color);
                llama.setColor(llamaColor);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown Llama color: '%s'.",
                        this.color
                ));
            }
        }

        return super.applyAttributes(llama);
    }

}
