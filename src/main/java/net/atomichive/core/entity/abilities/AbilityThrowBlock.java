package net.atomichive.core.entity.abilities;

import net.atomichive.core.Main;
import net.atomichive.core.util.SmartMap;
import net.atomichive.core.util.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

/**
 * Throw Block Ability
 * Throws a block at the target player.
 */
public class AbilityThrowBlock implements Ability {


    private final String material;


    public AbilityThrowBlock (SmartMap attributes) {
        material = attributes.get(String.class, "material");
    }


    /**
     * Execute
     * @param source Entity who executed the ability.
     * @param target Entity being targeted by the ability.
     */
    @Override
    public void execute (Entity source, Entity target) {

        // Get material
        Material material = Util.getEnumValue(Material.class, this.material);
        Location location = target.getLocation();

        // Get block beneath player's feet
        if (material == null || !material.isBlock() || material == Material.AIR) {

            Block block = null;
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
