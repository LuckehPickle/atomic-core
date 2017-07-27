package net.atomichive.core.entity.abilities;

import net.atomichive.core.util.SmartMap;
import org.bukkit.entity.Entity;

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
        SELF,
        TARGET

    }

    /**
     * Execute
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    void execute (Entity source, Entity target);

}
