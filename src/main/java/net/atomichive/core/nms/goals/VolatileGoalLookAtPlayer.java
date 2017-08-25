package net.atomichive.core.nms.goals;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.nms.NMSUtil;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.PathfinderGoal;
import net.minecraft.server.v1_12_R1.PathfinderGoalLookAtPlayer;
import org.bukkit.entity.Entity;

/**
 * A volatile pathfinding goal for looking at nearby entities.
 */
public class VolatileGoalLookAtPlayer implements VolatileGoal {

    private EntityInsentient insentient;
    private float radius;


    /**
     * Constructor
     *
     * @param entity Bukkit entity.
     */
    public VolatileGoalLookAtPlayer (Entity entity) {
        this(entity, 8.0f);
    }

    /**
     * Constructor
     *
     * @param entity Bukkit entity.
     * @param radius Maximum targetting radius.
     */
    public VolatileGoalLookAtPlayer (Entity entity, float radius) {
        this.insentient = NMSUtil.getEntityInsentient(entity);
        this.radius = radius;
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
        return new PathfinderGoalLookAtPlayer(insentient, EntityHuman.class, radius);
    }

}
