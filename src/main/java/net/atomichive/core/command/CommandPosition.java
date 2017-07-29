package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

/**
 * Command Position
 * Returns your current position and the position
 * of the block you're looking at.
 */
public class CommandPosition extends BaseCommand {


    public CommandPosition () {
        super (
                "position",
                "Returns your current position and the position of the block you're looking at.",
                "/pos [player]",
                "atomic-core.position",
                false,
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

        if (args.length == 0) {

            // Ensure sender is player
            if (!(sender instanceof Player))
                throw new CommandException("Only players can get their own position!");

            // Get player
            Player player = (Player) sender;

            player.sendMessage(String.format(
                    "Your current location is %s.",
                    ChatColor.GREEN + Util.formatLocation(player.getLocation()) + ChatColor.RESET
            ));

            // Get block player is looking at
            Location location = simpleTrace(player);

            // Ensure location is not null
            if (location == null)
                throw new CommandException("No block in sight.");

            player.sendMessage(String.format(
                    "You are looking at %s.",
                    ChatColor.GREEN + Util.formatLocation(location) + ChatColor.RESET
            ));

        } else {

            // Ensure sender has permission
            if (!sender.hasPermission("atomic-core.position.others"))
                throw new PermissionException("You do not have permission to view other players' positions.");

            // Get target player
            Player target = Bukkit.getPlayer(args[0]);

            // Ensure target player was found
            if (target == null)
                throw new CommandException("Player '" + args[0] + "' not found.");

            sender.sendMessage(String.format(
                    "%s is standing at %s.",
                    ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET,
                    ChatColor.GREEN + Util.formatLocation(target.getLocation()) + ChatColor.RESET
            ));

            Location location = simpleTrace(target);

            if (location == null)
                throw new CommandException("No block in sight.");

            sender.sendMessage(String.format(
                    "%s is looking at %s.",
                    ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET,
                    ChatColor.GREEN + Util.formatLocation(location) + ChatColor.RESET
            ));

        }
    }


    /**
     * Simple trace
     * Performs a simple ray trace to the block the
     * player is looking at.
     * @param player Player to trace from.
     */
    private Location simpleTrace (Player player) {

        // Create a new block iterator
        BlockIterator iterator = new BlockIterator(player, 200);

        // Iterate over blocks
        while (iterator.hasNext()) {

            // Get next block
            Block block = iterator.next();

            if (!block.isEmpty())
                return block.getLocation();

        }

        return null;

    }

}
