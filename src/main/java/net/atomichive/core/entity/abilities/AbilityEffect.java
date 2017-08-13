package net.atomichive.core.entity.abilities;

import net.atomichive.core.util.SmartMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Ability Effect
 * Applies affects to the target.
 */
public class AbilityEffect implements Ability {


    private final String effect;
    private final int duration;
    private final int amplifier;
    private final boolean hasParticles;


    public AbilityEffect (SmartMap attributes) {
        effect = attributes.get(String.class, "effect");
        duration = attributes.getInteger("duration", 30);
        amplifier = attributes.getInteger("amplifier", 0);
        hasParticles = attributes.get(Boolean.class, "has_particles", true);
    }


    /**
     * Execute
     *
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) {


        // Ensure target is living entity
        if (target instanceof LivingEntity) {

            // Get living entity
            LivingEntity living = (LivingEntity) target;

            // Get effect
            PotionEffectType effect = PotionEffectType.getByName(this.effect);

            if (effect != null) {
                // Add effect
                living.addPotionEffect(new PotionEffect(
                        effect,
                        duration,
                        amplifier,
                        true,
                        hasParticles
                ));
            }
        }
    }

}
