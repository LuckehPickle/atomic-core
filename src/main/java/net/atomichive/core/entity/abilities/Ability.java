package net.atomichive.core.entity.abilities;

import net.atomichive.core.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Base Ability
 */
public interface Ability {

    /**
     * Target enum
     * Targeting types.
     */
    enum Target {

        CLOSEST_PLAYER,
        CLOSEST_ENTITY,
        NEARBY_PLAYERS,
        NEARBY_ENTITIES,
        SELF;


        /**
         * Get targets
         * Retrieves all matching targets.
         * @param source Source entity.
         * @param target Target type.
         * @param radius Maximum targeting radius (if applicable).
         * @return Matching targets.
         */
        public static List<Entity> getTargets (Entity source, Target target, int radius) {

            List<Entity> entities = new ArrayList<>();

            if (target.equals(SELF)) {
                entities.add(source);
                return entities;
            }

            // Get nearby entities
            entities.addAll(getNearby(source, radius));

            // Remove non-players if necessary
            if (target.equals(NEARBY_PLAYERS) || target.equals(CLOSEST_PLAYER))
                entities.removeIf((entity) -> !(entity instanceof Player));

            switch (target) {
                case CLOSEST_ENTITY:
                case CLOSEST_PLAYER:
                    return Util.getClosest(source.getLocation(), entities);
                default:
                    return entities;
            }

        }


        /**
         * Get nearby
         * Returns a collection of all nearby entities.
         * @param source Source entity.
         * @param radius Max radius to search.
         * @return A collection of all entities within the given radius.
         */
        private static Collection<Entity> getNearby (Entity source, int radius) {
            return source.getWorld().getNearbyEntities(
                    source.getLocation(),
                    radius, radius, radius
            );
        }

    }


    /**
     * Execute
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    void execute (Entity source, Entity target);

}
