package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
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

        // Cast as player
        Player player = (Player) sender;

        for (Player target : Bukkit.getOnlinePlayers()) {
            target.teleport(player);
        }

        Bukkit.broadcastMessage(String.format(
                "Everyone has been teleported to %s.",
                ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET
        ));

    }

}
