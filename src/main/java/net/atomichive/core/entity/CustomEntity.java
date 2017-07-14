package net.atomichive.core.entity;

import com.google.gson.annotations.SerializedName;
import net.atomichive.core.exception.EntityException;
import net.atomichive.core.exception.Reason;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.StringJoiner;

/**
 * Custom Entity
 * A user defined custom entity.
 */
@SuppressWarnings("WeakerAccess")
public class CustomEntity {

    private String name;
    @SerializedName("class") private String atomicClass;
    private boolean persistant = false;


    /**
     * Custom Entity
     */
    public CustomEntity () {
        this(null, null);
    }

    /**
     * Custom Entity
     * @param name Unique entity name. Should be lower case and underscored.
     * @param atomicClass Corresponding Atomic class.
     */
    public CustomEntity (String name, String atomicClass) {
        this.name = name;
        this.atomicClass = atomicClass;
    }



    /**
     * Create instance
     * Attempts to create a new instance of this entity.
     * @param location Entity position.
     * @return Object, if it could be created
     * @throws EntityException if an exception is encountered.
     */
    @SuppressWarnings("unchecked")
    public Entity createInstance (Location location) throws EntityException {

        // Attempt to create instance
        try {
            // Get atomic class
            Class entityClass = Class.forName("net.atomichive.core.entity.Atomic" + atomicClass);

            // Construct instance
            Constructor constructor = entityClass.getConstructor(World.class);
            Entity entity = (Entity) constructor.newInstance(((CraftWorld) location.getWorld()).getHandle());

            // Do custom stuff with the entity here
            entity.getBukkitEntity().setCustomName(getDisplayName());
            entity.getBukkitEntity().setCustomNameVisible(true);
            //entity.setPersistant(persistant);

            return entity;

        } catch (ClassNotFoundException e) {
            throw new EntityException(
                    Reason.UNKNOWN_CLASS,
                    "Unknown class '" + atomicClass + "' in custom entity '" + name + "'."
            );
        } catch (NoSuchMethodException |
                InvocationTargetException |
                InstantiationException |
                IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * Get display name
     * Turns an underscored name into a display name.
     * @return display name.
     */
    public String getDisplayName () {

        // Get parts of name, splitting at underscores
        String[] parts = name.split("_");

        StringJoiner joiner = new StringJoiner(" ");

        // Add all parts with length >= 1
        for (String part : parts) {
            if (part.length() > 1) {
                joiner.add(part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase());
            } else if (part.length() == 1) {
                joiner.add(part);
            }
        }

        return joiner.toString();

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
