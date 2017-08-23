package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
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

        player.setHealth(20);
        player.setFoodLevel(20);

        player.sendMessage("You have been " + ChatColor.GREEN + "healed" + ChatColor.RESET + ".");

    }

}
