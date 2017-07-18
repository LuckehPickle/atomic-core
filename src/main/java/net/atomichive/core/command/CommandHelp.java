package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import org.bukkit.command.CommandSender;

/**
 * Command Help
 * Lists all available Atomic Hive commands.
 */
public class CommandHelp extends BaseCommand {


    public CommandHelp () {
        super (
                "help",
                "Lists all available Atomic Hive commands.",
                "/help [--legacy] [page]",
                "atomic-core.help",
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
    public boolean run (CommandSender sender, String label, String[] args)
            throws CommandException, PermissionException {



        return false;

    }

}
