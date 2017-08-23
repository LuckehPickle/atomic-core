package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.exception.UnknownPlayerException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Toggles whether a player can fly.
 */
public class CommandFly extends BaseCommand {


    // Successful output format
    private final static String FORMAT = "Flight %s.";


    public CommandFly () {
        super(
                "fly",
                "Toggles whether a player can fly.",
                "/fly [player]",
                "atomic-core.fly",
                false,
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

        // If the sender is not a player, ensure more than one arg is entered.
        if (args.length == 0 && !(sender instanceof Player)) {
            throw new CommandException(
                    Reason.INVALID_SENDER,
                    "Only players can change their own flight status."
            );
        }


        if (args.length == 0) {

            // Update the senders flight status
            Player player = (Player) sender;
            toggleFlight(player);

        } else if (args.length == 1) {

            // Ensure the user has permission
            if (!sender.hasPermission("atomic-core.fly.others")) {
                throw new CommandException(
                        Reason.INSUFFICIENT_PERMISSIONS,
                        "You do not have permission to toggle another player's flight state."
                );
            }

            // Retrieve the target player
            Player target = Bukkit.getPlayer(args[0]);

            // Ensure target exists
            if (target == null)
                throw new UnknownPlayerException(args[0]);

            toggleFlight(target);

            // Update the command sender
            sender.sendMessage(target.getDisplayName() + "'s " + getOutputString(target).toLowerCase());

        }

    }


    /**
     * Toggles the flying status of a particular player.
     *
     * @param player Player whose flight status should be toggled.
     */
    private void toggleFlight (Player player) {

        // Get flight status
        boolean canFly = player.getAllowFlight();

        player.setFallDistance(0f);
        player.setFlying(false);
        player.setAllowFlight(!canFly);

        // Update the player on their new flight status
        player.sendMessage(getOutputString(player));

    }


    /**
     * Constructs a string which details a user's flight status.
     *
     * @param player Player whose flight status will be printed.
     * @return A string which details whether a player can fly or not.
     */
    private String getOutputString (Player player) {

        // Get whether flight is enabled or disabled, and colour a string
        String status = player.getAllowFlight() ?
                ChatColor.GREEN + "enabled" :
                ChatColor.RED + "disabled";

        // Ensure that the colour is reset.
        status += ChatColor.RESET;

        return String.format(FORMAT, status);

    }

}
