package net.atomichive.core.nms.goals;

import net.atomichive.core.nms.NMSUtil;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.PathfinderGoal;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomLookaround;
import org.bukkit.entity.Entity;

/**
 * A volatile goal handler for randomly looking around.
 */
public class VolatileGoalLookAround implements VolatileGoal {

    private EntityInsentient insentient;


    /**
     * Constructor
     *
     * @param entity Bukkit entity.
     */
    public VolatileGoalLookAround (Entity entity) {
        insentient = NMSUtil.getEntityInsentient(entity);
    }


    /**
     * Converts this non-volatile handler to its volatile
     * NMS equivalent.
     *
     * @return NMS Pathfinder goal.
     */
    @Override
    public PathfinderGoal toNMS () {
        return new PathfinderGoalRandomLookaround(insentient);
    }

}
