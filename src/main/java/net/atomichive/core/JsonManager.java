package net.atomichive.core;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;
import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;

/**
 * A class which includes some common functions for
 * dealing with JSON files.
 */
public abstract class JsonManager {


    private File jsonFile;
    private String name;
    private String namePluralised;


    /**
     * Json Manager
     *
     * @param resource   Path to JSON file.
     * @param name       Name of each individual element.
     * @param pluralised Name of grouped elements.
     */
    public JsonManager (String resource, String name, String pluralised) {
        setFileLocation(resource);
        this.name = name;
        this.namePluralised = pluralised;
    }


    /**
     * Set file location
     *
     * @param resource Path to JSON file,
     */
    private void setFileLocation (String resource) {

        // Get data folder
        File dataFolder = Main.getInstance().getDataFolder();

        File file = new File(dataFolder, resource);

        if (!file.exists()) {
            // Copy resource to data folder
            try {
                Util.exportResource(resource);
            } catch (Exception e) {
                Main.getInstance().log(Level.SEVERE, e.getMessage());
                return;
            }
        }

        this.jsonFile = file;

    }


    /**
     * Loads custom elements from the JSON file. You
     * can call this at any time to reload.
     *
     * @return Number of elements loaded.
     */
    public int load () throws MalformedJsonException {

        try {
            // Parse JSON
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(
                    new FileReader(this.jsonFile)
            ).getAsJsonArray();


            // Iterate over elements
            for (int i = 0; i < array.size(); i++) {

                String element;

                try {
                    element = loadElement(array.get(i));
                } catch (CustomObjectException e) {
                    Main.getInstance().log(Level.SEVERE, e.getMessage());
                    continue;
                }

                Main.getInstance().log(Level.INFO, " - " + element);

            }


            String message = "Found %d %s.";

            switch (array.size()) {
                case 0:
                    message = "No custom " + namePluralised + " defined.";
                    break;
                case 1:
                    message = String.format(message, array.size(), name);
                    break;
                default:
                    message = String.format(message, array.size(), namePluralised);
            }

            Main.getInstance().log(Level.INFO, message);


            return array.size();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return -1;

    }


    /**
     * Loads a particular element as defined
     * by the extending class.
     *
     * @param element A JsonElement.
     * @return A string representation of the element.
     */
    protected abstract String loadElement (JsonElement element) throws CustomObjectException;

}
