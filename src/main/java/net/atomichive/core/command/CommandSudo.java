package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Sudo.
 * Run a command as another player.
 */
public class CommandSudo extends BaseCommand {


    public CommandSudo () {
        super(
                "sudo",
                "Run a command as another player.",
                "/sudo <player> <command>",
                "atomic-core.sudo",
                false,
                2
        );
    }


    /**
     * Run
     *
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
        Player target = Bukkit.getPlayer(args[0]);

        // Ensure player exists
        if (target == null)
            throw new CommandException("Player '" + args[0] + "' not found.");

        // Join args
        String command = Util.argsJoiner(args, 1);

        // Prepend forward slash if necessary
        if (!command.startsWith("/"))
            command = "/" + command;

        runCommand(sender, target, command);

        sender.sendMessage(String.format(
                "%s ran the command %s.",
                ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET,
                ChatColor.GREEN + command + ChatColor.RESET
        ));

    }


    /**
     * Run command
     *
     * @param sender  Player or thing that ran the sudo command.
     * @param target  Target player.
     * @param command Command for target player to run.
     */
    private void runCommand (CommandSender sender, Player target, String command) {

        // Get sending player
        String player = "console";

        if (sender instanceof Player)
            player = ((Player) sender).getDisplayName();

        target.sendMessage(String.format(
                "%s performed %s on your behalf.",
                ChatColor.YELLOW + player + ChatColor.RESET,
                ChatColor.GREEN + command + ChatColor.RESET
        ));

        target.performCommand(command.replaceFirst("/", ""));

    }

}
