package net.atomichive.core.entity.abilities;

import net.atomichive.core.util.SmartMap;
import org.bukkit.entity.Entity;

import java.util.Map;

/**
 * Ability Ignite
 * Sets target on fire.
 */
public class AbilityIgnite implements Ability {


    private SmartMap attributes;


    /**
     * Ability
     * @param attributes Ability attributes.
     */
    public AbilityIgnite (SmartMap attributes) {
        this.attributes = attributes;
    }


    /**
     * Execute
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) {

        // Get ticks
        double ticks = attributes.get(Double.class, "ticks", 40.0);

        target.setFireTicks((int) ticks);

    }

}
