package net.atomichive.core.entity.abilities;

import net.atomichive.core.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

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
        NEARBY_ENTITIES;

        /**
         * Get
         * Retrieves all entities which match the
         * target.
         * @param source Source entity.
         * @param radius Maximum radius.
         * @return Matching entities.
         */
        public static Collection<Entity> get (Target target, Entity source, int radius) {

            // Get nearby entities
            Collection<Entity> entities = source.getWorld().getNearbyEntities(
                    source.getLocation(),
                    radius, radius, radius
            );


            // Remove non-players if necessary
            if (target.equals(Target.CLOSEST_PLAYER) || target.equals(Target.NEARBY_PLAYERS)) {
                entities.removeIf(e -> !(e instanceof Player));
            }

            switch (target) {
                case CLOSEST_ENTITY:
                case CLOSEST_PLAYER:
                    return Util.getClosest(source.getLocation(), entities);
                case NEARBY_ENTITIES:
                case NEARBY_PLAYERS:
                    return entities;
            }

            return null;
        }

    }

    /**
     * Execute
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    void execute (Entity source, Entity target);

}
