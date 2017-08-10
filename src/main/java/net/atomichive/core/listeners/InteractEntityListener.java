package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

/**
 * Interact Entity Listener
 * Fired whenever a player interacts with an
 * entity.
 */
public class InteractEntityListener extends BaseListener implements Listener {

    @EventHandler
    public void onInteract (PlayerInteractEntityEvent event) {

        // Get clicked and source player
        Entity clicked = event.getRightClicked();

        if (clicked.hasMetadata("is_rideable")) {
            List<MetadataValue> metadata = clicked.getMetadata("is_rideable");
            for (MetadataValue value : metadata) {
                if (value.getOwningPlugin().equals(Main.getInstance()))
                    event.setCancelled(value.asBoolean());
            }
        }

    }
}
