package net.atomichive.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Suicide
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
     * Run
     * The main logic for the command is handled here.
     * @param sender The object that sent the command.
     * @param label  The exact command label typed by the user.
     * @param args   Any command arguments.
     */
    @Override
    public void run (CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        player.setFireTicks(100);
        player.setHealth(0);
    }
}
