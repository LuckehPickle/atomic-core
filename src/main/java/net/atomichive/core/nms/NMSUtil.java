package net.atomichive.core.nms;

import net.atomichive.core.util.Util;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityCreeper;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.PathfinderGoalSelector;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCreeper;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Creeper;

import java.lang.reflect.Field;

/**
 * Used for working with net.minecraft.server
 */
public class NMSUtil {


    /**
     * Retrieves a NMS entity from a Bukkit entity.
     * TODO This is volatile, fix it.
     *
     * @param entity Bukkit entity.
     * @return Corresponding NMS entity.
     */
    public static Entity getNMSEntity (org.bukkit.entity.Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }


    /**
     * Alters a creeper's explosion radius through reflection.
     *
     * @param _creeper Bukkit creeper.
     * @param radius  Explosion radius.
     */
    public static void setCreeperRadius (Creeper _creeper, int radius) {

        // Get NMS entity
        EntityCreeper creeper = ((CraftCreeper) _creeper).getHandle();

        Field field = Util.getPrivateField(
                EntityCreeper.class,
                "explosionRadius"
        );

        if (field == null) return;

        try {
            field.setInt(creeper, radius);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * Alters a creeper's fuse time through reflection.
     *
     * @param _creeper Bukkit creeper
     * @param ticks Fuse ticks.
     */
    public static void setCreeperFuse (Creeper _creeper, int ticks) {

        // Get NMS entity
        EntityCreeper creeper = ((CraftCreeper) _creeper).getHandle();

        Field field = Util.getPrivateField(
                EntityCreeper.class,
                "maxFuseTicks"
        );

        if (field == null) return;

        try {
            field.setInt(creeper, ticks);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * Retrieves a volatile goal selector from a Bukkit
     * entity.
     *
     * @param entity Bukkit entity.
     * @param type   Type of goal selector.
     * @return Volatile goal selector.
     */
    public static VolatileGoalSelector getGoalSelector (org.bukkit.entity.Entity entity, VolatileGoalSelector.Type type) {

        EntityInsentient insentient = getEntityInsentient(entity);

        // Retrieve NMS field
        Field field = Util.getPrivateField(
                EntityInsentient.class,
                type.getFieldName()
        );

        // Ensure field isn't null
        if (field == null) return null;

        try {
            return new VolatileGoalSelector(
                    (PathfinderGoalSelector) field.get(insentient)
            );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * Retrieves an insentient entity.
     *
     * @param entity Bukkit entity.
     * @return Entity insentient.
     */
    public static EntityInsentient getEntityInsentient (org.bukkit.entity.Entity entity) {
        return (EntityInsentient) ((CraftLivingEntity) entity).getHandle();
    }

}
