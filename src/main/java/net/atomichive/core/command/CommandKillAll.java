package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.entity.EntityManager;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Kills all entities.
 */
@SuppressWarnings("SpellCheckingInspection")
public class CommandKillAll extends BaseCommand {


    public CommandKillAll () {
        super(
                "killall",
                "Kills all entities.",
                "/killall [range]",
                "atomic-core.killall",
                true,
                0
        );
    }


    /**
     * Executes this command.
     *
     * @param sender Command sender.
     * @param label  The exact command label typed by the user.
     * @param args   Command arguments.
     * @throws CommandException if a generic error occurs.
     */
    @Override
    public void run (CommandSender sender, String label, String[] args)
            throws CommandException {

        // Get player
        Player player = (Player) sender;

        if (args.length == 0) {

            // Ensure player has permission
            if (!player.hasPermission("atomic-core.killall.world")) {
                throw new CommandException(
                        Reason.INSUFFICIENT_PERMISSIONS,
                        "You do not have permission to kill all entities in a world. Please specify a radius."
                );
            }

            // Kill and output
            out(player, remove(player.getWorld().getEntities()));

        } else {

            int radius;
            int maxRadius = Main.getInstance().getBukkitConfig().getInt("max_kill_radius", 100);

            // Attempt to get radius from args
            try {
                radius = Integer.parseInt(args[0]);

                if (radius > maxRadius && !player.hasPermission("atomic-core.killall.world"))
                    throw new CommandException("Maximum radius exceeded. Please enter a value lower than " + maxRadius + ".");

            } catch (NumberFormatException e) {
                throw new CommandException(
                        Reason.GENERIC_ERROR,
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
     * Removes all non-player entities in a collection.
     *
     * @param entities Collection of entities to remove.
     * @return Number of entities removed.
     */
    private int remove (Collection<Entity> entities) {
        return remove(new ArrayList<>(entities));
    }


    /**
     * Removes all non-player entities in a list.
     *
     * @param entities List of entities to remove.
     * @return Number of entities removed.
     */
    private int remove (List<Entity> entities) {

        // Tracks the number of entities killed
        int count = 0;
        EntityManager manager = Main.getInstance().getEntityManager();

        // Iterate over all entities.
        for (Entity entity : entities) {
            if (!(entity instanceof Player)) {
                manager.remove(entity);
                entity.remove();
                count++;
            }
        }

        return count;

    }


    /**
     * Formats and outputs a kill count to the player.
     *
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
