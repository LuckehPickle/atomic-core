package net.atomichive.core.entity;

import net.atomichive.core.entity.abilities.TimedAbilityHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * Asynchronous Entity Clock
 */
public class EntityAsyncClock extends BukkitRunnable {

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
