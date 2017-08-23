package net.atomichive.core.util;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityCreeper;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCreeper;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.Creeper;

import java.lang.reflect.Field;

/**
 * NMS Util
 * Used for working with net.minecraft.server
 */
public class NMSUtil {


    /**
     * Get NMS Entity
     *
     * @param entity Bukkit entity.
     * @return Corresponding NMS entity.
     */
    public static Entity getNMSEntity (org.bukkit.entity.Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }


    /**
     * Get private field
     *
     * @param clazz     Class to get field from.
     * @param fieldName Name of private field to retrieve.
     * @return Private field, set as accessible.
     */
    public static Field getPrivateField (Class clazz, String fieldName) {

        Field field;

        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }

        field.setAccessible(true);

        return field;

    }


    /**
     * Set creeper radius
     *
     * @param c      Bukkit creeper.
     * @param radius Explosion radius.
     */
    public static void setCreeperRadius (Creeper c, int radius) {

        // Get NMS entity
        EntityCreeper creeper = ((CraftCreeper) c).getHandle();

        Field field = getPrivateField(EntityCreeper.class, "explosionRadius");

        try {
            field.setInt(creeper, radius);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * Set creeper fuse
     *
     * @param c     Bukkit creeper
     * @param ticks Fuse ticks.
     */
    public static void setCreeperFuse (Creeper c, int ticks) {

        // Get NMS entity
        EntityCreeper creeper = ((CraftCreeper) c).getHandle();

        Field field = getPrivateField(EntityCreeper.class, "maxFuseTicks");

        try {
            field.setInt(creeper, ticks);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
