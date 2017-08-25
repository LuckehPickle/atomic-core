package net.atomichive.core.nms.goals;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.nms.NMSUtil;
import net.minecraft.server.v1_12_R1.EntityCreature;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.PathfinderGoal;
import net.minecraft.server.v1_12_R1.PathfinderGoalMeleeAttack;
import org.bukkit.entity.Entity;

/**
 * A volatile goal for melee attacking a target.
 */
public class VolatileGoalMelee implements VolatileGoal {

    private EntityInsentient insentient;
    private double speed;
    private boolean notSure; // ¯\_(ツ)_/¯


    /**
     * Constructor
     *
     * @param entity Bukkit entity
     */
    public VolatileGoalMelee (Entity entity) {
        this(entity, 1.0D);
    }


    /**
     * Constructor
     *
     * @param entity Bukkit entity
     * @param speed  Entity speed modifier (walks faster, not attack speed).
     */
    public VolatileGoalMelee (Entity entity, double speed) {
        this.insentient = NMSUtil.getEntityInsentient(entity);
        this.speed = speed;
        this.notSure = true;
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

        // Ensure entity is creature
        if (!(insentient instanceof EntityCreature)) {
            throw new CustomObjectException("Could not create pathfinding goal melee_attack because the parent entity is not a creature.");
        }

        return new PathfinderGoalMeleeAttack(((EntityCreature) insentient), speed, notSure);

    }

}
