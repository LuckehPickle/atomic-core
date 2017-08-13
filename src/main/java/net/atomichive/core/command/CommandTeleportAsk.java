package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.util.ExpiringValue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Teleport Ask
 * Sends a teleport request to another player.
 */
public class CommandTeleportAsk extends BaseCommand {


    public CommandTeleportAsk () {
        super(
                "tpa",
                "Sends a teleport request to another player.",
                "/tpa <player>",
                "atomic-core.tp.ask",
                true,
                1
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

        // Get source and target players
        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        // Ensure target exists
        if (target == null)
            throw new CommandException("Player '" + args[0] + "' could not be found.");

        // Ensure target is not player
        if (player.equals(target))
            throw new CommandException("You can't send a teleport request to yourself!");


        // Retrieve atomic player
        AtomicPlayer atomicTarget = Main.getInstance().getPlayerManager().get(target);
        ExpiringValue<Player> value = atomicTarget.getLastTeleportRequest();

        // Ensure request is not already pending
        if (value.is(player))
            throw new CommandException(String.format(
                    "You already have a pending teleport request with %s.",
                    target.getDisplayName()
            ));

        value.update(player);

        // Alert players of current situation
        player.sendMessage(String.format(
                "Successfully sent a teleport request to %s.",
                ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET
        ));

        target.sendMessage(String.format(
                "%s wants to teleport to you! Use %s or %s to respond.\n" +
                        "%sExpires in %d seconds.",
                ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET,
                ChatColor.GREEN + "/accept" + ChatColor.RESET,
                ChatColor.RED + "/deny" + ChatColor.RESET,
                ChatColor.GRAY,
                Main.getInstance().getBukkitConfig().getInt("teleport_request_expiry", 30)
        ));

    }
}
