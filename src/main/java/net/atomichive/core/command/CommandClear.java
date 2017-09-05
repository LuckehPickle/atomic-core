package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.util.CommandUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Clears the target player's inventory.
 */
public class CommandClear extends BaseCommand {


    public CommandClear () {
        super(
                "clear",
                "Clears the target player's inventory.",
                "/clear [player]",
                "atomic-core.clear",
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
                clear(sender);
                break;
            default:
                clear(sender, args[0]);
        }

    }


    /**
     * Clears a player's own inventory.
     *
     * @param sender Command sender.
     * @throws CommandException if the sender is not a player.
     */
    private void clear (CommandSender sender) throws CommandException {

        // Ensure sender is a player
        if (!(sender instanceof Player)) {
            throw new CommandException(
                    Reason.INVALID_SENDER,
                    "Only players can clear their own inventory."
            );
        }

        ((Player) sender).getInventory().clear();

        //noinspection SpellCheckingInspection
        sender.sendMessage(String.format(
                "Inventory %scleared%s.",
                ChatColor.GREEN,
                ChatColor.RESET
        ));

    }


    /**
     * Clears the target player's inventory.
     *
     * @param sender Command sender.
     * @param arg    Target player as an argument.
     * @throws CommandException if the player is not found,
     * or the sender does not have permission.
     */
    private void clear (CommandSender sender, String arg) throws CommandException {

        // Ensure sender has permission
        if (!sender.hasPermission("atomic-core.clear.others")) {
            throw new CommandException(
                    Reason.INSUFFICIENT_PERMISSIONS,
                    "You do not have permission to clear another player's inventory."
            );
        }

        // Retrieve target
        Player target = CommandUtil.parseTarget(arg);
        String name = "Console";

        target.getInventory().clear();

        // Update name (if applicable)
        if (sender instanceof Player) {
            name = ((Player) sender).getDisplayName();
        }

        sender.sendMessage(String.format(
                "Cleared %s's inventory.",
                ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET
        ));

        target.sendMessage(String.format(
                "%s cleared your inventory.",
                ChatColor.YELLOW + name + ChatColor.RESET
        ));

    }

}
