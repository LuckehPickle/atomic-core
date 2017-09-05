package net.atomichive.core.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Menu trigger item stack.
 */
public class MenuItemStack extends ItemStack {

    public MenuItemStack () {

        super(Material.COMPASS);

        ItemMeta meta = super.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Open Menu");

        super.setItemMeta(meta);

    }

}
