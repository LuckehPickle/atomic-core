package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.exception.Reason;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Teleport
 * Teleports one player to another.
 */
public class CommandTeleport extends BaseCommand {


    public CommandTeleport () {
        super(
                "teleport",
                "Teleports one player to another.",
                "/tp [source] <destination>",
                "atomic-core.tp",
                false,
                0
        );
    }


    /**
     * Run
     * Toggles a players flight state.
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

        if (args.length == 0) {
            throw new CommandException(Reason.INVALID_USAGE, getUsage());
        } else if (args.length == 1) {

            // Ensure sender is a player.
            if (!(sender instanceof Player)) {
                throw new CommandException(
                        Reason.INVALID_SENDER,
                        "Only players can teleport to other players."
                );
            }

            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            boolean silent = false;

            // Ensure target player exists
            if (target == null)
                throw new CommandException("Player '" + args[0] + "' could not be found.");

            // Check if the tp is silent
            if (label.equalsIgnoreCase("tpsilent") || label.equalsIgnoreCase("tps")) {

                // Ensure player has permission
                if (!player.hasPermission("atomic-core.tp.silent"))
                    throw new PermissionException("You do not have permission to silently teleport.");

                silent = true;
            }

            teleport(player, target, silent);

        } else {

            // Ensure sender has permission
            if (!sender.hasPermission("atomic-core.tp.others"))
                throw new PermissionException("You do not have permission to teleport other players.");

            // Get players
            Player source = Bukkit.getPlayer(args[0]);
            Player destination = Bukkit.getPlayer(args[1]);

            // Ensure players exist
            if (source == null)
                throw new CommandException("Player '" + args[0] + "' could not be found.");

            if (destination == null)
                throw new CommandException("Player '" + args[1] + "' could not be found.");

            teleport(source, destination);

            // Update sender
            sender.sendMessage(String.format(
                    "Teleported %s to %s.",
                    ChatColor.YELLOW + source.getDisplayName() + ChatColor.RESET,
                    ChatColor.YELLOW + destination.getDisplayName() + ChatColor.RESET
            ));

        }

        return true;
    }



    /**
     * Teleport
     * @param source      Player being teleported.
     * @param destination Player to teleport to.
     */
    private void teleport (Player source, Player destination) {
        teleport(source, destination, false);
    }



    /**
     * Teleport
     * @param source      Player being teleported.
     * @param destination Player to teleport to.
     * @param silent      Whether to make the teleport silent.
     */
    private void teleport (Player source, Player destination, boolean silent) {

        // Teleport
        source.teleport(destination);

        // Send contextual information
        source.sendMessage(String.format(
                !silent ? "Teleported to %s." : "Silently teleported to %s.",
                ChatColor.YELLOW + destination.getDisplayName() + ChatColor.RESET
        ));

        if (!silent) {
            destination.sendMessage(String.format(
                    "%s teleported to you.",
                    ChatColor.YELLOW + source.getDisplayName() + ChatColor.RESET
            ));
        }

    }


}
