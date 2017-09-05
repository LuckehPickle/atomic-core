package net.atomichive.core.item.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * A class which holds all custom menus.
 * TODO Make these use translations
 */
public enum Menus {

    MAIN (new Menu("Main menu", 9, new MainMenuHandler())
            .add(0, Material.EMPTY_MAP,   "Quests " + ChatColor.GRAY + "[Coming Soon]", "View all your active quests.")
            .add(1, Material.MONSTER_EGG, "Pets " + ChatColor.GRAY + "[Coming Soon]", "Select your current pet.")
            .add(2, Material.GOLD_INGOT,  "Bank " + ChatColor.GRAY + "[Coming Soon]", "This will eventually say how much money you currently own.")
            .add(3, Material.DIAMOND,     "Achievements " + ChatColor.GRAY + "[Coming Soon]", "This will eventually say something about all your amazing achievements."));

    private final Menu menu;

    /**
     * Constructor
     *
     * @param menu Custom menu
     */
    Menus (Menu menu) {
        this.menu = menu;
    }


    /*
        Getters.
     */

    public Menu getMenu () {
        return menu;
    }

}
