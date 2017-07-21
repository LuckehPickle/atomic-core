package net.atomichive.core.entity.ai;

import net.atomichive.core.util.NMSUtil;
import net.minecraft.server.v1_12_R1.ControllerLook;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.MathHelper;
import net.minecraft.server.v1_12_R1.NavigationAbstract;
import net.minecraft.server.v1_12_R1.PathType;
import net.minecraft.server.v1_12_R1.PathfinderGoal;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Pathfinder Goal Follow Entity
 * Follows an entity. Useful for pets or escort
 * missions.
 */
public class PathfinderGoalFollowEntity extends PathfinderGoal {


    private EntityInsentient entity;
    private Entity follow;
    private NavigationAbstract navigation;
    private double speed;
    private float maxDistance;
    private float minDistance;
    private int count;
    private float h;


    /**
     * Pathfinder goal follow entity
     * @param entity Entity that is following.
     * @param follow Entity to be followed.
     * @param minDistance Maximum distance before a teleport is carried out.
     */
    public PathfinderGoalFollowEntity (EntityInsentient entity, Entity follow, double speed, float minDistance, float maxDistance) {
        this.entity = entity;
        this.follow = follow;
        this.navigation = entity.getNavigation();
        this.speed = speed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }


    /**
     * a
     * Validates goal. If false, then entity will ignore this goal.
     * @return Whether the goal is valid.
     */
    @Override
    public boolean a () {

        if (follow == null) {
            return false;
        } else if (follow.isDead()) {
            return false;
        }

        return true;
    }


    public void c() {
        this.count = 0;
        this.h = this.entity.a(PathType.WATER);
        this.entity.a(PathType.WATER, 0.0F);
    }

    public void d() {
        this.follow = null;
        this.navigation.p();
        this.entity.a(PathType.WATER, this.h);
    }


    /**
     * e
     * Executes goal.
     */
    public void e () {

        this.entity.getControllerLook().a(NMSUtil.getNMSEntity(follow), 10.0F, (float) this.entity.N());

        count--;

        if (count <= 0) {

            count = 10;

            // Calculate distance between entities with pythagoras
            double deltaX = entity.locX - follow.getLocation().getX();
            double deltaY = entity.locY - follow.getLocation().getY();
            double deltaZ = entity.locZ - follow.getLocation().getZ();

            double distance = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

            if (distance > (double) maxDistance * maxDistance) {
                if (follow.isOnGround())
                    entity.getBukkitEntity().teleport(follow);
            } else if (distance > (double) minDistance * minDistance) {
                this.navigation.a(NMSUtil.getNMSEntity(follow), this.speed);
            } else {
                this.navigation.p();
                ControllerLook look = this.entity.getControllerLook();
                if (distance <= (double) minDistance || look.e() == entity.locX && look.f() == entity.locY && look.g() == entity.locZ) {
                    double var10 = follow.getLocation().getX() - entity.locX;
                    double var12 = follow.getLocation().getZ() - entity.locZ;
                    this.navigation.a(entity.locX - var10, entity.locY, entity.locZ - var12, speed);
                }
            }

        }

    }

}
