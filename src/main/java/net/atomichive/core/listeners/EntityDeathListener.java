package net.atomichive.core.listeners;

import net.atomichive.core.entity.EntityManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Entity death listener
 * Listens for entity deaths
 */
public class EntityDeathListener extends BaseListener implements Listener {

    @EventHandler
    public void onDeath (EntityDeathEvent event) {
        EntityManager.remove(event.getEntity());
    }

}
