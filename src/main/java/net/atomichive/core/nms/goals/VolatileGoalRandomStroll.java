package net.atomichive.core.nms.goals;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.nms.NMSUtil;
import net.minecraft.server.v1_12_R1.EntityCreature;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.PathfinderGoal;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomStroll;
import org.bukkit.entity.Entity;

/**
 * A volatile pathfinding goal for randomly strolling
 * around.
 */
public class VolatileGoalRandomStroll implements VolatileGoal {

    private EntityInsentient insentient;
    private double speed;


    /**
     * Constructor
     *
     * @param entity Bukkit entity.
     */
    public VolatileGoalRandomStroll (Entity entity) {
        this(entity, 1.0D);
    }

    /**
     * Constructor
     *
     * @param entity Bukkit entity.
     * @param speed  Speed modifier.
     */
    public VolatileGoalRandomStroll (Entity entity, double speed) {
         this.insentient = NMSUtil.getEntityInsentient(entity);
         this.speed = speed;
    }


    /**
     * Converts this non-volatile handler to its volatile
     * NMS equivalent.
     *
     * @return NMS Pathfinder goal.
     * @throws CustomObjectException if the NMS conversion cannot be completed.
     */
    @Override
    public PathfinderGoal toNMS () throws CustomObjectException {

        // Ensure entity is a creature.
        if (!(insentient instanceof EntityCreature)) {
            throw new CustomObjectException("Could not create pathfinding goal random_stroll because the parent entity is not a creature.");
        }

        return new PathfinderGoalRandomStroll(((EntityCreature) insentient), speed);

    }
}
