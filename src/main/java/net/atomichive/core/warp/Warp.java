package net.atomichive.core.warp;

import net.atomichive.core.exception.UnknownWorldException;
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
    private int x;
    private int y;
    private int z;
    private float pitch;
    private float yaw;


    /**
     * Warp constructor.
     * Create a new warp.
     */
    public Warp () {
        this(null, null, 0, 0, 0);
    }


    /**
     * Warp constructor.
     * Create a new warp.
     *
     * @param name     Warp name, must be unique.
     * @param location Location of warp.
     */
    public Warp (String name, Location location) {

        this(
                name,
                location.getWorld().getUID(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ()
        );

        // Set pitch and yaw
        this.setPitch(location.getPitch());
        this.setYaw(location.getYaw());

    }


    /**
     * Warp constructor.
     * Create a new warp.
     *
     * @param name  Warp name, must be unique.
     * @param world UUID of the world.
     * @param x     X coordinate.
     * @param y     Y coordinate.
     * @param z     Z coordinate.
     */
    public Warp (String name, UUID world, int x, int y, int z) {

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
     *
     * @param player Player to teleport/warp.
     * @throws UnknownWorldException if the world could not be found.
     */
    public void warpPlayer (Player player) throws UnknownWorldException {

        World world = Bukkit.getServer().getWorld(this.world);

        // Ensure the world exists
        if (world == null)
            throw new UnknownWorldException();

        // Teleport player
        player.teleport(new Location(
                world,
                x + 0.5, y, z + 0.5,
                yaw, pitch
        ));

        // Print contextual information
        player.sendMessage("Warping to " + ChatColor.GREEN + toString() + ChatColor.RESET + ".");

        if (message != null) {
            player.sendMessage(ChatColor.GRAY + message);
        }

    }


    @Override
    public String toString () {
        return String.format(
                "%s %s[%s]",
                name,
                ChatColor.GRAY,
                identifier.toString().substring(0, 6)
        );
    }

    public boolean equals (Warp warp) {
        return this.name.equalsIgnoreCase(warp.getName());
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

    public int getX () {
        return x;
    }

    public void setX (int x) {
        this.x = x;
    }

    public int getY () {
        return y;
    }

    public void setY (int y) {
        this.y = y;
    }

    public int getZ () {
        return z;
    }

    public void setZ (int z) {
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
