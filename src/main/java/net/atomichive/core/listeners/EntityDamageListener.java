package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.entity.ActiveEntity;
import net.atomichive.core.entity.EntityManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Handles entity damage events.
 */
public class EntityDamageListener extends BaseListener implements Listener {


    /**
     * Whenever an entity takes damage.
     *
     * @param event Entity damage event.
     */
    @EventHandler
    void onEntityDamage (EntityDamageEvent event) {

        // Get entity
        Entity entity = event.getEntity();

        // By default, making an entity invulnerable seems to
        // have no effect. So we cancel damage instead
        if (entity.isInvulnerable()) {
            event.setCancelled(true);
        }

    }


    /**
     * An event which occurs whenever an entity damages
     * another entity.
     *
     * @param event Entity damage entity event
     */
    @EventHandler
    void onEntityDamageEntity (EntityDamageByEntityEvent event) {

        // Handle damager first
        EntityManager manager = Main.getInstance().getEntityManager();
        Entity damager = event.getDamager();
        ActiveEntity activeEntity = manager.getActiveEntity(damager);

        // Prevent fireworks from damaging entities
        if (damager instanceof Firework) {
            event.setCancelled(true);
            return;
        }

        // Cancel event if weapon used is too high level
        if (damager instanceof Player) {

        }


        // Check if entity is active entity
        if (activeEntity != null) {
            // Run on attack abilities
            activeEntity.runOnAttack(damager, event.getEntity());
        }

        // Handle damagee
        Entity target = event.getEntity();
        activeEntity = manager.getActiveEntity(target);

        // Check if entity is active entity
        if (activeEntity != null) {
            activeEntity.runOnDamage(target, event.getDamager());
        }

    }

}
