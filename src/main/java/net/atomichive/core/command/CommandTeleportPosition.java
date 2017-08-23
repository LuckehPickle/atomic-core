package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
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

        // Cast to player
        Player player = (Player) sender;

        int x;
        int y;
        int z;

        if (args.length == 2) {
            if (!Util.isInteger(args[0])) {
                throw new CommandException(
                        Reason.INVALID_NUMBER,
                        "Please enter a x coordinate."
                );
            }

            x = Integer.parseInt(args[0]);
        }

    }

}
