package net.atomichive.core.command;

import org.bukkit.command.CommandSender;

/**
 * Command Kill
 * Sucks the life out of a target player.
 */
public class CommandKill extends BaseCommand {


    public CommandKill () {
        super(
                "kill",
                "Sucks the life out of a target player.",
                "/kill <player>",
                "atomic-core.kill",
                false,
                1
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
    public boolean run (CommandSender sender, String label, String[] args) {


        return false;
    }

}
