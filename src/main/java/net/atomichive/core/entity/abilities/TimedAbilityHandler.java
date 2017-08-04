package net.atomichive.core.entity.abilities;

import org.bukkit.entity.Entity;

import java.util.Collection;
import java.util.List;

/**
 * Timed Ability Handler
 * Wraps around a regular ability for time
 * based execution.
 */
public class TimedAbilityHandler extends GenericAbilityHandler {

    private int tick = 0;
    private final int maxTicks;


    /**
     * Timed Ability Handler
     * @param ability Base ability to handle.
     * @param target  Ability target.
     * @param radius  Maximum target radius (if applicable).
     */
    public TimedAbilityHandler (Ability ability, Ability.Target target, int radius) {
        this(ability, target, radius, 10);
    }

    /**
     * Timed Ability Handler
     * @param ability  Base ability to handle.
     * @param target   Ability target.
     * @param radius   Maximum target radius (if applicable).
     * @param maxTicks Ticks before the ability is fired.
     */
    public TimedAbilityHandler (Ability ability, Ability.Target target, int radius, int maxTicks) {
        super(ability, target, radius);
        this.maxTicks = maxTicks;
    }


    /**
     * Tick
     * @param source Ability source
     */
    public void tick (Entity source) {

        tick++;

        if (tick >= maxTicks) {
            tick = 0;
            super.run(source);
        }

    }


}
