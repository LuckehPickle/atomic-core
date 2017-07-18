package net.atomichive.core.entity;

import org.bukkit.entity.Entity;

/**
 * Active Entity
 */
public class ActiveEntity {

    private Entity entity;

    public ActiveEntity (Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity () {
        return entity;
    }

}
