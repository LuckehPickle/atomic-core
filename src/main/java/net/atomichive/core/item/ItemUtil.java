package net.atomichive.core.item;

import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A collection of custom item related utilities.
 */
public class ItemUtil {

    /**
     * Retrieves this items level, or -1 if none are applied.
     *
     * @param stack Item stack to retrieve level from.
     * @return Items minimum level.
     */
    public static int getLevel (ItemStack stack) {

        if (!stack.hasItemMeta()) return -1;
        if (!stack.getItemMeta().hasLore()) return -1;

        // Get lore
        List<String> lore = stack.getItemMeta().getLore();

        for (String str : lore) {
            if (str.startsWith(ChatColor.RED + "Level ")) {
                String level = str.replace(ChatColor.RED + "Level ", "");
                if (Util.isInteger(level)) {
                    return Integer.parseInt(level);
                }
            }
        }

        return -1;

    }

}
