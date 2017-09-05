package net.atomichive.core.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Listens to inventory related events.
 */
public class InventoryListener extends BaseListener implements Listener {


    /**
     * Fires whenever a user clicks on an item in an
     * inventory.
     *
     * @param event Inventory click event
     */
    @EventHandler
    void onClick (InventoryClickEvent event) {
        if (event.getSlotType().equals(InventoryType.SlotType.QUICKBAR)) {
            if (event.getSlot() == 8) {
                event.getWhoClicked().sendMessage(ChatColor.RED + "You can't move that!");
                event.setCancelled(true);
            }
        }
    }


    /**
     * Fires whenever an item swaps hands.
     *
     * @param event Player swap hand items event
     */
    @EventHandler
    void onSwap (PlayerSwapHandItemsEvent event) {

        // Gets the item heading to the off hand
        ItemStack stack = event.getOffHandItem();

        if (stack.getType().equals(Material.COMPASS)) {
            event.getPlayer().sendMessage(ChatColor.RED + "You can't move that!");
            event.setCancelled(true);
        }

    }

}
