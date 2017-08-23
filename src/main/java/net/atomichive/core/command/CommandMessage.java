package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.UnknownPlayerException;
import net.atomichive.core.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Send a private message.
 */
public class CommandMessage extends BaseCommand {


    public CommandMessage () {
        super(
                "message",
                "Send a private message.",
                "/msg <player> <message>",
                "atomic-core.message",
                false,
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

        // Get target player
        Player target = Bukkit.getPlayer(args[0]);

        // Ensure target player could be found
        if (target == null)
            throw new UnknownPlayerException(args[0]);

        // Get message
        String message = Util.argsJoiner(args, 1);


        sendMessage(sender, target, message);

    }


    /**
     * Actual message sending logic goes here.
     *
     * @param sender  Command sender.
     * @param target  Message recipient.
     * @param message Message contents.
     */
    static void sendMessage (CommandSender sender, Player target, String message) {

        String name = "console";

        // Set name if sender is a player
        if (sender instanceof Player) {
            Player player = (Player) sender;

            name = player.getDisplayName();

            // Update last sender
            Main.getInstance()
                    .getPlayerManager()
                    .get(target)
                    .setLastMessageFrom(player);
        }


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
