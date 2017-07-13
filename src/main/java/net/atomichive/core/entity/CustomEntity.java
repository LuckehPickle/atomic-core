package net.atomichive.core.entity;

import com.google.gson.annotations.SerializedName;
import net.atomichive.core.exception.EntityException;
import net.atomichive.core.exception.Reason;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Custom Entity
 * A user defined custom entity.
 */
@SuppressWarnings("WeakerAccess")
public class CustomEntity {

    private String name;

    @SerializedName("class")
    private String atomicClass;


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
     * @param world Required for entity instantiation.
     * @return Object, if it could be created
     * @throws EntityException if an exception is encountered.
     */
    @SuppressWarnings("unchecked")
    public Entity createInstance (World world) throws EntityException {

        // Attempt to create instance
        try {
            // Get atomic class
            Class entityClass = Class.forName("net.atomichive.core.entity.Atomic" + atomicClass);

            // Construct instance
            Constructor constructor = entityClass.getConstructor(World.class);
            return (Entity) constructor.newInstance(world);

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
