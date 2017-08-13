package net.atomichive.core.entity;

import com.google.gson.annotations.SerializedName;
import net.atomichive.core.entity.atomic.AtomicEntity;
import net.atomichive.core.exception.AtomicEntityException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.util.SmartMap;
import net.atomichive.core.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Map;

/**
 * Custom Entity
 * A user defined custom entity.
 */
@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class CustomEntity {

    private String name;

    @SerializedName("class")
    private String atomicClass;

    @SerializedName("globals")
    private Map<String, Object> globalAttributes;

    @SerializedName("locals")
    private Map<String, Object> localAttributes;

    // Transient global attributes
    private transient boolean isNameVisible;


    private transient boolean despawn;
    private transient boolean isInvulnerable;
    private transient boolean isGlowing;
    private transient boolean respectsGravity;
    private transient boolean isSilent;
    private transient boolean isCollidable;
    private transient boolean preventItemPickup;
    private transient String displayName;
    private transient Map pathfinding;
    private transient List abilities;


    /**
     * Init
     * Retrieves all global attributes and sets them.
     */
    public void init () {

        // Create new attributes object from global attributes
        SmartMap attributes = new SmartMap(globalAttributes);

        // Apply global attributes
        isNameVisible = attributes.get(Boolean.class, "is_name_visible", true);
        despawn = attributes.get(Boolean.class, "despawn", true);
        isInvulnerable = attributes.get(Boolean.class, "is_invulnerable", false);
        isGlowing = attributes.get(Boolean.class, "is_glowing", false);
        respectsGravity = attributes.get(Boolean.class, "respects_gravity", true);
        isSilent = attributes.get(Boolean.class, "is_silent", false);
        isCollidable = attributes.get(Boolean.class, "is_collidable", true);
        preventItemPickup = attributes.get(Boolean.class, "prevent_item_pickup", false);
        displayName = attributes.get(String.class, "display_name", null);
        pathfinding = attributes.get(Map.class, "pathfinding", null);
        abilities = attributes.get(List.class, "abilities", null);

    }


    /**
     * Spawn
     * Creates a new active entity.
     *
     * @param location Location to spawn entity.
     * @return Active Entity.
     */
    public ActiveEntity spawn (Location location) throws AtomicEntityException {
        return spawn(location, null);
    }


    /**
     * Spawn
     * Creates a new active entity.
     *
     * @param location Location to spawn entity.
     * @return Active Entity.
     */
    public ActiveEntity spawn (Location location, Entity owner) throws AtomicEntityException {

        Class entityClass;
        AtomicEntity entity = null;

        // Attempt to get class
        try {
            // Get class by name
            entityClass = Class.forName("net.atomichive.core.entity.atomic.Atomic" +
                    Util.toCamelCase(true, atomicClass));
        } catch (ClassNotFoundException e) {
            throw new AtomicEntityException(
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
        entity.init(new SmartMap(localAttributes));

        ActiveEntity activeEntity = new ActiveEntity(entity.spawn(location));
        activeEntity.setOwner(owner);

        return this.applyAttributes(activeEntity);

    }


    /**
     * Apply local attributes
     * Apply local attributes to the active entity
     *
     * @param activeEntity Active entity to modify.
     * @return Modified active entity.
     */
    public ActiveEntity applyAttributes (ActiveEntity activeEntity) {

        // Get entity
        Entity entity = activeEntity.getEntity();
        String customName;

        // Set entity name
        if (this.displayName == null) {
            customName = Util.toTitleCase(this.name);
        } else {
            customName = this.displayName;
        }

        entity.setCustomName("%s" + customName + " [%d]");

        // Apply misc. attributes
        entity.setCustomNameVisible(this.isNameVisible);
        entity.setInvulnerable(this.isInvulnerable);
        entity.setGlowing(this.isGlowing);
        entity.setGravity(this.respectsGravity);
        entity.setSilent(this.isSilent);


        // Apply living attributes.
        if (!entity.isDead()) {

            // Cast to living entity
            LivingEntity living = (LivingEntity) entity;

            // Apply
            living.setRemoveWhenFarAway(this.despawn);
            living.setCollidable(this.isCollidable);
            living.setCanPickupItems(!this.preventItemPickup);

        }


        // Attempt to apply pathfinding.
        if (this.pathfinding != null)
            activeEntity.applyPathfinding(pathfinding);

        // Attempt to apply abilities.
        if (this.abilities != null)
            activeEntity.applyAbilities(abilities);

        return activeEntity;

    }


    @Override
    public String toString () {
        return String.format("%s [%s]", this.name, this.atomicClass);
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
