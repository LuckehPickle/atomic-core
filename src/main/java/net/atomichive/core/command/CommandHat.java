package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Put any block on your head.
 */
public class CommandHat extends BaseCommand {


    public CommandHat () {
        super (
                "hat",
                "Put any block on your head.",
                "/hat",
                "atomic-core.hat",
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

        // Get item in main hand
        ItemStack stack = player.getInventory().getItemInMainHand();

        // Ensure an item is selected
        if (stack.getType().equals(Material.AIR)) {
            throw new CommandException(
                    Reason.GENERIC_ERROR,
                    "Your hand is currently empty."
            );
        }

        // Move helmet to inventory
        player.getInventory().addItem(player.getInventory().getHelmet());

        // Set new helment
        player.getInventory().setHelmet(new ItemStack(stack.getType()));

        // Reduce stack size
        stack.setAmount(stack.getAmount() - 1);

        player.sendMessage(randomMessage());

    }


    /**
     * Selects a random message to display. Just for fun.
     *
     * @return Random message.
     */
    private String randomMessage () {

        Random random = new Random();

        String[] messages = new String[] {
                "Wow, nice hat!",
                "Enjoy your fresh new hat.",
                "Does this make my head look big?",
                "This one's a bit too tight...",
                "I wish I had a hat like that.",
                "It's like a shoe for your brain."
        };

        return messages[random.nextInt(messages.length)];

    }

}
