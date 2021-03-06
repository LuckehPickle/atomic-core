package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;

/**
 * Any custom ageable entity.
 */
public abstract class AtomicAgeable extends AtomicEntity {


    private boolean isBaby;
    private boolean ageLock;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        isBaby = attributes.get(Boolean.class, "is_baby", false);
        ageLock = attributes.get(Boolean.class, "age_lock", false);

        super.init(attributes);

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
        Ageable ageable = (Ageable) entity;

        if (this.isBaby)
            ageable.setBaby();

        ageable.setAgeLock(this.ageLock);

        return super.applyAttributes(ageable);

    }

}
