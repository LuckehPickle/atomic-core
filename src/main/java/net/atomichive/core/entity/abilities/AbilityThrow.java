package net.atomichive.core.entity.abilities;

import net.atomichive.core.util.SmartMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 * Ability Throw
 */
public class AbilityThrow implements Ability {


    private final double multiplier;


    /**
     * Ability
     * @param attributes Ability attributes.
     */
    public AbilityThrow (SmartMap attributes) {
        this.multiplier = attributes.get(Double.class, "multiplier", 1.0d);
    }


    /**
     * Execute
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) {

        // Get delta vector
        Vector delta = target.getLocation().toVector().subtract(source.getLocation().toVector());

        // Normalize and multiply
        delta.normalize();
        delta.setY(0.25);
        delta.multiply(multiplier);

        target.setVelocity(delta);

    }

}
