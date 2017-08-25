package net.atomichive.core.nms.goals;

import net.atomichive.core.exception.CustomObjectException;
import net.minecraft.server.v1_12_R1.PathfinderGoal;

/**
 * A volatile goal.
 */
public interface VolatileGoal {

    /**
     * Converts this non-volatile handler to its volatile
     * NMS equivalent.
     *
     * @return NMS Pathfinder goal.
     * @throws CustomObjectException if the NMS conversion cannot be completed.
     */
    PathfinderGoal toNMS () throws CustomObjectException;

}
