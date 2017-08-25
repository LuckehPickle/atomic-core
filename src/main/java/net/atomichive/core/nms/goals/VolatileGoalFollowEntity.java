package net.atomichive.core.nms.goals;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.nms.NMSUtil;
import net.atomichive.core.nms.pathfinding.PathfinderGoalFollowEntity;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.PathfinderGoal;
import org.bukkit.entity.Entity;

/**
 * A volatile pathfinding goal for following entities.
 */
public class VolatileGoalFollowEntity implements VolatileGoal {

    private EntityInsentient insentient;
    private Entity owner;


    /**
     * Constructor
     *
     * @param entity Bukkit entity.
     * @param owner Entity to follow.
     */
    public VolatileGoalFollowEntity (Entity entity, Entity owner) {
        insentient = NMSUtil.getEntityInsentient(entity);
        this.owner = owner;
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

        // Ensure owner is not null
        if (owner == null) {
            throw new CustomObjectException("Cannot follow owner; no owner assigned.");
        }

        return new PathfinderGoalFollowEntity(insentient, owner, 1.0D, 2.5F, 30.0F);
    }

}
