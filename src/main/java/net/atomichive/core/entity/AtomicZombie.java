package net.atomichive.core.entity;

import net.minecraft.server.v1_12_R1.EntityZombie;
import net.minecraft.server.v1_12_R1.World;

/**
 * Atomic Zombie
 */
public class AtomicZombie extends EntityZombie {

    private float speed;

    public AtomicZombie (World world) {
        super(world);
    }
}
