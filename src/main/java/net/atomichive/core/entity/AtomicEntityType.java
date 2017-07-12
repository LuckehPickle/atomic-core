package net.atomichive.core.entity;

import net.minecraft.server.v1_12_R1.EntityInsentient;

/**
 * Atomic Entity Types
 */
public enum AtomicEntityType {

    BLAZE      ("blaze",      AtomicBlaze.class),
    CHICKEN    ("chicken",    AtomicChicken.class),
    COW        ("cow",        AtomicCow.class),
    CREEPER    ("creeper",    AtomicCreeper.class),
    ENDERMAN   ("enderman",   AtomicEnderman.class),
    MAGMA_CUBE ("magma_cube", AtomicMagmaCube.class),
    PIG        ("pig",        AtomicPig.class),
    SHEEP      ("sheep",      AtomicSheep.class),
    SILVERFISH ("silverfish", AtomicSilverfish.class),
    SKELETON   ("skeleton",   AtomicSkeleton.class),
    SLIME      ("slime",      AtomicSlime.class),
    SPIDER     ("spider",     AtomicSpider.class),
    VILLAGER   ("villager",   AtomicVillager.class),
    WITCH      ("witch",      AtomicWitch.class),
    ZOMBIE     ("zombie",     AtomicZombie.class);


    // Attributes
    private String name;
    private Class<? extends EntityInsentient> atomicClass;


    AtomicEntityType (String name, Class<? extends EntityInsentient> atomicClass) {
        this.name = name;
        this.atomicClass = atomicClass;
    }


    /*
        Getters and setters.
     */

    public String getName () {
        return name;
    }

    public Class<? extends EntityInsentient> getAtomicClass () {
        return atomicClass;
    }
}
