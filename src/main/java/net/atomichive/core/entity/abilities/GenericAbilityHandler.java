package net.atomichive.core.entity.abilities;

import net.atomichive.core.Main;
import net.atomichive.core.exception.AbilityException;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.logging.Level;

/**
 * Wraps around an ability to add target support.
 */
public class GenericAbilityHandler {


    private final Ability ability;
    private final Ability.Target target;
    private final int radius;


    /**
     * Generic ability handler
     *
     * @param ability Base ability to handle
     * @param target  Ability target.
     */
    public GenericAbilityHandler (Ability ability, Ability.Target target) {
        this(ability, target, 30);
    }

    /**
     * Generic Ability Handle
     *
     * @param ability Base ability to handle.
     * @param target  Ability target.
     * @param radius  Maximum target radius (if applicable).
     */
    public GenericAbilityHandler (Ability ability, Ability.Target target, int radius) {
        this.ability = ability;
        this.target = target;
        this.radius = radius;
    }


    /**
     * Finds applicable targets and executes related
     * ability.
     *
     * @param source Source entity
     */
    public void run (Entity source) {

        // Retrieve targets
        List<Entity> targets = Ability.Target.getTargets(source, target, radius);

        // Iterate over targets, execute ability
        for (Entity target : targets) {
            try {
                ability.execute(source, target);
            } catch (AbilityException e) {
                Main.getInstance().log(Level.SEVERE, e.getMessage());
            }
        }

    }


    /**
     * Executes related ability with specified
     * target.
     *
     * @param source Source entity.
     * @param target Ability target (can be overwritten if
     *               target is specified).
     */
    public void run (Entity source, Entity target) {

        try {
            // Check if target has been specified
            if (this.target == null) {
                ability.execute(source, target);
                return;
            } else if (this.target == Ability.Target.SELF) {
                ability.execute(target, source);
                return;
            }
        } catch (AbilityException e) {
            Main.getInstance().log(Level.SEVERE, e.getMessage());
        }

        run(source);

    }


    public Ability getAbility () {
        return ability;
    }

    public Ability.Target getTarget () {
        return target;
    }

}
