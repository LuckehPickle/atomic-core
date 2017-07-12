package net.atomichive.core.warp;

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

    public static List<Warp> getAll () {
        return warps;
    }

}
