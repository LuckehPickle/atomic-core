package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Teleport Here
 * Teleports another player to you.
 */
public class CommandTeleportHere extends BaseCommand {


    public CommandTeleportHere () {
        super (
                "tphere",
                "Teleports another player to you.",
                "/tphere <player>",
                "atomic-core.tphere",
                true,
                1
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

        Player player = (Player) sender;

        // Ensure player has tp others permission
        if (!player.hasPermission("atomic-core.tp.others"))
            throw new PermissionException("You do not have permission to teleport other players.");

        Player target = Bukkit.getPlayer(args[0]);

        // Ensure target was found
        if (target == null)
            throw new CommandException("Player '" + args[0] + "' could not be found.");

        teleport(player, target);

        return true;

    }



    /**
     * Teleport
     * @param destination Destination player.
     * @param source Player to be teleported.
     */
    private void teleport (Player destination, Player source) {

        // tp
        source.teleport(destination);

        // Send contextual information
        source.sendMessage(String.format(
                "You've been teleported to %s.",
                ChatColor.YELLOW + destination.getDisplayName() + ChatColor.RESET
        ));

        destination.sendMessage(String.format(
                "You teleported %s to you.",
                ChatColor.YELLOW + source.getDisplayName() + ChatColor.RESET
        ));

    }

}
