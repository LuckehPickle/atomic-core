package net.atomichive.core.entity.abilities;

import net.atomichive.core.util.SmartMap;
import org.bukkit.entity.Entity;

/**
 * Ability Ignite
 * Sets target on fire.
 */
public class AbilityIgnite implements Ability {


    private final int ticks;


    /**
     * Ability
     *
     * @param attributes Ability attributes.
     */
    public AbilityIgnite (SmartMap attributes) {
        this.ticks = attributes.getInteger("ticks", 40);
    }


    /**
     * Execute
     *
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) {
        target.setFireTicks(ticks);
    }

}
