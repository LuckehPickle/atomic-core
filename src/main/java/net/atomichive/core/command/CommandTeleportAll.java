package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command teleport all
 * Teleports all players to you.
 */
public class CommandTeleportAll extends BaseCommand {


    public CommandTeleportAll () {
        super(
                "tpall",
                "Teleports all players to you.",
                "/tpall",
                "atomic-core.tp.all",
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

        // Cast as player
        Player player = (Player) sender;

        for (Player target : Bukkit.getOnlinePlayers())
            target.teleport(player);

        Bukkit.broadcastMessage(String.format(
                "Everyone has been teleported to %s.",
                ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET
        ));

    }

}
