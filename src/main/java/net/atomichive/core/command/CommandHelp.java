package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import org.bukkit.command.CommandSender;

/**
 * Lists all available Atomic Hive commands.
 */
public class CommandHelp extends BaseCommand {


    public CommandHelp () {
        super(
                "help",
                "Lists all available Atomic Hive commands.",
                "/help [--legacy] [page]",
                "atomic-core.help",
                false,
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

    }

}
