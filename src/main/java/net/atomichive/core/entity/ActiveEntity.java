package net.atomichive.core.entity;

import net.atomichive.core.entity.ai.PathfinderGoalFollowEntity;
import net.atomichive.core.entity.ai.Pathfinding;
import net.atomichive.core.util.NMSUtil;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Active Entity
 * Represents an entity who is active in the world.
 */
@SuppressWarnings("WeakerAccess")
public class ActiveEntity {


    // Attributes
    private Entity entity;
    private PathfinderGoalSelector goals;
    private PathfinderGoalSelector targets;
    private Entity owner;


    /**
     * Active Entity
     * @param entity Bukkit entity.
     */
    public ActiveEntity (Entity entity) {
        this.entity = entity;
    }


    /**
     * Apply pathfinding
     * @param preset Pathfinding preset to apply.
     */
    public void applyPathfinding (Pathfinding preset) {

        // Init
        EntityInsentient entity = (EntityInsentient)((CraftLivingEntity) this.entity).getHandle();
        Field goalsField   = NMSUtil.getPrivateField(EntityInsentient.class, "goalSelector");
        Field targetsField = NMSUtil.getPrivateField(EntityInsentient.class, "targetSelector");

        try {
            this.goals   = (PathfinderGoalSelector) goalsField.get(entity);
            this.targets = (PathfinderGoalSelector) targetsField.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        clearGoalsAndTargets();
        goals.a(0, new PathfinderGoalFloat(entity));

        switch (preset) {
            case NPC:
                applyNPCPreset(entity);
                break;
            case FOLLOW_OWNER:
                applyOwnerPreset(entity, false);
                break;
            case PROTECT_OWNER:
                applyOwnerPreset(entity, true);
                break;
            case HOSTILE:
                applyHostilePreset(entity);
                break;
            case GUARD:
                // applyGuardPreset(entity);
                break;
        }

    }


    /**
     * Clear goals and targets
     * Clears all currently stored pathfinding goals
     * and targets.
     */
    private void clearGoalsAndTargets () {

        Field fieldB = NMSUtil.getPrivateField(PathfinderGoalSelector.class, "b");
        Field fieldC = NMSUtil.getPrivateField(PathfinderGoalSelector.class, "c");

        try {

            // Get sets
            ((Set) fieldB.get(this.goals)).clear();
            ((Set) fieldC.get(this.goals)).clear();
            ((Set) fieldB.get(this.targets)).clear();
            ((Set) fieldC.get(this.targets)).clear();

        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }

    }


    /**
     * Apply NPC Preset
     * @param entity Entity to receive preset
     */
    private void applyNPCPreset (EntityInsentient entity) {
        goals.a(2, new PathfinderGoalLookAtPlayer(entity, EntityHuman.class, 5.0f));
        goals.a(2, new PathfinderGoalRandomLookaround(entity));
    }


    /**
     * Apply owner preset
     * @param entity Entity to receive preset
     * @param protect Whether the entity should
     *                protect its owner.
     */
    private void applyOwnerPreset (EntityInsentient entity, boolean protect) {
        goals.a(2, new PathfinderGoalFollowEntity(entity, this.owner, 1d, 3f, 20f));
        goals.a(4, new PathfinderGoalRandomLookaround(entity));
    }


    /**
     * Apply hostile preset
     * @param entity Entity to receive preset
     */
    private void applyHostilePreset (EntityInsentient entity) {

        EntityCreature creature = (EntityCreature) entity;

        goals.a(2, new PathfinderGoalMeleeAttack(creature, 1.0d, false));
        goals.a(8, new PathfinderGoalLookAtPlayer(entity, EntityHuman.class, 8.0f));
        goals.a(8, new PathfinderGoalRandomLookaround(entity));
        targets.a(1, new PathfinderGoalTargetNearestPlayer(entity));
    }



    /*
        Getters and setters.
     */

    public Entity getEntity () {
        return entity;
    }

    public Entity getOwner () {
        return owner;
    }

    public void setOwner (Entity owner) {
        this.owner = owner;
    }

}
