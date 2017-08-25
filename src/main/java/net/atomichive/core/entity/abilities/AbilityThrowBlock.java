package net.atomichive.core.entity.abilities;

import net.atomichive.core.Main;
import net.atomichive.core.exception.AbilityException;
import net.atomichive.core.util.SmartMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

/**
 * Throws a block at the target player.
 */
public class AbilityThrowBlock implements Ability {


    private final String material;


    public AbilityThrowBlock (SmartMap attributes) {
        material = attributes.get(String.class, "material");
    }


    /**
     * Execute
     *
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) throws AbilityException {

        Material material;
        Location location = target.getLocation();

        // Attempt to get material
        try {
            material = Material.valueOf(this.material);
        } catch (IllegalArgumentException e) {
            throw new AbilityException(String.format(
                    "Unknown material: '%s'.",
                    this.material
            ));
        }

        // Get block beneath player's feet
        if (!material.isBlock() || material == Material.AIR) {

            Block block;
            Location temp = location.clone();

            do {
                block = temp.getBlock();
                temp = temp.add(0, -1, 0);
            } while (block.isEmpty());

            material = block.getType();

        }

        // Spawn block
        FallingBlock block = location.getWorld().spawnFallingBlock(
                location,
                new MaterialData(material)
        );

        block.setHurtEntities(true);
        block.setMetadata(
                "remove_on_ground",
                new FixedMetadataValue(Main.getInstance(), true)
        );

        Vector deltaV = target.getLocation().toVector().subtract(source.getLocation().toVector());
        deltaV.normalize();

        // Set vector
        block.setVelocity(deltaV);

    }

}
