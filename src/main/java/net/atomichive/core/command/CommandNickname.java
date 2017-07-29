package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.player.PlayerManager;
import net.atomichive.core.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Nickname
 * Set your a custom display name.
 */
public class CommandNickname extends BaseCommand {


    public CommandNickname () {
        super (
                "nick",
                "Set your a custom display name.",
                "/nick <name...>",
                "atomic-core.nick",
                true,
                0
        );
    }


    /**
     * Run
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
        AtomicPlayer atomicPlayer = Main.getInstance().getPlayerManager().getOrCreate(player);


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
                    Reason.INVALID_INPUT,
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
