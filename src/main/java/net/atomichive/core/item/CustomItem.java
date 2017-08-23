package net.atomichive.core.item;

import com.google.gson.annotations.SerializedName;
import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.Util;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom Item
 * Contains all the information needed to construct
 * a custom item stack.
 */
public class CustomItem {


    private String name;
    private String lore;
    private String material;
    private Rarity rarity = Rarity.COMMON;

    @SerializedName("display_name")
    private String displayName;

    private int level = 1;


    /**
     * Get item stack
     *
     * @param amount Number of items in stack.
     * @return An item stack of this custom item.
     */
    public ItemStack getItemStack (int amount) throws CustomObjectException {

        // Attempt to get material
        Material material = Util.getEnumValue(Material.class, this.material);

        // Ensure material exists
        if (material == null) {
            throw new CustomObjectException(String.format(
                    "Unknown material %s in custom item %s.",
                    this.material,
                    this.name
            ));
        }

        ItemStack stack = new ItemStack(material, amount);
        setStackMeta(stack);
        return stack;

    }


    /**
     * Set stack meta
     * Applies appropriate item meta to this stack.
     *
     * @param stack ItemStack to apply meta data to.
     */
    private void setStackMeta (ItemStack stack) {

        ItemMeta meta = stack.getItemMeta();

        // Determine display name
        String displayName = (this.displayName != null) ?
                this.displayName : Util.toTitleCase(name);

        // Ensure rarity is not null
        if (rarity == null)
            rarity = Rarity.COMMON;

        meta.setDisplayName(ChatColor.GRAY + displayName);


        // Lore
        List<String> lore = new ArrayList<>();

        // Description
        String[] lines = WordUtils.wrap(this.lore, 30).split("\n");

        for (String line : lines) {
            lore.add(ChatColor.DARK_GRAY + line);
        }

        // Rarity
        lore.add(rarity.getColor() + rarity.getLore());

        // Minimum level
        if (!rarity.equals(Rarity.JUNK))
            lore.add(ChatColor.RED + "Level " + level);

        // Set Lore
        meta.setLore(lore);


        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        stack.setItemMeta(meta);

    }


    @Override
    public String toString () {
        return String.format("%s (%s) [%s]", this.name, this.rarity, this.material);
    }

    /*
        Getters and setters.
     */

    public String getLore () {
        return lore;
    }

    public String getMaterial () {
        return material;
    }

    public Rarity getRarity () {
        return rarity;
    }

    public String getName () {
        return name;
    }

    public String getDisplayName () {
        return displayName;
    }

    public int getLevel () {
        return level;
    }

}
