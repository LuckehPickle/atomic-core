package net.atomichive.core.entity.abilities;

import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Ability Explode
 * Causes an explosion at the target.
 */
public class AbilityExplode implements Ability {


    private final float power;
    private final boolean setFire;
    private final boolean breakBlocks;


    public AbilityExplode (SmartMap attributes) {
        power = attributes.get(Float.class, "power", 3.0f);
        setFire = attributes.get(Boolean.class, "set_fire", false);
        breakBlocks = attributes.get(Boolean.class, "break_blocks", false);
    }


    /**
     * Execute
     *
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) {

        // Get target location
        Location location = target.getLocation();

        location.getWorld().createExplosion(
                location.getX(),
                location.getY(),
                location.getZ(),
                power, setFire, breakBlocks
        );

    }

}
