package net.atomichive.core.entity;

import net.atomichive.core.util.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

/**
 * Atomic Villager
 */
public class AtomicVillager extends AtomicAgeable {


    private String profession;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {

        profession = attributes.getString("profession", null);

        super.init(attributes);

    }


    /**
     * Spawn
     * Generates a new entity, and places it in the world.
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) {
        return spawn(location, EntityType.VILLAGER);
    }


    /**
     * Apply attributes
     * Applies everything defined in config to the entity.
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) {

        // Cast
        Villager villager = (Villager) entity;

        // Apply
        if (this.profession != null) {
            Villager.Profession profession = Utils.getVillagerProfession(this.profession);
            if (profession != null)
                villager.setProfession(profession);
        }

        return villager;
    }


}
