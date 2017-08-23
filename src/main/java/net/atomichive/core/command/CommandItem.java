package net.atomichive.core.command;

import com.google.gson.stream.MalformedJsonException;
import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.CustomObjectException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.item.CustomItem;
import net.atomichive.core.item.ItemManager;
import net.atomichive.core.util.CommandUtil;
import net.atomichive.core.util.PaginatedResult;
import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.logging.Level;

/**
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


        switch (args[0].toLowerCase()) {
            case "reload":
            case "r":
                reloadItems(sender);
                return;
            case "list":
            case "l":
                listItems(sender, args);
                return;
        }


        // Options
        Player target = null;
        String material;
        int amount = 1;


        // Parse options
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

        // Ensure player has permission
        if (target != null && !sender.hasPermission("atomic-core.item.others")) {
            throw new CommandException(
                    Reason.INSUFFICIENT_PERMISSIONS,
                    "You do not have permission to give other players items."
            );
        }

        // Ensure sender is player if necessary.
        if (target == null && !(sender instanceof Player)) {
            throw new CommandException(
                    Reason.INVALID_SENDER,
                    "Only players can give themselves items."
            );
        }


        if (target == null)
            target = (Player) sender;

        give(sender, target, material, amount);

    }


    /**
     * Reloads all custom items from items.json.
     *
     * @param sender Command sender.
     */
    private void reloadItems (CommandSender sender) throws CommandException {

        // Reload items and get count
        int found;

        try {
            found = Main.getInstance().getItemManager().load();
        } catch (MalformedJsonException e) {
            throw new CommandException("Malformed JSON in items.json. View console for more information.");
        }

        // Update found for output purposes.
        if (found == -1)
            found = 0;


        String out;

        if (found == 1) {
            out = "Found " + ChatColor.GREEN + "1" + ChatColor.RESET + " custom item.";
        } else {
            out = String.format(
                    "Found %s custom items.",
                    ChatColor.GREEN + "" + found + ChatColor.RESET
            );
        }

        sender.sendMessage(out);

    }


    /**
     * Lists all custom items.
     *
     * @param sender Command sender.
     * @param args   Command arguments.
     */
    private void listItems (CommandSender sender, String[] args) throws CommandException {

        int page = 0;

        if (args.length >= 2) {
            try {
                page = Integer.parseInt(args[1]) - 1;
            } catch (NumberFormatException e) {
                throw new CommandException("Please enter a valid number.");
            }
        }

        // Get all warps
        List<CustomItem> items = Main.getInstance().getItemManager().getCustomItems();

        // Create a new paginated result
        new PaginatedResult<CustomItem>("Custom items") {

            @Override
            public String format (CustomItem item) {
                return String.format(
                        "%s (%s) [%s]",
                        item.getName() + ChatColor.GRAY,
                        item.getRarity(),
                        item.getMaterial()
                );
            }

        }.display(sender, items, page);

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
     * Gives a player an item
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
            if (material == null) {
                throw new CommandException("Unknown item/material '" + material + "'.");
            }

            stack = new ItemStack(mat, amount);

        } else {
            try {
                stack = item.getItemStack(amount);
            } catch (CustomObjectException e) {
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
