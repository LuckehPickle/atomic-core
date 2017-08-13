package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command teleport position
 * Teleport to a position in your current world.
 */
public class CommandTeleportPosition extends BaseCommand {


    public CommandTeleportPosition () {
        super(
                "tppos",
                "Teleport to a position in your current world.",
                "/tppos <x> [y] <z>",
                "atomic-core.tp.pos",
                true,
                2
        );
    }


    /**
     * Run
     *
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

        // Cast to player
        Player player = (Player) sender;

        int x;
        int y;
        int z;

        if (args.length == 2) {
            if (!Util.isInteger(args[0]))
                throw new CommandException(Reason.INVALID_INPUT, "Please enter a x coordinate.");

            x = Integer.parseInt(args[0]);
        }

    }

}
