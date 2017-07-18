package net.atomichive.core.entity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;
import net.atomichive.core.JsonManager;
import net.atomichive.core.Main;
import net.atomichive.core.exception.EntityException;
import net.atomichive.core.exception.Reason;
import net.minecraft.server.v1_12_R1.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Entity Manager
 * A class which tracks all custom entities.
 */
public class EntityManager extends JsonManager {

    private static List<CustomEntity> customEntities = new ArrayList<>();


    public static int load () {
        load("entities.json");

        try {
            return loadCustomEntities(new FileReader(getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }


    /**
     * Load custom entities
     * Loads an instance of all custom entities.
     * @param json JSON entities reader.
     */
    public static int loadCustomEntities (FileReader json) {

        // Parse JSON
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(json).getAsJsonArray();

        Main.getInstance().log(Level.INFO, "Found " + array.size() + " custom entities.");
        customEntities.clear();

        // Iterate over added entities
        for (int i = 0; i < array.size(); i++) {

            // Get entity from JSON
            CustomEntity entity = gson.fromJson(array.get(i), CustomEntity.class);
            entity.init();

            // Ensure entity is unique
            if (customEntities.contains(entity)) {
                Main.getInstance().log(
                        Level.WARNING,
                        "Entity is already defined with the name " + entity.getName() + ". Ignoring."
                );
                continue;
            }

            // Add entity to list
            Main.getInstance().log(Level.INFO, "Loaded " + entity.getName());
            customEntities.add(entity);

        }

        return array.size();

    }



    /**
     * Spawn entity
     * Spawns a new custom entity
     * @param location Desired spawn location.
     * @param entityName Name of custom entity to spawn.
     * @param count Number of entities to spawn.
     * @throws EntityException if an exception is encountered. These
     *                         are safe to relay to players.
     */
    public static void spawnEntity (Location location, String entityName, int count) throws EntityException {

        CustomEntity customEntity = null;

        // Determine which entity needs to be spawned
        for (CustomEntity e : customEntities) {
            if (e.getName().equalsIgnoreCase(entityName)) {
                customEntity = e;
                break;
            }
        }

        // Ensure entity was found
        if (customEntity == null) {
            throw new EntityException(
                    Reason.ENTITY_ERROR,
                    "Entity '" + entityName + "' could not be found."
            );
        }

        for (int i = 0; i < count; i++)
            customEntity.spawn(location);

    }


    /*
        Getters and setters.
     */

    public static List<CustomEntity> getAll () {
        return customEntities;
    }


}
