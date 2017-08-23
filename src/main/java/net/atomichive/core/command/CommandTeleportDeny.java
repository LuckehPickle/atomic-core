package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.util.ExpiringValue;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Denies the most recent teleport request.
 */
public class CommandTeleportDeny extends BaseCommand {


    public CommandTeleportDeny () {
        super(
                "tpdeny",
                "Denies the most recent teleport request.",
                "/tpdeny",
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

        value.expire();

        player.sendMessage(String.format(
                "Teleport request from %s %s.",
                ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET,
                ChatColor.RED + "denied" + ChatColor.RESET
        ));

    }

}
