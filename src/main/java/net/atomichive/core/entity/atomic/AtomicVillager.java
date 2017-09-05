package net.atomichive.core.entity.atomic;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * A custom villager.
 */
@SuppressWarnings("unused")
public class AtomicVillager extends AtomicAgeable {


    private String profession;
    private boolean preventTrade;


    /**
     * Set config values.
     *
     * @param attributes Entity Config from entities.json.
     */
    @Override
    public void init (SmartMap attributes) {

        profession   = attributes.get(String.class,  "profession",    null);
        preventTrade = attributes.get(Boolean.class, "prevent_trade", true);

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
        return spawn(location, EntityType.VILLAGER);
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
        Villager villager = (Villager) entity;

        if (this.profession != null) {

            // Attempt to apply villager profession
            try {
                Villager.Profession profession = Villager.Profession.valueOf(this.profession.toUpperCase());
                villager.setProfession(profession);
            } catch (IllegalArgumentException e) {
                throw new CustomObjectException(String.format(
                        "Unknown villager profession: '%s'.",
                        this.profession
                ));
            }
        }

        villager.setMetadata(
                "prevent_trade",
                new FixedMetadataValue(Main.getInstance(), preventTrade)
        );

        return villager;
    }


}
