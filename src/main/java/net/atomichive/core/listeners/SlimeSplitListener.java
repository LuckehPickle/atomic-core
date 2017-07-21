package net.atomichive.core.listeners;

import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

/**
 * Slime split listener
 * Listens for slime split events.
 */
public class SlimeSplitListener extends BaseListener implements Listener {

    /**
     * On slime split
     * Occurs whenever a slime splits.
     * @param event Slime split event.
     */
    @EventHandler
    public void onSlimeSplit (SlimeSplitEvent event) {

        // Get slime
        Slime slime = event.getEntity();

        List<MetadataValue> values = slime.getMetadata("prevent_split");

        if (values.size() >= 1)
            event.setCancelled(values.get(0).asBoolean());

    }

}
