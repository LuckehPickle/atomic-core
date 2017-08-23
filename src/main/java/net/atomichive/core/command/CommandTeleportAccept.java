package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.util.ExpiringValue;
import net.atomichive.core.util.TeleportUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Accepts the most recent teleport request.
 */
public class CommandTeleportAccept extends BaseCommand {


    @SuppressWarnings("SpellCheckingInspection")
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
        AtomicPlayer atomicPlayer = Main.getInstance().getPlayerManager().get(player);

        // Retrieve last tp request
        ExpiringValue<Player> value = atomicPlayer.getLastTeleportRequest();
        Player target = value.get();

        // Ensure target exists
        if (target == null) {
            throw new CommandException(
                    Reason.GENERIC_ERROR,
                    "There are currently no pending teleport requests."
            );
        }

        // Expire any existing requests
        value.expire();

        TeleportUtil.teleport(player, target);

    }

}
