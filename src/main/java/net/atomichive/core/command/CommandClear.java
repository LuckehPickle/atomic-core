package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
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

        Player target;

        switch (args.length) {
            case 0:
                if (sender instanceof Player)
                    target = (Player) sender;
                else
                    throw new CommandException("Only players can clear their own inventory.");
                break;
            default:
                target = CommandUtil.parseTarget(args[0]);
        }

        target.getInventory().clear();

        // Send messages
        if (target.equals(sender)) {
            //noinspection SpellCheckingInspection
            target.sendMessage(String.format(
                    "Inventory %scleared%s.",
                    ChatColor.GREEN,
                    ChatColor.RESET
            ));
        } else {

            String name = "Console";

            if (sender instanceof Player)
                name = ((Player) sender).getDisplayName();

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

}
