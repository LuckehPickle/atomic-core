package net.atomichive.core.entity;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.entity.EntityType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Custom Entity Type
 */
public enum CustomEntityType {

    BLAZE      ("Blaze",      "blaze",         61, EntityType.BLAZE,      AtomicBlaze.class),
    CHICKEN    ("Chicken",    "chicken",       93, EntityType.CHICKEN,    AtomicChicken.class),
    COW        ("Cow",        "cow",           92, EntityType.COW,        AtomicCow.class),
    CREEPER    ("Creeper",    "creeper",       50, EntityType.CREEPER,    AtomicCreeper.class),
    ENDERMAN   ("Enderman",   "enderman",      58, EntityType.ENDERMAN,   AtomicEnderman.class),
    GHAST      ("Ghast",      "ghast",         56, EntityType.GHAST,      AtomicGhast.class),
    MAGMA_CUBE ("LavaSlime",  "magma_cube",    62, EntityType.MAGMA_CUBE, AtomicMagmaCube.class),
    PIG        ("Pig",        "pig",           90, EntityType.PIG,        AtomicPig.class),
    PIG_ZOMBIE ("PigZombie",  "zombie_pigman", 57, EntityType.PIG_ZOMBIE, AtomicPigZombie.class),
    SHEEP      ("Sheep",      "sheep",         91, EntityType.SHEEP,      AtomicSheep.class),
    SILVERFISH ("Silverfish", "silverfish",    60, EntityType.SILVERFISH, AtomicSilverfish.class),
    SKELETON   ("Skeleton",   "skeleton",      51, EntityType.SKELETON,   AtomicSkeleton.class),
    SLIME      ("Slime",      "slime",         55, EntityType.SLIME,      AtomicSlime.class),
    SNOWMAN    ("SnowMan",    "snowman",       97, EntityType.SNOWMAN,    AtomicSnowman.class),
    SPIDER     ("Spider",     "spider",        52, EntityType.SPIDER,     AtomicSpider.class),
    VILLAGER   ("Villager",   "villager",     120, EntityType.VILLAGER,   AtomicVillager.class),
    WITCH      ("Witch",      "witch",         66, EntityType.WITCH,      AtomicWitch.class),
    WOLF       ("Wolf",       "wolf",          95, EntityType.WOLF,       AtomicWolf.class),
    ZOMBIE     ("Zombie",     "zombie",        54, EntityType.ZOMBIE,     AtomicZombie.class);

    private String name;
    private String internalName;
    private int id;
    private EntityType type;
    private Class<? extends EntityInsentient> atomicClass;

    CustomEntityType (String name, String internalName, int id, EntityType type, Class<? extends EntityInsentient> atomicClass) {
        this.name = name;
        this.internalName = internalName;
        this.id = id;
        this.type = type;
        this.atomicClass = atomicClass;
    }


    public static void registerEntities () {
        for (CustomEntityType entity : values()) {
            try {
                Method a = EntityTypes.class.getDeclaredMethod("a", int.class, String.class, Class.class, String.class);
                a.setAccessible(true);
                a.invoke(
                        null,
                        entity.getId(),
                        entity.getInternalName(),
                        entity.getAtomicClass(),
                        entity.getName()
                );
            } catch (NoSuchMethodException |
                    IllegalAccessException |
                    InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    /*
        Getters and setters.
     */

    public String getName () {
        return name;
    }

    public String getInternalName () {
        return internalName;
    }

    public int getId () {
        return id;
    }

    public EntityType getType () {
        return type;
    }

    public Class<? extends EntityInsentient> getAtomicClass () {
        return atomicClass;
    }
}
