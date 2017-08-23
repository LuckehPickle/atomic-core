package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.entity.ActiveEntity;
import net.atomichive.core.entity.EntityManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Entity damage listener
 * Handles entity damage events.
 */
public class EntityDamageListener extends BaseListener implements Listener {


    /**
     * On entity damage
     *
     * @param event Entity damage event.
     */
    @EventHandler
    public void onEntityDamage (EntityDamageEvent event) {

        // Get entity
        Entity entity = event.getEntity();

        // By default, making an entity invulnerable seems to
        // have no effect. So we cancel damage instead
        if (entity.isInvulnerable()) {
            event.setCancelled(true);
        }

    }


    /**
     * On entity damage entity
     * An event which occurs whenever an entity damages
     * another entity.
     *
     * @param event Entity damage entity event
     */
    @EventHandler
    public void onEntityDamageEntity (EntityDamageByEntityEvent event) {

        // Handle damager first
        EntityManager manager = Main.getInstance().getEntityManager();
        Entity entity = event.getDamager();
        ActiveEntity activeEntity = manager.getActiveEntity(entity);

        if (entity instanceof Firework) {
            event.setCancelled(true);
            return;
        }

        // Check if entity is active entity
        if (activeEntity != null) {
            // Run on attack abilities
            activeEntity.runOnAttack(entity, event.getEntity());
        }

        // Handle damagee
        entity = event.getEntity();
        activeEntity = manager.getActiveEntity(entity);

        // Check if entity is active entity
        if (activeEntity != null) {
            // Run on damage abilities
            activeEntity.runOnDamage(entity, event.getDamager());
        }

    }

}
