package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.InvalidNumberException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Change your flying or walking speeds.
 */
public class CommandSpeed extends BaseCommand {


    public CommandSpeed () {
        super(
                "speed",
                "Change your flying or walking speeds.",
                "/speed <value>",
                "atomic-core.speed",
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


        // Reset player speeds if no args are entered.
        if (args.length == 0) {
            player.setWalkSpeed(0.2f);
            player.setFlySpeed(0.1f);
            player.sendMessage("Walking and flying speeds " + ChatColor.GREEN + "reset" + ChatColor.RESET + ".");
            return;
        }


        int speed;
        float internalSpeed;

        // Ensure the user entered a valid number
        try {
            // Clamp speed between -100 and 100
            speed = Integer.parseInt(args[0]);
            speed = Math.max(-100, Math.min(100, speed));

            internalSpeed = speed / 100.0f;

        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }

        if (player.isFlying()) {
            player.setFlySpeed(internalSpeed);
            player.sendMessage("Your flying speed has been set to " + ChatColor.GREEN + speed + ChatColor.RESET + ".");
        } else {
            player.setWalkSpeed(internalSpeed);
            player.sendMessage("Your walking speed has been set to " + ChatColor.GREEN + speed + ChatColor.RESET + ".");
        }

    }

}
