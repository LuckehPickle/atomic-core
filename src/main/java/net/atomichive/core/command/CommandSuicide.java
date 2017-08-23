package net.atomichive.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Causes the player to spontaneously combust.
 * TODO Change death message.
 */
public class CommandSuicide extends BaseCommand {


    public CommandSuicide () {
        super(
                "suicide",
                "Causes the player to spontaneously combust.",
                "/suicide",
                "atomic-core.suicide",
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
     */
    @Override
    public void run (CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        player.setFireTicks(100);
        player.setHealth(0);
    }

}
