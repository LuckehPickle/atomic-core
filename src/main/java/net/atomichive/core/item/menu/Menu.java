package net.atomichive.core.item.menu;

import net.atomichive.core.Main;
import net.atomichive.core.listeners.BaseListener;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A custom item menu based on inventories.
 */
public class Menu extends BaseListener implements Listener {

    private final String title;
    private final int size;
    private final MenuClickEventHandler handler;

    private ItemStack[] items;
    private Inventory inventory;


    /**
     * Constructor
     *
     * @param title   Title of the menu, can include chat colors.
     * @param size    Inventory size, must be multiple of 9.
     * @param handler Event handle that is fired whenever a click occurs.
     */
    public Menu (String title, int size, MenuClickEventHandler handler) {

        super();

        this.title = title;
        this.size = size;
        this.handler = handler;

        items = new ItemStack[size];

    }


    /**
     * Adds an item stack to this menu.
     *
     * @param position    Position of item stack in menu.
     * @param material    Item material.
     * @param label       Item stack label.
     * @param description Item stack description.
     * @return Menu for chaining.
     */
    public Menu add (int position, Material material, String label, String description) {
        return add(position, createItemStack(material, label, description));
    }


    /**
     * Adds an item stack to this menu.
     *
     * @param position Position of item stack in menu.
     * @param stack    Item stack to add.
     * @return Menu for chaining.
     */
    public Menu add (int position, ItemStack stack) {
        this.items[position] = stack;
        return this;
    }


    /**
     * Opens this menu for a particular player.
     *
     * @param player Player to open menu for.
     */
    public void open (Player player) {

        // Create inventory if it has not yet been created.
        if (this.inventory == null) {

            // Create inv
            inventory = Bukkit.createInventory(null, size, title);

            // Add item stacks
            for (int i = 0; i < items.length; i++) {
                // Ensure item is not null
                if (items[i] != null) {
                    inventory.setItem(i, items[i]);
                }
            }

        }

        player.openInventory(inventory);

    }


    /**
     * An event which fires whenever an item in this
     * inventory is clicked.
     *
     * @param event Inventory click event.
     */
    @EventHandler
    void onInventoryClick (InventoryClickEvent event) {

        // Ensure this inv was clicked
        if (event.getClickedInventory().equals(this.inventory)) {

            event.setCancelled(true);

            int slot = event.getRawSlot();

            // Ensure slot is within bounds
            if (slot < 0 || slot >= size || items[slot] == null) {
                return;
            }

            final Player player = (Player) event.getWhoClicked();

            // Create a new event
            MenuClickEvent clickEvent = new MenuClickEvent(
                    player,
                    slot,
                    items[slot].getItemMeta().getDisplayName()
            );

            handler.onClick(clickEvent);

            if (clickEvent.willClose()) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(
                        Main.getInstance(),
                        player::closeInventory,
                        1
                );
            }

        }
    }


    /**
     * Creates a new item stack.
     *
     * @param material    Bukkit material.
     * @param label       Item stack label.
     * @param description Item stack description.
     * @return A new item stack.
     */
    private ItemStack createItemStack (Material material, String label, String description) {

        // Create new stack and retrieve its meta
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();

        // Set display name and lore
        meta.setDisplayName(ChatColor.GOLD + label);
        List<String> lore = new ArrayList<>();

        String[] lines = WordUtils.wrap(description, 30).split("\n");
        for (String line : lines) {
            lore.add(ChatColor.RESET + line);
        }

        meta.setLore(lore);
        stack.setItemMeta(meta);

        return stack;

    }

}
