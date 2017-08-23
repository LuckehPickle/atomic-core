package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Reply
 * Respond to the last message you received.
 */
public class CommandReply extends BaseCommand {


    public CommandReply () {
        super(
                "reply",
                "Respond to the last message you received.",
                "/reply <message>",
                "atomic-core.message.reply",
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
        Player player = (Player) sender;

        // Get target
        Player target = Main.getInstance()
                .getPlayerManager()
                .get(player)
                .getLastMessageFrom();

        // Ensure target exists
        if (target == null) {
            throw new CommandException(
                    Reason.GENERIC_ERROR,
                    "You have not received a message yet."
            );
        }

        String message = Util.argsJoiner(args);

        CommandMessage.sendMessage(sender, target, message);

    }

}
