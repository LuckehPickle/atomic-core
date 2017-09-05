package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.item.ItemUtil;
import net.atomichive.core.player.AtomicPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Listeners for events related to the use of bows.
 */
public class BowListener extends BaseListener implements Listener {

    @EventHandler
    void onShoot (EntityShootBowEvent event) {

        Entity entity = event.getEntity();

        // Handle players
        if (entity instanceof Player) {

            AtomicPlayer player = Main.getInstance().getPlayerManager().get((Player) entity);
            ItemStack bow = event.getBow();

            // Ensure player is high enough level
            if (player.getLevel() < ItemUtil.getLevel(bow)) {
                event.setCancelled(true);
                entity.sendMessage(ChatColor.RED + "This bow is too high level for you!");
            }

        }

    }

}
