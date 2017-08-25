package net.atomichive.core.item;

import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;

/**
 * Rarity
 * Defines the rarity of a particular item.
 */
public enum Rarity {

    JUNK      (ChatColor.RED),
    QUEST     (ChatColor.GOLD),
    COMMON    (ChatColor.GRAY),
    UNCOMMON  (ChatColor.BLUE),
    RARE      (ChatColor.GREEN),
    EPIC      (ChatColor.GOLD),
    LEGENDARY (ChatColor.DARK_PURPLE);


    private ChatColor color;

    Rarity (ChatColor color) {
        this.color = color;
    }


    /*
        Getters and setters.
     */


    public ChatColor getColor () {
        return color;
    }

    @Override
    public String toString () {
        return Util.toTitleCase(this.name());
    }

}
