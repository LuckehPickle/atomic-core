package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.UnknownPlayerException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Kills a target player.
 */
public class CommandKill extends BaseCommand {


    private static final String format = "You've been killed by %s.";


    public CommandKill () {
        super(
                "kill",
                "Kills a target player.",
                "/kill <player>",
                "atomic-core.kill",
                false,
                1
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

        Player target = Bukkit.getPlayer(args[0]);

        // Ensure target player was found
        if (target == null) {
            throw new UnknownPlayerException(args[0]);
        }

        // Alert recipient
        if (sender instanceof Player) {
            Player player = (Player) sender;
            target.sendMessage(String.format(
                    format,
                    ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET
            ));
        } else {
            target.sendMessage(String.format(
                    format,
                    ChatColor.YELLOW + "console" + ChatColor.RESET
            ));
        }

        target.setHealth(0);

        sender.sendMessage("Killed " + ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET + ".");

    }

}
