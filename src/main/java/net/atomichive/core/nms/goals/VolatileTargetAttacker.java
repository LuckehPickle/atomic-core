package net.atomichive.core.nms.goals;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.nms.NMSUtil;
import net.minecraft.server.v1_12_R1.EntityCreature;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.PathfinderGoal;
import net.minecraft.server.v1_12_R1.PathfinderGoalHurtByTarget;
import org.bukkit.entity.Entity;

/**
 * A volatile goal that targets an entity's attacker.
 */
public class VolatileTargetAttacker implements VolatileGoal {

    private EntityInsentient insentient;


    /**
     * Constructor
     *
     * @param entity Bukkit entity.
     */
    public VolatileTargetAttacker (Entity entity) {
        this.insentient = NMSUtil.getEntityInsentient(entity);
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

        // Ensure entity is a creature
        if (!(insentient instanceof EntityCreature)) {
            throw new CustomObjectException("Could not create pathfinding target attacker because the parent entity is not a creature.");
        }

        return new PathfinderGoalHurtByTarget((EntityCreature) insentient, false);

    }
}
