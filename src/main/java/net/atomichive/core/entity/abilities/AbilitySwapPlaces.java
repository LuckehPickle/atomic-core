package net.atomichive.core.entity.abilities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Swaps the source and targets locations.
 */
public class AbilitySwapPlaces implements Ability {


    /**
     * Execute
     *
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) {

        // Get current target location
        Location temp = target.getLocation();

        // Swap locations
        target.teleport(source);
        source.teleport(temp);

    }

}
