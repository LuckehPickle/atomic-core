package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import org.bukkit.command.CommandSender;

/**
 * Toggles god mode.
 */
public class CommandGod extends BaseCommand {


    public CommandGod () {
        super(
                "god",
                "Toggles god mode.",
                "/god",
                "atomic-core.god",
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

    }

}
