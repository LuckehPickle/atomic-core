package net.atomichive.core.entity;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;

/**
 * Atomic Ageable
 */
public abstract class AtomicAgeable extends AtomicEntity {


    private boolean isBaby;
    private boolean ageLock;

    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {
        isBaby  = attributes.getBoolean("is_baby",  false);
        ageLock = attributes.getBoolean("age_lock", false);
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
        Ageable ageable = (Ageable) entity;

        if (this.isBaby)
            ageable.setBaby();

        ageable.setAgeLock(this.ageLock);

        return ageable;

    }

}
