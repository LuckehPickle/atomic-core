package net.atomichive.core.entity.atomic;

import net.atomichive.core.entity.EntityAttributes;
import net.atomichive.core.entity.atomic.AtomicEntity;
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

        isBaby  = attributes.get(Boolean.class, "is_baby",  false);
        ageLock = attributes.get(Boolean.class, "age_lock", false);

        super.init(attributes);

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
