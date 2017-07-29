package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import org.bukkit.command.CommandSender;

/**
 * Command God
 * Toggles god mode.
 */
public class CommandGod extends BaseCommand {


    public CommandGod () {
        super (
                "god",
                "Toggles god mode.",
                "/god",
                "atomic-core.god",
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

    }

}
