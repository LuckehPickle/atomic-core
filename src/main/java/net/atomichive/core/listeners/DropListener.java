package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.item.MenuItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Listens for dropped items.
 */
public class DropListener extends BaseListener implements Listener {

    @EventHandler
    void onDropItem (PlayerDropItemEvent event) {

        ItemStack stack = event.getItemDrop().getItemStack();
        Player player = event.getPlayer();

        // Cancel drop if item is menu trigger
        if (stack.getType().equals(Material.COMPASS)) {
            player.sendMessage(ChatColor.RED + "You might need that!");
            event.setCancelled(true);
        }

    }

}
