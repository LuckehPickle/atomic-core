package net.atomichive.core.item;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.MalformedJsonException;
import net.atomichive.core.JsonManager;
import net.atomichive.core.exception.ElementAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

/**
 * Item Manager
 * A class which tracks all custom item definitions.
 */
public class ItemManager extends JsonManager {


    private List<CustomItem> customItems = new ArrayList<>();


    /**
     * Item Manager
     *
     * @param resource Path to JSON file.
     */
    public ItemManager (String resource) {
        super(resource, "item", "items");
    }


    /**
     * Load
     * Attempts to reload items from items.json
     *
     * @return Number of elements loaded.
     */
    @Override
    public int load () throws MalformedJsonException {
        customItems.clear();
        return super.load();
    }


    /**
     * Load element
     * Loads a particular element as defined
     * by the extending class.
     *
     * @param element A JsonElement.
     * @return A string representation of the element.
     */
    @Override
    protected String loadElement (JsonElement element)
            throws ElementAlreadyExistsException {

        // Get entity from json
        Gson gson = new Gson();
        CustomItem item = gson.fromJson(element, CustomItem.class);

        // Ensure item has not already been defined
        if (contains(item))
            throw new ElementAlreadyExistsException(item.getName());

        customItems.add(item);

        return item.toString();

    }


    /**
     * Contains
     * A simple search through existing custom items
     * which compares the unique names.
     *
     * @param item Item to search for.
     * @return Whether this custom item is already defined.
     */
    private boolean contains (CustomItem item) {

        // Iterate over custom entities
        for (CustomItem i : customItems) {
            if (i.getName().equalsIgnoreCase(item.getName()))
                return true;
        }

        return false;

    }


    /**
     * Get item by name
     *
     * @param name Name of item to retrieve.
     * @return CustomItem with matching name or null.
     */
    public CustomItem getItemByName (String name) {

        // Iterate over items
        for (CustomItem item : customItems) {
            if (item.getName().equalsIgnoreCase(name))
                return item;
        }

        return null;

    }

    public List<CustomItem> getCustomItems () {
        return customItems;
    }

}
