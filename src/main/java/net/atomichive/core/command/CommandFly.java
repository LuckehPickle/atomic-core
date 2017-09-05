package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.util.CommandUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Toggles whether a player can fly.
 */
public class CommandFly extends BaseCommand {


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

        switch (args.length) {
            case 0:
                toggleFlight(sender);
                break;
            case 1:
                toggleFlight(sender, args[0]);
        }

    }


    /**
     * Toggles a player's own flight state.
     *
     * @param sender Command sender.
     * @throws CommandException if the sender is not a player.
     */
    private void toggleFlight (CommandSender sender) throws CommandException {

        // Ensure sender is a player
        if (!(sender instanceof Player)) {
            throw new CommandException(
                    Reason.INVALID_SENDER,
                    "Only players can toggle their own flight state."
            );
        }

        boolean enabled = toggleFlightState((Player) sender);

        sender.sendMessage(String.format(
                "Flight %s%s.",
                enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled",
                ChatColor.RESET
        ));

    }


    /**
     * Toggles another player's flight state.
     *
     * @param sender Command sender.
     * @param arg    Target player as an argument.
     * @throws CommandException if the sender does not have
     * permission or if the target player cannot be found.
     */
    private void toggleFlight (CommandSender sender, String arg) throws CommandException {

        // Ensure the user has permission
        if (!sender.hasPermission("atomic-core.fly.others")) {
            throw new CommandException(
                    Reason.INSUFFICIENT_PERMISSIONS,
                    "You do not have permission to toggle another player's flight state."
            );
        }

        Player target = CommandUtil.parseTarget(arg);
        String name = "Console";

        // Update name if applicable
        if (sender instanceof Player) {
            name = ((Player) sender).getDisplayName();
        }

        boolean enabled = toggleFlightState(target);

        sender.sendMessage(String.format(
                "%s's flight has been %s%s.",
                ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET,
                enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled",
                ChatColor.RESET
        ));

        target.sendMessage(String.format(
                "%s %s%s flight.",
                ChatColor.YELLOW + name + ChatColor.RESET,
                enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled",
                ChatColor.RESET
        ));

    }


    /**
     * Toggles the flying status of a particular player.
     *
     * @param player Player whose flight status should
     *               be toggled.
     * @return Whether flight was enabled or disabled.
     */
    private boolean toggleFlightState (Player player) {

        // Get flight status
        boolean canFly = player.getAllowFlight();

        player.setFallDistance(0f);
        player.setFlying(false);
        player.setAllowFlight(!canFly);

        return !canFly;

    }

}
