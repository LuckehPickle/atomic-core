package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

/**
 * Fired whenever a non-player entity (such as an enderman)
 * attempts to teleport.
 */
public class TeleportListener extends BaseListener implements Listener {

    @EventHandler
    void onEntityTeleport (EntityTeleportEvent event) {

        // Get entity which fired event
        Entity entity = event.getEntity();

        List<MetadataValue> items = entity.getMetadata("prevent_teleport");

        // Iterate over metadata
        for (MetadataValue value : items) {
            if (value.getOwningPlugin().equals(Main.getInstance())) {
                event.setCancelled(value.asBoolean());
            }
        }

    }

}
