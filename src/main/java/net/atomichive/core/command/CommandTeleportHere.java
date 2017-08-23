package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.exception.UnknownPlayerException;
import net.atomichive.core.util.TeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Teleports another player to you.
 */
public class CommandTeleportHere extends BaseCommand {


    public CommandTeleportHere () {
        super(
                "tphere",
                "Teleports another player to you.",
                "/tphere <player>",
                "atomic-core.tp.here",
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

        Player player = (Player) sender;

        // Ensure player has tp others permission
        if (!player.hasPermission("atomic-core.tp.others")) {
            throw new CommandException(
                    Reason.INSUFFICIENT_PERMISSIONS,
                    "You do not have permission to teleport other players."
            );
        }

        Player target = Bukkit.getPlayer(args[0]);

        // Ensure target was found
        if (target == null) {
            throw new UnknownPlayerException(args[0]);
        }

        TeleportUtil.teleportHere(player, target);

    }

}
