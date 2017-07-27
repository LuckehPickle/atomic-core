package net.atomichive.core.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

/**
 * Entity Change Block Listener
 */
public class EntityChangeBlockListener extends BaseListener implements Listener {

    /**
     * On change
     * @param event Entity change block event.
     */
    @EventHandler
    public void onChange (EntityChangeBlockEvent event) {

        // Get entity
        Entity entity = event.getEntity();

        // Check if entity has metadata
        List<MetadataValue> values = entity.getMetadata("remove_on_ground");

        if (values.size() >= 1)
            event.setCancelled(values.get(0).asBoolean());

    }

}
