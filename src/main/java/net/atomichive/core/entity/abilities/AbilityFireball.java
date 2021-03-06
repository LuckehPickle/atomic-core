package net.atomichive.core.entity.abilities;

import net.atomichive.core.util.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

/**
 * Ability Fireball
 */
public class AbilityFireball implements Ability {

    /**
     * Execute
     *
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) {

        // Spawn snowball at source entity
        Fireball ball = (Fireball) source.getWorld().spawnEntity(
                source.getLocation(),
                EntityType.FIREBALL
        );

        ball.setShooter((ProjectileSource) source);

        Vector deltaV = Util.getDeltaV(source, target);
        deltaV.normalize();

        ball.setDirection(deltaV);

    }
}
