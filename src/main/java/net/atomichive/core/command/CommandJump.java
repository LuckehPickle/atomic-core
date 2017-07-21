package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Jump
 * Jump to the block you're looking at.
 */
public class CommandJump extends BaseCommand {


    public CommandJump () {
        super (
                "jump",
                "Jump to the block you're looking at.",
                "/jump",
                "atomic-core.jump",
                true,
                0
        );
    }


    /**
     * Run
     * Toggles a players flight state.
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

        // Ray trace
        Location location = Util.trace(player);

        if (location == null)
            throw new CommandException("No block in sight.");

        // Set direction
        location.setDirection(player.getLocation().getDirection());

        player.teleport(location);

        player.sendMessage("Jumped to " + ChatColor.GREEN + Util.formatLocation(location) + ChatColor.RESET + ".");

    }

}
