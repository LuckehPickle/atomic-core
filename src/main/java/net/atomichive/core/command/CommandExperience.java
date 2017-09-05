package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Gives experience.
 */
public class CommandExperience extends BaseCommand {


    public CommandExperience () {
        super (
                "experience",
                "Gives experience.",
                "/xp <experience>",
                "atomic-core.experience",
                true,
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

        Player player = (Player) sender;
        AtomicPlayer atomicPlayer = Main.getInstance().getPlayerManager().get(player);

        int xp;

        // Ensure entered value can be parsed as an int
        if (!Util.isInteger(args[0]))
            throw new CommandException("Please enter a valid number.");

        xp = Integer.parseInt(args[0]);

        atomicPlayer.giveExperience(xp);

        player.sendMessage(String.format(
                "Received %s.",
                ChatColor.GREEN + String.valueOf(xp) + "xp" + ChatColor.RESET
        ));

    }


}
