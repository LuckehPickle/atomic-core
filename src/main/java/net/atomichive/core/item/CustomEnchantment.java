package net.atomichive.core.item;

/**
 * An enum of all possible custom item enchantments.
 */
public enum CustomEnchantment {

    XP_BOOST      ("XP boost",        2), // Increases amount of xp earned
    FIRE_ASPECT   ("Fire aspect",     2), // Causes targets to be ignited on hit
    CRIT_CHANCE   ("Critical chance", 3), // Increase the chance of a critical hit
    POWER         ("Power",           5), // Increases the distance/speed of arrows
    QUICK_FINGERS ("Quick fingers",   3), // Shoots multiple arrows at once
    FLAME         ("Flame",           2); // Causes arrows to ignite targets


    private String display;
    private int maxLevel;


    CustomEnchantment (String display, int maxLevel) {
        this.display  = display;
        this.maxLevel = maxLevel;
    }


    /**
     * Retrieves an enchantment by its display name.
     *
     * @param display Display name of enchantment.
     * @return Enchantment with matching display name, or null.
     */
    public static CustomEnchantment getByDisplayName (String display) {

        // Iterate over each enchantment
        for (CustomEnchantment enchantment : CustomEnchantment.values()) {
            if (enchantment.getDisplay().equalsIgnoreCase(display))
                return enchantment;
        }

        return null;

    }


    /**
     * Determines whether the given level is valid
     * for the current enchantment.
     *
     * @param level Level to validate.
     * @return Whether the level is valid.
     */
    public boolean isLevelValid (int level) {
        return (level > 0) && (level <= this.getMaxLevel());
    }


    /*
        Getters and setters.
     */

    public String getDisplay () {
        return display;
    }

    public int getMaxLevel () {
        return maxLevel;
    }

}
