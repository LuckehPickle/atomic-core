package net.atomichive.core.warp;

import net.atomichive.core.exception.UnknownWarpException;

import java.util.ArrayList;
import java.util.List;

/**
 * Warp Manager
 * Keeps track of all currently active warps.
 */
public class WarpManager {

    private static List<Warp> warps = new ArrayList<>();

    public static void add (Warp warp) {
        warps.add(warp);
    }


    /**
     * Get
     * @param name Name of warp to retrieve.
     * @return Warp with corresponding name, null
     *         if not found.
     */
    public static Warp get (String name) {

        // Iterate over warps
        for (Warp warp : warps) {
            if (warp.getName().equalsIgnoreCase(name))
                return warp;
        }

        return null;

    }


    public static List<Warp> getAll () {
        return warps;
    }


    /**
     * Load
     * Retrieves all warps from the database and loads
     * them into memory.
     */
    public static void load () {
        warps = WarpDAO.getAll();
    }


    /**
     * Contains
     * Checks to see if a warp by this name already
     * exists.
     * @param name Name of the warp.
     * @return Whether the warp already exists.
     */
    public static boolean contains (String name) {

        // Iterate over warps
        for (Warp warp : warps) {
            if (warp.getName().equalsIgnoreCase(name))
                return true;
        }

        return false;

    }



    /**
     * Delete
     * Removes a warp from the db, and from
     * the warp manager.
     * @param name Name of the warp to delete.
     * @return deleted warp for output purposes.
     */
    public static Warp delete (String name) throws UnknownWarpException {

        // Ensure warp exists.
        if (!contains(name))
            throw new UnknownWarpException("Warp '" + name + "' does not exist.");

        // Get warp
        Warp warp = get(name);

        // Delete
        WarpDAO.drop(warp);
        warps.remove(warp);

        return warp;

    }



    /**
     * Remove all
     * Removes all warps from the list, but first
     * pushes them all to the db.
     */
    public static void removeAll () {
        warps.clear();
    }

}
