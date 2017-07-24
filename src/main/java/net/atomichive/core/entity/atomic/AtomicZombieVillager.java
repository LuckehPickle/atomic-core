package net.atomichive.core.entity.atomic;

import net.atomichive.core.entity.EntityAttributes;
import net.atomichive.core.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;

/**
 * Atomic Zombie Villager
 */
public class AtomicZombieVillager extends AtomicZombie {


    private String profession;


    /**
     * Init
     * Set config values.
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (EntityAttributes attributes) {

        profession = attributes.get(String.class, "profession", null);

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
        return spawn(location, EntityType.ZOMBIE_VILLAGER);
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
        ZombieVillager villager = (ZombieVillager) entity;

        // Apply
        if (this.profession != null) {
            Villager.Profession profession = Util.getEnumValue(Villager.Profession.class, this.profession);
            if (profession != null)
                villager.setVillagerProfession(profession);
        }

        return super.applyAttributes(villager);

    }
}