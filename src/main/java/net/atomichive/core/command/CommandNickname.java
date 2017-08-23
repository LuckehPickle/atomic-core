package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Set your a custom display name.
 */
public class CommandNickname extends BaseCommand {


    public CommandNickname () {
        super(
                "nick",
                "Set your a custom display name.",
                "/nick <name...>",
                "atomic-core.nick",
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


        // Reset display name
        if (args.length == 0) {

            // Update display name
            player.setDisplayName(null);
            player.setPlayerListName(null);
            player.sendMessage("Display name " + ChatColor.GREEN + "reset" + ChatColor.RESET + ".");

            // Update value in db
            atomicPlayer.setDisplayName(null);

            return;

        }

        // Join args
        String joined = Util.argsJoiner(args);

        // Enforce maximum length
        if (joined.length() > 24) {
            throw new CommandException(
                    Reason.GENERIC_ERROR,
                    "Display name must not be greater than 24 characters."
            );
        }

        // Update display name
        player.setDisplayName(joined);
        player.setPlayerListName(joined);

        Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.RESET + " is now named " + ChatColor.GREEN + joined + ChatColor.RESET + ".");

        player.sendMessage("Your display name is now " + ChatColor.GREEN + joined + ChatColor.RESET + ".");

        // Update value in db
        atomicPlayer.setDisplayName(joined);

    }


}
