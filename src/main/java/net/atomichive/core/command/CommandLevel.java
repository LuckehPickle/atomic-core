package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.InvalidNumberException;
import net.atomichive.core.exception.UnknownPlayerException;
import net.atomichive.core.player.AtomicPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A debug command for setting a players level.
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

        if (args.length == 1) {

            Player player = (Player) sender;
            float level;

            // Ensure the user entered a valid float
            try {
                level = Float.parseFloat(args[0]);
            } catch (NumberFormatException e) {
                throw new InvalidNumberException();
            }

            setExperience(player, level);

        } else if (args.length == 2) {

            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            float level;

            // Ensure the target player exists
            if (target == null)
                throw new UnknownPlayerException(args[0]);


            // Ensure the user entered a valid float
            try {
                level = Float.parseFloat(args[1]);
            } catch (NumberFormatException e) {
                throw new InvalidNumberException();
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
     * Sets a players experience level to a defined float.
     *
     * @param player Player whose experience should be set.
     * @param level  Desired level as float.
     */
    private void setExperience (Player player, float level) {

        // Split the whole part and the quotient
        // e.g. 6.5 becomes 6 and 0.5
        int wholePart = (int) level;
        float quotient = level - wholePart;

        AtomicPlayer atomicPlayer = Main.getInstance().getPlayerManager().get(player);
        atomicPlayer.setLevel(wholePart);
        atomicPlayer.setExperienceFloat(quotient);
        atomicPlayer.updateExperience();

        player.sendMessage("Set level to " + ChatColor.GREEN + level + ChatColor.RESET + ".");

    }

}
