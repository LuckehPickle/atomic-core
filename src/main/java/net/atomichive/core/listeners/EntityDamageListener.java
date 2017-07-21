package net.atomichive.core.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Entity damage listener
 * Handles entity damage events.
 */
public class EntityDamageListener extends BaseListener implements Listener {

    /**
     * On entity damage
     * @param event Entity damage event.
     */
    @EventHandler
    public void onEntityDamage (EntityDamageEvent event) {

        // Get entity
        Entity entity = event.getEntity();

        if (entity.isInvulnerable())
            event.setCancelled(true);

    }

}
