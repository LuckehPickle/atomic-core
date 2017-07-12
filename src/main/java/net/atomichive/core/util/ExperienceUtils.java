package net.atomichive.core.util;

/**
 * Experience Utils
 * Utilities which help convert from experience to levels and
 * back again. Also contains utils which convert from internal
 * experience levels to in game experience levels.
 */
public class ExperienceUtils {



    /**
     * Levels to experience
     * @param level Player's level.
     * @return Corresponding amount of experience.
     */
    public static int levelsToExperience (double level) {

        /*
            f(x) = 0.4x^2 + 50
         */
        return (int) Math.round(0.4 * Math.pow(level, 2) + 50);

    }



    /**
     * Experience to levels
     * @param experience Amount of experience.
     * @return Corresponding player level.
     */
    public static double experienceToLevels (int experience) {

        /*
            f(x) = sqrt(10x - 500) / 2
         */
        return Math.sqrt(10 * experience - 500) / 2;
    }

}