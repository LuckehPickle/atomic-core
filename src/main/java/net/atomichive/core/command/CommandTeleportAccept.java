package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.util.ExpiringValue;
import net.atomichive.core.util.TeleportUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Teleport Accept
 * Accepts the most recent teleport request.
 */
public class CommandTeleportAccept extends BaseCommand {


    public CommandTeleportAccept () {
        super(
                "tpaccept",
                "Accepts the most recent teleport request.",
                "/tpaccept",
                "atomic-core.tp.request",
                true,
                0
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

        // Get player
        Player player = (Player) sender;
        AtomicPlayer atomicPlayer = Main.getInstance().getPlayerManager().get(player);

        // Retrieve last tp request
        ExpiringValue<Player> value = atomicPlayer.getLastTeleportRequest();
        Player target = value.get();

        // Ensure target exists
        if (target == null)
            throw new CommandException("There are currently no pending teleport requests.");

        value.expire();

        TeleportUtil.teleport(player, target);

    }

}
