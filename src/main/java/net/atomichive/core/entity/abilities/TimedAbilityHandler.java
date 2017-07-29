package net.atomichive.core.entity.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.List;

/**
 * Timed Ability Handler
 * Wraps around a regular ability for time
 * based execution.
 */
public class TimedAbilityHandler {

    private final Ability ability;
    private final Ability.Target target;

    private int tick = 0;
    private int maxTicks = 10;
    private int radius = 30;


    /**
     * Timed Ability Handler
     * @param ability Base ability.
     * @param target  Ability target.
     */
    public TimedAbilityHandler (Ability ability, Ability.Target target) {
        this.ability = ability;
        this.target = (target == null) ? Ability.Target.CLOSEST_PLAYER : target;
    }


    /**
     * Tick
     * @param source Ability source
     */
    public void tick (Entity source) {

        tick++;

        if (tick >= maxTicks) {
            tick = 0;

            Collection<Entity> targets = Ability.Target.get(this.target, source, radius);

            // Iterate over targets and execute ability
            for (Entity target : targets)
                ability.execute(source, target);

        }

    }


    /*
        Getters and setters.
     */

    public void setRadius (int radius) {
        this.radius = radius;
    }

    public void setMaxTicks (int maxTicks) {
        this.maxTicks = maxTicks;
    }

}
