package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Jump to the block you're looking at.
 */
public class CommandJump extends BaseCommand {


    public CommandJump () {
        super(
                "jump",
                "Jump to the block you're looking at.",
                "/jump",
                "atomic-core.jump",
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

        // Ray trace
        Location location = Util.trace(player);

        // Ensure a block was found
        if (location == null) {
            throw new CommandException("No block in sight.");
        }

        // Set direction
        location.setDirection(player.getLocation().getDirection());

        player.teleport(location);

        player.sendMessage("Jumped to " + ChatColor.GREEN + Util.formatLocation(location) + ChatColor.RESET + ".");

    }

}
