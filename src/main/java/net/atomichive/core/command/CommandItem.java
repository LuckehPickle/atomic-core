package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.CustomElementException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.item.CustomItem;
import net.atomichive.core.item.ItemManager;
import net.atomichive.core.util.CommandUtil;
import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

/**
 * Command Item
 * Give an item to the specified player.
 */
public class CommandItem extends BaseCommand {


    public CommandItem () {
        super(
                "item",
                "Give an item to the specified player.",
                "/item [player] <material> [amount]",
                "atomic-core.item",
                false,
                1
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


        // Options
        Player target = null;
        String material;
        int amount = 1;


        // Parse options
        try {
            switch (args.length) {
                case 1:
                    material = args[0];
                    break;
                case 2:
                    if (Util.isInteger(args[1])) {
                        material = args[0];
                        amount = parseAmount(args[1]);
                    } else {
                        target = CommandUtil.parseTarget(args[0]);
                        material = args[1];
                    }
                    break;
                default:
                    target = CommandUtil.parseTarget(args[0]);
                    material = args[1];
                    amount = parseAmount(args[2]);
            }
        } catch (CommandException e) {
            throw e;
        }

        // Ensure player has permission
        if (target != null && !sender.hasPermission("atomic-core.item.others"))
            throw new PermissionException("You do not have permission to give other players items.");

        // Ensure sender is player if necessary.
        if (target == null && !(sender instanceof Player))
            throw new CommandException("Only players can give themselves items.");


        if (target == null)
            target = (Player) sender;

        give(sender, target, material, amount);

    }


    /**
     * Parse amount
     *
     * @param arg Amount of items to give.
     * @return An integer representing the amount.
     */
    private int parseAmount (String arg) throws CommandException {

        // Ensure arg is an int
        if (!Util.isInteger(arg))
            throw new CommandException("Please enter a valid amount.");

        int amount = Integer.parseInt(arg);

        // Ensure amount is positive
        if (amount < 0)
            throw new CommandException("Please enter a positive amount.");

        return amount;

    }


    /**
     * Give
     *
     * @param sender   Command sender.
     * @param target   Player to receive item.
     * @param material Name of material or item to give.
     * @param amount   Number of items to give.
     * @throws CommandException If the provided material or item can not be found.
     */
    private void give (CommandSender sender, Player target, String material, int amount)
            throws CommandException {

        ItemManager manager = Main.getInstance().getItemManager();
        CustomItem item = manager.getItemByName(material);
        ItemStack stack;

        if (item == null) {

            Material mat = Util.getEnumValue(Material.class, material);

            // Ensure material exists
            if (mat == null) {
                throw new CommandException("Unknown item/material '" + material + "'.");
            }

            stack = new ItemStack(mat, amount);

        } else {
            try {
                stack = item.getItemStack(amount);
            } catch (CustomElementException e) {
                Main.getInstance().log(Level.SEVERE, e.getMessage());
                throw new CommandException(e.getMessage());
            }
        }

        target.getInventory().addItem(stack);


        // Alert user(s)
        String itemName;

        if (stack.hasItemMeta()) {
            itemName = stack.getItemMeta().getDisplayName();
        } else {
            itemName = stack.getType().name();
        }

        if (target.equals(sender)) {

            sender.sendMessage(String.format(
                    "Giving %s %s.",
                    ChatColor.GREEN + String.valueOf(amount) + ChatColor.RESET,
                    ChatColor.GREEN + itemName + ChatColor.RESET
            ));

        } else {

            String name = "Console";

            if (sender instanceof Player)
                name = ((Player) sender).getDisplayName();

            sender.sendMessage(String.format(
                    "Giving %s %s to %s.",
                    ChatColor.GREEN + String.valueOf(amount) + ChatColor.RESET,
                    ChatColor.GREEN + itemName + ChatColor.RESET,
                    ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET
            ));

            target.sendMessage(String.format(
                    "%s gave you %s %s.",
                    ChatColor.YELLOW + name + ChatColor.RESET,
                    ChatColor.GREEN + String.valueOf(amount) + ChatColor.RESET,
                    ChatColor.GREEN + itemName + ChatColor.RESET
            ));

        }

    }

}
