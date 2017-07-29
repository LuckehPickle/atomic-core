package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Reply
 * Respond to the last message you received.
 */
public class CommandReply extends BaseCommand {


    public CommandReply () {
        super (
                "reply",
                "Respond to the last message you received.",
                "/reply <message>",
                "atomic-core.message.reply",
                true,
                1
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

        // Get player
        Player player = (Player) sender;

        // Get target
        Player target = Main.getInstance()
                .getPlayerManager()
                .getOrCreate(player)
                .getLastMessageFrom();

        // Ensure target exists
        if (target == null)
            throw new CommandException("You have not received a message yet.");

        String message = Util.argsJoiner(args);

        CommandMessage.sendMessage(sender, target, message);

    }

}
