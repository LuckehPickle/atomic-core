package net.atomichive.core.entity.atomic;

import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;

/**
 * A custom zombie villager.
 */
@SuppressWarnings("unused")
public class AtomicZombieVillager extends AtomicZombie {


    private String profession;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        profession = attributes.get(String.class, "profession", null);

        super.init(attributes);

    }


    /**
     * Generates a new entity, and places it in the world.
     *
     * @param location to spawn entity.
     * @return Spawned entity.
     */
    @Override
    public Entity spawn (Location location) throws CustomObjectException {
        return spawn(location, EntityType.ZOMBIE_VILLAGER);
    }


    /**
     * Applies everything defined in config to the entity.
     *
     * @param entity Entity to edit.
     * @return Modified entity.
     */
    @Override
    public Entity applyAttributes (Entity entity) throws CustomObjectException {

        // Cast
        ZombieVillager villager = (ZombieVillager) entity;

        if (this.profession != null) {
            // Attempt to apply profession
            try {
                Villager.Profession profession = Villager.Profession.valueOf(this.profession);
                villager.setVillagerProfession(profession);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown zombie villager profession: '%s'.",
                        this.profession
                ));
            }
        }

        return super.applyAttributes(villager);

    }
}
