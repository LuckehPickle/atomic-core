package net.atomichive.core.item;

import org.bukkit.ChatColor;

/**
 * Rarity
 * Defines the rarity of a particular item.
 */
public enum Rarity {

    JUNK      ("Junk item",      ChatColor.RED),
    COMMON    ("Common item",    ChatColor.BLUE),
    UNCOMMON  ("Uncommon item",  ChatColor.GRAY),
    RARE      ("Rare item",      ChatColor.YELLOW),
    EPIC      ("Epic item",      ChatColor.LIGHT_PURPLE),
    LEGENDARY ("Legendary item", ChatColor.GOLD);


    private String lore;
    private ChatColor color;

    Rarity (String lore, ChatColor color) {
        this.lore  = lore;
        this.color = color;
    }


    /*
        Getters and setters.
     */

    public String getLore () {
        return lore;
    }

    public ChatColor getColor () {
        return color;
    }

    @Override
    public String toString () {
        return this.getLore();
    }

}
