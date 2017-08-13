package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Heal
 * Restores health and hunger.
 */
public class CommandHeal extends BaseCommand {


    public CommandHeal () {
        super(
                "heal",
                "Restores health and hunger.",
                "/heal",
                "atomic-core.heal",
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

        player.setHealth(20);
        player.setFoodLevel(20);

        player.sendMessage("You have been " + ChatColor.GREEN + "healed" + ChatColor.RESET + ".");

    }
}
