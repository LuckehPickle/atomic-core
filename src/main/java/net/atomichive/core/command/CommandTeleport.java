package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.exception.UnknownPlayerException;
import net.atomichive.core.util.TeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Teleports one player to another.
 * TODO This could use a rewrite
 */
public class CommandTeleport extends BaseCommand {


    public CommandTeleport () {
        super(
                "teleport",
                "Teleports one player to another.",
                "/tp [source] <destination>",
                "atomic-core.tp",
                false,
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

        if (args.length == 1) {

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
            if (target == null) {
                throw new UnknownPlayerException(args[0]);
            }

            // Check if the tp is silent
            if (label.equalsIgnoreCase("tpsilent") || label.equalsIgnoreCase("tps")) {

                // Ensure player has permission
                if (!player.hasPermission("atomic-core.tp.silent")) {
                    throw new CommandException(
                            Reason.INSUFFICIENT_PERMISSIONS,
                            "You do not have permission to silently teleport."
                    );
                }

                silent = true;
            }

            TeleportUtil.teleport(player, target, silent);

        } else {

            // Ensure sender has permission
            if (!sender.hasPermission("atomic-core.tp.others")) {
                throw new CommandException(
                        Reason.INSUFFICIENT_PERMISSIONS,
                        "You do not have permission to teleport other players."
                );
            }

            // Get players
            Player source = Bukkit.getPlayer(args[0]);
            Player destination = Bukkit.getPlayer(args[1]);

            // Ensure players exist
            if (source == null) {
                throw new UnknownPlayerException(args[0]);
            }

            if (destination == null) {
                throw new UnknownPlayerException(args[1]);
            }

            TeleportUtil.teleport(source, destination);

            // Update sender
            sender.sendMessage(String.format(
                    "Teleported %s to %s.",
                    ChatColor.YELLOW + source.getDisplayName() + ChatColor.RESET,
                    ChatColor.YELLOW + destination.getDisplayName() + ChatColor.RESET
            ));

        }

    }


}
