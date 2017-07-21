package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Level
 * A debug command which lets you set your experience level
 * to any double.
 */
public class CommandLevel extends BaseCommand {


    public CommandLevel () {
        super(
                "level",
                "A debug command for setting a players level.",
                "/level [player] <level>",
                "atomic-core.level",
                true,
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
    public void run (CommandSender sender, String label, String[] args) throws CommandException {

        if (args.length == 1) {

            Player player = (Player) sender;
            float level;

            // Ensure the user entered a valid float
            try {
                level = Float.parseFloat(args[0]);
            } catch (NumberFormatException e) {
                throw new CommandException("Please enter a valid number.");
            }

            setExperience(player, level);

        } else if (args.length == 2) {

            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            float level;

            // Ensure the target player exists
            if (target == null)
                throw new CommandException("The player " + args[0] + " could not be found.");


            // Ensure the user entered a valid float
            try {
                level = Float.parseFloat(args[1]);
            } catch (NumberFormatException e) {
                throw new CommandException("Please enter a valid number.");
            }

            setExperience(target, level);

            player.sendMessage(String.format(
                    "Set %s's level to %s",
                    ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET,
                    ChatColor.GREEN + "" + level + ChatColor.RESET
            ));

        }

    }



    /**
     * Set experience
     * Sets a players experience level to a defined float.
     * @param player Player whose experience should be set.
     * @param level  Desired level as float.
     */
    private void setExperience (Player player, float level) {

        // Split the whole part and the quotient
        // e.g. 6.5 becomes 6 and 0.5
        int wholePart = (int) level;
        float quotient = level - wholePart;

        player.setLevel(wholePart);
        player.setExp(quotient);

        player.sendMessage("Set level to " + ChatColor.GREEN + level + ChatColor.RESET + ".");

    }

}
