package net.atomichive.core.entity;

import com.google.gson.annotations.SerializedName;
import net.atomichive.core.Main;
import net.atomichive.core.exception.EntityException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.util.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.logging.Level;

/**
 * Custom Entity
 * A user defined custom entity.
 */
@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class CustomEntity {

    private String name;

    @SerializedName("class")
    private String atomicClass;

    private Map<String, Object> options;

    private transient boolean isNameVisible;
    private transient boolean despawn;
    private transient boolean isInvulnerable;
    private transient boolean isGlowing;
    private transient boolean respectsGravity;
    private transient boolean isSilent;

    private Map<String, Object> attributes;


    /**
     * Init
     */
    public void init () {

        // Create new attributes object from options
        EntityAttributes attributes = new EntityAttributes(options);

        // Apply options
        isNameVisible   = attributes.getBoolean("is_name_visible", true);
        despawn         = attributes.getBoolean("despawn", true);
        isInvulnerable  = attributes.getBoolean("is_invlunerable", false);
        isGlowing       = attributes.getBoolean("is_glowing", false);
        respectsGravity = attributes.getBoolean("respects_gravity", true);
        isSilent        = attributes.getBoolean("is_silent", false);

    }


    /**
     * Spawn
     * Creates a new active entity.
     * @param location Location to spawn entity.
     * @return Active Entity.
     */
    public ActiveEntity spawn (Location location) throws EntityException {

        Class entityClass;
        AtomicEntity entity = null;

        // Attempt to get class
        try {
            // Get class by name
            entityClass = Class.forName("net.atomichive.core.entity.Atomic" +
                    Utils.toCamelCase(true, atomicClass));
        } catch (ClassNotFoundException e) {
            throw new EntityException(
                    Reason.UNKNOWN_CLASS,
                    "Unknown entity class '" + atomicClass + "' in custom entity '" + name + "'."
            );
        }

        // Construct instance
        try {
            entity = (AtomicEntity) entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        // Init AtomicEntity
        entity.init(new EntityAttributes(attributes));

        ActiveEntity activeEntity = new ActiveEntity(entity.spawn(location));

        return this.applyAttributes(activeEntity);

    }


    /**
     * Apply attributes
     * Apply global attributes to the active entity
     * @param activeEntity Active entity to modify.
     * @return Modified active entity.
     */
    public ActiveEntity applyAttributes (ActiveEntity activeEntity) {

        Entity entity = activeEntity.getEntity();

        entity.setCustomName(Utils.toTitleCase(name));
        entity.setCustomNameVisible(isNameVisible);

        if (this.isInvulnerable)
            entity.setInvulnerable(true);

        if (this.isGlowing)
            entity.setGlowing(true);

        if(!this.respectsGravity)
            entity.setGravity(false);

        if (this.isSilent)
            entity.setSilent(true);

        return activeEntity;

    }



    /*
        Getters and setters.
     */

    public String getName () {
        return name;
    }

    public String getAtomicClass () {
        return atomicClass;
    }

}
