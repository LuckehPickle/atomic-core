package net.atomichive.core.entity;

import net.atomichive.core.entity.abilities.TimedAbilityHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * Entity Clock
 * This clocks entire purpose is essentially just
 * to tick entity abilities.
 */
public class EntityClock extends BukkitRunnable {

    @Override
    public void run () {

        // Get all active entities
        List<ActiveEntity> entities = EntityManager.getActiveEntities();

        // Tick all entities
        for (ActiveEntity entity : entities)
            for (TimedAbilityHandler handler : entity.getOnTimer())
                handler.tick(entity.getEntity());

    }

}
