package net.atomichive.core.util;

/**
 * Experience Util
 * Various utilities for working with player experience.
 */
public class ExperienceUtil {


    /**
     * Level up experience
     * Determines the amount of experience required in order
     * to level up.
     * The experience function is as follows:
     *  (x + 1)^3 + 20
     *  where x is the player's current level.
     *
     * @param level Current player level.
     * @return Total amount of experience required to level up.
     */
    public static int levelUpExperience (int level) {
        return (int) (Math.pow(level + 1, 3) + 20);
    }

}
