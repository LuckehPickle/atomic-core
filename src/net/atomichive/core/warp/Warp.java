package net.atomichive.core.warp;

import net.atomichive.core.exception.WorldDoesNotExistException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Warp
 * Represents a player defined warp location.
 */
public class Warp {


    // Attributes
    private UUID identifier;
    private String name;
    private String message;
    private UUID world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;


    /**
     * Warp constructor.
     * Create a new warp.
     * @param name  Warp name, must be unique.
     * @param world The world containing this warp location.
     */
    public Warp (String name, UUID world) {
        this(name, world, 0, 0, 0);
    }

    /**
     * Warp constructor.
     * Create a new warp.
     * @param name  Warp name, must be unique.
     * @param world UUID of the world.
     * @param x     X coordinate.
     * @param y     Y coordinate.
     * @param z     Z coordinate.
     */
    public Warp (String name, UUID world, double x, double y, double z) {

        // Init
        this.identifier = UUID.randomUUID();
        this.name = name;
        this.message = null;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = 0.0f;
        this.yaw = 0.0f;

    }


    /**
     * Warp player
     * Warp the specified player to this warp.
     * TODO particles and sounds
     * @param player Player to teleport/warp.
     * @throws WorldDoesNotExistException if the world could not be found.
     *                                    This is typically caused by a
     *                                    world being deleted.
     */
    public void warpPlayer (Player player) throws WorldDoesNotExistException {

        // Get world from UUID
        World world = Bukkit.getServer().getWorld(this.world);

        // Ensure the world exists
        if (world == null)
            throw new WorldDoesNotExistException();

        // Teleport player
        player.teleport(new Location(world, x, y, z, yaw, pitch));

        // Print contextual information
        player.sendMessage("Warping to: " + ChatColor.YELLOW + name);

        if (message != null)
            player.sendMessage(message);

    }


	/*
        Getters and setters.
	 */

    public UUID getIdentifier () {
        return identifier;
    }

    public void setIdentifier (UUID identifier) {
        this.identifier = identifier;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public UUID getWorld () {
        return world;
    }

    public void setWorld (UUID world) {
        this.world = world;
    }

    public double getX () {
        return x;
    }

    public void setX (double x) {
        this.x = x;
    }

    public double getY () {
        return y;
    }

    public void setY (double y) {
        this.y = y;
    }

    public double getZ () {
        return z;
    }

    public void setZ (double z) {
        this.z = z;
    }

    public float getPitch () {
        return pitch;
    }

    public void setPitch (float pitch) {
        this.pitch = pitch;
    }

    public float getYaw () {
        return yaw;
    }

    public void setYaw (float yaw) {
        this.yaw = yaw;
    }
}
