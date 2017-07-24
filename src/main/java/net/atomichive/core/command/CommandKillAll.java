package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.exception.Reason;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Command Kill All
 * Kills all entities.
 */
public class CommandKillAll extends BaseCommand {


    public CommandKillAll () {
        super (
                "killall",
                "Kills all entities.",
                "/killall [range]",
                "atomic-core.killall",
                true,
                0
        );
    }



    /**
     * Run
     * @param sender The object that sent the command.
     * @param label  The exact command label typed by the user.
     * @param args   Any command arguments.
     * @throws CommandException    if an error occurs.
     * @throws PermissionException if the user doesn't have
     *                             appropriate permissions.
     */
    @Override
    public void run (CommandSender sender, String label, String[] args)
            throws CommandException, PermissionException {

        // Get player
        Player player = (Player) sender;

        if (args.length == 0) {

            // Ensure player has permission
            if (!player.hasPermission("atomic-core.killall.world"))
                throw new PermissionException("You do not have permission to kill all entities in a world. Please specify a radius.");

            // Kill and output
            out(player, remove(player.getWorld().getEntities()));

        } else {

            int radius = 10;
            int maxRadius = Main.getInstance().getBukkitConfig().getInt("max_kill_radius", 100);

            // Attempt to get radius from args
            try {
                radius = Integer.parseInt(args[0]);

                if (radius > maxRadius && !player.hasPermission("atomic-core.killall.world"))
                    throw new CommandException("Maximum radius exceeded. Please enter a value lower than " + maxRadius + ".");

            } catch (NumberFormatException e) {
                throw new CommandException(
                        Reason.INVALID_INPUT,
                        "Please enter a valid number."
                );
            }

            // Remove nearby
            int count = remove(player.getWorld().getNearbyEntities(
                    player.getLocation(),
                    radius, radius, radius
            ));

            out(player, count);

        }

    }



    /**
     * Remove
     * Removes all non-player entities in a collection.
     * @param entities Collection of entities to remove.
     * @return Number of entities removed.
     */
    private int remove (Collection<Entity> entities) {
        return remove(new ArrayList<>(entities));
    }


    /**
     * Remove
     * Removes all non-player entities in a list.
     * @param entities List of entities to remove.
     * @return Number of entities removed.
     */
    private int remove (List<Entity> entities) {

        // Tracks the number of entities killed
        int count = 0;

        // Iterate over all entities.
        for (Entity entity : entities) {
            if (!(entity instanceof Player)) {
                entity.remove();
                count++;
            }
        }

        return count;

    }



    /**
     * Out
     * Formats and outputs a kill count to the player.
     * @param player Player who initiated the command
     * @param count  Number of entities killed.
     */
    private void out (Player player, int count) {

        // Update format depending on count
        String format = (count == 1) ? "Killed %s entity." : "Killed %s entities.";

        player.sendMessage(String.format(
                format,
                ChatColor.GREEN + "" + count + ChatColor.RESET
        ));

    }

}