package net.atomichive.core;

import net.atomichive.core.util.Util;

import java.io.File;

/**
 * Json Manager
 */
public abstract class JsonManager {

    private static File file;


    protected static void load (String resource) {

        // Get data folder
        File dataFolder = Main.getInstance().getDataFolder();

        File file = new File(dataFolder, resource);

        if (!file.exists()) {
            copyDefault(resource);
        }

        JsonManager.file = file;

    }


    /**
     * Copy default
     * Retrieves a JSON file in the data folder.
     * @param resource Name of the resource to copy
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void copyDefault (String resource) {

        // Copy resource to data folder
        try {
            Util.exportResource(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    protected static File getFile () {
        return file;
    }
}
