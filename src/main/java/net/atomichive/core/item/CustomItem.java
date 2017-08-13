package net.atomichive.core.item;

import com.google.gson.annotations.SerializedName;
import net.atomichive.core.exception.CustomElementException;
import net.atomichive.core.util.CommandUtil;
import net.atomichive.core.util.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Custom Item Definition
 */
public class CustomItem {

    private String name;
    private String material;

    @SerializedName("display_name")
    private String displayName;


    /**
     * Get item stack
     *
     * @param amount Number of items in stack.
     * @return An item stack of this custom item.
     */
    public ItemStack getItemStack (int amount) throws CustomElementException {

        // Attempt to get material
        Material material = Util.getEnumValue(Material.class, this.material);

        // Ensure material exists
        if (material == null)
            throw new CustomElementException(
                    "Unknown material %s in custom item %s.",
                    this.material,
                    this.name
            );

        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = stack.getItemMeta();

        if (displayName != null) {
            meta.setDisplayName(CommandUtil.handleColorCodes(displayName));
        } else {
            meta.setDisplayName(Util.toTitleCase(name));
        }

        stack.setItemMeta(meta);

        return stack;

    }


    @Override
    public String toString () {
        return String.format("%s [%s]", this.name, this.material);
    }

    /*
        Getters and setters.
     */

    public String getName () {
        return name;
    }

    public String getMaterial () {
        return material;
    }

    public String getDisplayName () {
        return displayName;
    }

}
