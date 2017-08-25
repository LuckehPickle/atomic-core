package net.atomichive.core.nms;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.nms.goals.VolatileGoal;
import net.atomichive.core.util.Util;
import net.minecraft.server.v1_12_R1.PathfinderGoalSelector;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * A volatile pathfinding goal selector.
 */
public class VolatileGoalSelector {


    private final PathfinderGoalSelector selector;


    /**
     * Type of pathfinding selector.
     */
    public enum Type {

        GOAL   ("goalSelector"),
        TARGET ("targetSelector");

        private String fieldName;

        /**
         * Constructor
         *
         * @param fieldName Name of the NMS field.
         */
        Type (String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldName () {
            return fieldName;
        }

    }


    /**
     * Constructor
     *
     * @param selector NMS Pathfindeer goal selector.
     */
    public VolatileGoalSelector (PathfinderGoalSelector selector) {
        this.selector = selector;
    }


    /**
     * Adds a new goal to this goal selector.
     *
     * @param priority Priority level.
     * @param goal     Volatile goal to add.
     * @throws CustomObjectException if the goal could not be created.
     */
    public void add (int priority, VolatileGoal goal) throws CustomObjectException {
        this.selector.a(priority, goal.toNMS());
    }


    /**
     * Clears out this goal selector
     */
    public void clear () {

        Field fieldB = Util.getPrivateField(
                PathfinderGoalSelector.class,
                "b"
        );

        Field fieldC = Util.getPrivateField(
                PathfinderGoalSelector.class,
                "c"
        );

        try {

            if (fieldB != null)
                ((Set) fieldB.get(selector)).clear();

            if (fieldC != null)
                ((Set) fieldC.get(selector)).clear();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


}
