package net.atomichive.core.entity.abilities;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Ability Summon
 * Summons entities around a target.
 */
public class AbilitySummon implements Ability {


    private final String entity;
    private final int count;
    private final int radius;


    /**
     * Ability Summon
     * Accepts any of the following attributes:
     * 1. `entity`: Unique name of the entity to summon.
     * 2. `count`: Number of entities to summon.
     * 3. `radius`: Radius around target to summon.
     *
     * @param attributes Ability attributes.
     */
    public AbilitySummon (SmartMap attributes) {
        entity = attributes.get(String.class, "entity", null);
        count = attributes.getInteger("count", 3);
        radius = attributes.getInteger("radius", 3);
    }


    /**
     * Execute
     *
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) {

        // Get target loc
        Location location = target.getLocation().clone();
        double theta = (2 * Math.PI) / count;

        // Iterate once for each entity
        for (int i = 0; i < count; i++) {
            location.setX(target.getLocation().getX() + radius * Math.cos(i * theta));
            location.setZ(target.getLocation().getZ() + radius * Math.sin(i * theta));
            try {
                Main.getInstance().getEntityManager()
                        .spawnEntity(location, entity, 1, source);
            } catch (CustomObjectException e) {
                e.printStackTrace();
            }
        }

    }

}
