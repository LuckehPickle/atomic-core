package net.atomichive.core.entity;

import com.google.gson.annotations.SerializedName;
import net.atomichive.core.Main;
import net.atomichive.core.entity.atomic.AtomicEntity;
import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.Map;
import java.util.logging.Level;

/**
 * Contains all the information needed to construct
 * a custom entity. Attributes here are loaded through
 * with Gson.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class CustomEntity {

    private static final transient String ATOMIC_STUB = "net.atomichive.core.entity.atomic.Atomic";

    private String name;             // Unique name of this entity
    private boolean despawn  = true; // Whether the entity despawns
    private int level = 1;           // Entities level
    private Map pathfinding = null;  // Pathfinding configuration
    private Map[] abilities = null;  // Entity abilities
    private Map merchant    = null;  // Merchant related info

    @SerializedName("class")            // Atomic entity class
    private String entityClass;

    @SerializedName("display_name")     // Exact text to include in name tag
    private String displayName = null;

    @SerializedName("is_name_visible")  // Whether this entities name tag should be visible
    private boolean isNameVisible = true;

    @SerializedName("is_invulnerable")  // Prevents this entity from taking any damage
    private boolean isInvulnerable = false;

    @SerializedName("is_glowing")       // Causes the entity to glow
    private boolean isGlowing = false;

    @SerializedName("respects_gravity") // Whether the entity should be affected by gravity
    private boolean respectsGravity = true;

    @SerializedName("is_silent")        // Prevents the entity from making noise
    private boolean isSilent = false;

    @SerializedName("is_collidable")    // Whether the tntity should collide with other entities
    private boolean isCollidable = true;

    @SerializedName("prevent_item_pickup") // Whether the entity can pick items up or not.
    private boolean preventItemPickup = false;

    @SerializedName("class_attributes")           // Local attributes, specific to entity class
    private Map<String, Object> classAttributes;


    /**
     * Creates a new active entity.
     *
     * @param location Location to spawn entity.
     * @return Active Entity.
     */
    public ActiveEntity spawn (Location location) throws CustomObjectException {
        return spawn(location, null);
    }


    /**
     * Creates a new active entity.
     *
     * @param location Location to spawn entity.
     * @return Active Entity.
     */
    public ActiveEntity spawn (Location location, Entity owner) throws CustomObjectException {

        Class entityClass;
        AtomicEntity entity;

        // Attempt to get class
        try {
            // Get class by name
            entityClass = Class.forName(ATOMIC_STUB + Util.toCamelCase(this.entityClass));
        } catch (ClassNotFoundException e) {
            throw new CustomObjectException(String.format(
                    "Unknown entity class '%s' in custom entity '%s'.",
                    this.entityClass,
                    name
            ));
        }

        // Construct instance
        try {
            entity = (AtomicEntity) entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        // Calling init will apply class attributes
        entity.init(new SmartMap(classAttributes));

        ActiveEntity activeEntity = new ActiveEntity(entity.spawn(location));
        activeEntity.setOwner(owner);
        activeEntity.setLevel(level);

        return this.applyAttributes(activeEntity);

    }


    /**
     * Apply attributes to the active entity.
     *
     * @param activeEntity Active entity to modify.
     * @return Modified active entity.
     */
    public ActiveEntity applyAttributes (ActiveEntity activeEntity) {

        // Get entity
        Entity entity = activeEntity.getEntity();

        String customName;
        String prefix = "";

        SmartMap merchantMap;

        if (this.merchant != null) {
            merchantMap = new SmartMap(this.merchant);
            prefix = merchantMap.get(String.class, "prefix", "");
            if (!prefix.isEmpty()) {
                prefix = String.format(
                        "%s[%s]%s ",
                        ChatColor.YELLOW, prefix, ChatColor.RESET
                );
            }
        }

        // Set entity name
        if (this.displayName == null) {
            customName = Util.toTitleCase(this.name);
        } else {
            customName = this.displayName;
        }

        entity.setCustomName(String.format(
                "%s<COLOR>%s [%d]",
                prefix,
                customName,
                this.level
        ));

        // Apply misc. attributes
        entity.setCustomNameVisible(this.isNameVisible);
        entity.setInvulnerable(this.isInvulnerable);
        entity.setGlowing(this.isGlowing);
        entity.setGravity(this.respectsGravity);
        entity.setSilent(this.isSilent);


        if (!entity.isDead()) {

            // Apply living attributes.
            LivingEntity living = (LivingEntity) entity;
            living.setRemoveWhenFarAway(this.despawn);
            living.setCollidable(this.isCollidable);
            living.setCanPickupItems(!this.preventItemPickup);

        }


        try {
            if (this.pathfinding != null) {
                activeEntity.applyPathfinding(pathfinding);
            }
        } catch (CustomObjectException e) {
            Main.getInstance().log(Level.SEVERE, String.format(
                    "Failed to apply pathfinding to custom entity '%s': %s",
                    this.name,
                    e.getMessage()
            ));
        }

        if (this.abilities != null) {
            activeEntity.applyAbilities(abilities);
        }

        return activeEntity;

    }


    @Override
    public String toString () {
        return String.format("%s [%s]", this.name, this.entityClass);
    }


    /*
        Getters and setters.
     */

    public String getName () {
        return name;
    }

    public boolean isDespawn () {
        return despawn;
    }

    public int getLevel () {
        return level;
    }

    public Map getPathfinding () {
        return pathfinding;
    }

    public Map[] getAbilities () {
        return abilities;
    }

    public String getEntityClass () {
        return entityClass;
    }

    public String getDisplayName () {
        return displayName;
    }

    public boolean isNameVisible () {
        return isNameVisible;
    }

    public boolean isInvulnerable () {
        return isInvulnerable;
    }

    public boolean isGlowing () {
        return isGlowing;
    }

    public boolean isRespectsGravity () {
        return respectsGravity;
    }

    public boolean isSilent () {
        return isSilent;
    }

    public boolean isCollidable () {
        return isCollidable;
    }

    public boolean isPreventItemPickup () {
        return preventItemPickup;
    }

}
