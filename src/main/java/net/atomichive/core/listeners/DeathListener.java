package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Entity death listener
 * Listens for entity deaths
 */
public class DeathListener extends BaseListener implements Listener {

    @EventHandler
    void onDeath (EntityDeathEvent event) {
        Main.getInstance().getEntityManager()
                .remove(event.getEntity());
    }

}
