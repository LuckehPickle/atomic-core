package net.atomichive.core.listeners;

import net.atomichive.core.exception.MetadataException;
import net.atomichive.core.util.Util;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

/**
 * Listens for slime split events.
 */
public class SlimeSplitListener extends BaseListener implements Listener {

    /**
     * Occurs whenever a slime splits.
     *
     * @param event Slime split event.
     */
    @EventHandler
    void onSlimeSplit (SlimeSplitEvent event) {

        // Get slime
        Slime slime = event.getEntity();

        try {
            boolean preventSplit = Util.getMetadata(slime, "prevent_split");
            event.setCancelled(preventSplit);
        } catch (MetadataException ignored) {}

    }

}
