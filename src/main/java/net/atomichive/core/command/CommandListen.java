package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.InvalidNumberException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Change command listening levels.
 */
public class CommandListen extends BaseCommand {


    public CommandListen () {
        super(
                "listen",
                "Change command listening levels.",
                "/listen <help|verbosity(0-2)>",
                "atomic-core.listen",
                true,
                1
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
        AtomicPlayer player = Main.getInstance().getPlayerManager().get((Player) sender);
        int verbosity;

        // Print verbosity levels
        if (args[0].equalsIgnoreCase("help") || args[0].equals("?")) {
            sender.sendMessage(ChatColor.GOLD + "Verbosity levels:");
            sender.sendMessage(ChatColor.YELLOW + "0: " + ChatColor.RESET + "No commands.");
            sender.sendMessage(ChatColor.YELLOW + "1: " + ChatColor.RESET + "Non-staff commands.");
            sender.sendMessage(ChatColor.YELLOW + "2: " + ChatColor.RESET + "All commands.");
            return;
        }

        // Ensure input is a valid int
        if (!Util.isInteger(args[0]))
            throw new InvalidNumberException();

        verbosity = Integer.parseInt(args[0]);

        // Ensure verbosity is within range
        if (verbosity < 0 || verbosity > 2) {
            throw new CommandException(
                    Reason.INVALID_NUMBER,
                    "Please enter a valid verbosity level. (From 0 to 2)"
            );
        }

        // Update verbosity
        player.setVerbosity((short) verbosity);
        String level = null;

        // Handle verbosity
        switch (verbosity) {
            case 0:
                level = "no commands";
                break;
            case 1:
                level = "non-staff commands";
                break;
            case 2:
                level = "all commands";
        }

        sender.sendMessage(String.format(
                "Command verbosity set to %s.",
                ChatColor.GREEN + level + ChatColor.RESET
        ));

    }

}
