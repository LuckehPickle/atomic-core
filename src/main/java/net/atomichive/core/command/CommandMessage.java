package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Message
 * Send a private message.
 */
public class CommandMessage extends BaseCommand {


    public CommandMessage () {
        super (
                "message",
                "Send a private message.",
                "/msg <player> <message>",
                "atomic-core.message",
                false,
                2
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

        // Get target player
        Player target  = Bukkit.getPlayer(args[0]);
        String name = "console";

        // Ensure target player could be found
        if (target == null)
            throw new CommandException("The player '" + args[0] + "' could not be found.");

        // Get message
        String message = Util.argsJoiner(args, 1);

        if (sender instanceof Player)
            name = ((Player) sender).getDisplayName();

        target.sendMessage(String.format(
                "%s -> %s: %s",
                ChatColor.YELLOW + name + ChatColor.RESET,
                ChatColor.YELLOW + "you" + ChatColor.RESET,
                message
        ));

        sender.sendMessage(String.format(
                "%s -> %s: %s",
                ChatColor.YELLOW + "You" + ChatColor.RESET,
                ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET,
                message
        ));

    }

}
