package net.atomichive.core.entity.abilities;

import org.bukkit.entity.Entity;

/**
 * Ability Lightning
 * Strikes lightning at targets.
 */
public class AbilityLightning implements Ability {


    /**
     * Execute
     *
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) {
        target.getWorld().strikeLightningEffect(target.getLocation());
    }

}
