package net.atomichive.core.item;

import com.google.gson.annotations.SerializedName;
import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.Util;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Contains all the information needed to construct
 * a custom item stack. Attributes here are loaded through
 * with Gson.
 */
@SuppressWarnings({"unused", "MismatchedReadAndWriteOfArray"})
public class CustomItem {

    private String name;
    private String lore;
    private String material;
    private Rarity rarity = Rarity.COMMON;
    private int level = -1;
    private String[] enchantments = new String[0];

    @SerializedName("display_name")
    private String displayName;


    /**
     * Constructs an item stack from this custom item.
     *
     * @param amount Number of items in stack.
     * @return An item stack of this custom item.
     */
    public ItemStack getItemStack (int amount) throws CustomObjectException {

        Material material;

        // Attempt to get material
        try {
            material = Material.valueOf(this.material.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomObjectException(String.format(
                    "Unknown material '%s' in custom item '%s'.",
                    this.material,
                    this.name
            ));
        }

        // Create item stack and apply meta data
        ItemStack stack = new ItemStack(material, amount);
        setStackMeta(stack);
        addEnchantments(stack);

        return stack;

    }


    /**
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

        List<String> lore = new ArrayList<>();

        // Description
        String[] lines = WordUtils.wrap(this.lore, 30).split("\n");
        for (String line : lines) {
            lore.add(ChatColor.DARK_GRAY + line);
        }

        // Rarity
        lore.add(String.format(
                "%s[%s]",
                rarity.getColor(),
                rarity.toString()
        ));

        // Minimum level
        if (level != -1) {
            lore.add(ChatColor.RED + "Level " + level);
        }

        meta.setLore(lore);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        stack.setItemMeta(meta);

    }


    /**
     * Adds any custom enchantments to the item stack.
     * This involves several validation steps.
     *
     * @param stack Item stack to add enchantments to.
     */
    private void addEnchantments (ItemStack stack) throws CustomObjectException {

        // Ensure there is at least one enchantment defined.
        if (this.enchantments.length == 0) return;

        HashMap<CustomEnchantment, Integer> enchantments = new HashMap<>();

        // Iterate over enchants
        for (String string : this.enchantments) {

            // Split at first space
            String[] components = string.split(" ", 2);
            CustomEnchantment enchantment;
            int level = 1;

            // Retrieve desired enchantment
            try {
                enchantment = CustomEnchantment.valueOf(components[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown enchantment '%s' for custom item '%s'.",
                        components[0],
                        this.name
                ));
            }

            // Ensure enchantment hasn't already been added
            if (enchantments.containsKey(enchantment)) {
                throw new CustomObjectException(String.format(
                        "Custom item '%s' already has the enchantment '%s'.",
                        this.name,
                        enchantment.name()
                ));
            }

            // Check for level
            if (components.length == 2) {

                // Ensure level is an int
                if (!Util.isInteger(components[1])) {
                    throw new CustomObjectException(String.format(
                            "Invalid level '%s' (not a number) for custom enchantment '%s' on custom item '%s'.",
                            components[1],
                            enchantment.name(),
                            this.name
                    ));
                }

                // Parse as int
                level = Integer.parseInt(components[1]);

            }

            // Ensure level is valid
            if (!enchantment.isLevelValid(level)) {
                throw new CustomObjectException(String.format(
                        "Invalid level '%s' for custom enchantment '%s' on custom item '%s'.",
                        components[1],
                        enchantment.name(),
                        this.name
                ));
            }

            enchantments.put(enchantment, level);

        }


        if (!enchantments.isEmpty()) {
            addEnchantments(stack, enchantments);
        }

    }


    /**
     * Applies enchantments to a particular item stack. Note that
     * no validation is done here.
     *
     * @param stack Item stack to receive enchantments.
     * @param enchantments Hash map of enchantments to add.
     */
    private void addEnchantments (ItemStack stack, HashMap<CustomEnchantment, Integer> enchantments) {

        // Add glowing effect
        if (!stack.containsEnchantment(Enchantment.ARROW_INFINITE)) {
            stack.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        }

        // Add to lore
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = meta.getLore();

        lore.add("");

        // Iterate over enchantments to add
        for (CustomEnchantment enchantment : enchantments.keySet()) {
            lore.add(String.format(
                    "%s%s %s",
                    ChatColor.GRAY,
                    enchantment.getDisplay(),
                    Util.toRomanNumeral(enchantments.get(enchantment))
            ));
        }

        meta.setLore(lore);
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
