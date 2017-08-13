package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.exception.UnknownWarpException;
import net.atomichive.core.exception.UnknownWorldException;
import net.atomichive.core.util.PaginatedResult;
import net.atomichive.core.util.Util;
import net.atomichive.core.warp.Warp;
import net.atomichive.core.warp.WarpDAO;
import net.atomichive.core.warp.WarpManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Command Warp
 * Designate a warp destination, or warp somewhere.
 */
public class CommandWarp extends BaseCommand {

    public CommandWarp () {
        super(
                "warp",
                "Designate a warp destination, or warp somewhere.",
                "/warp [list|create|remove|warp-name]",
                "atomic-core.warp",
                false,
                0
        );
    }


    /**
     * Run
     * The main logic for the command is handled here.
     *
     * @param sender The object that sent the command.
     * @param label  The exact command label typed by the user.
     * @param args   Any command arguments.
     */
    @Override
    public void run (CommandSender sender, String label, String[] args) throws CommandException {

        // If no arguments are entered
        if (args.length == 0) {
            throw new CommandException(Reason.INVALID_USAGE, getUsage());
        } else {

            String arg = args[0].toLowerCase();

            switch (arg) {
                case "help":
                case "?":
                    sender.sendMessage(getUsage());
                    break;
                case "list":
                case "l":
                    listWarps(sender, args);
                    break;
                case "add":
                case "a":
                case "create":
                case "set":
                    createWarp(sender, args);
                    break;
                case "remove":
                case "rm":
                case "delete":
                    removeWarp(sender, args);
                    break;
                default:
                    warp(sender, args);
            }
        }

    }


    /**
     * List warps
     * Sends a list of all warps to the sender.
     *
     * @param sender Command sender.
     * @param args   Command arguments.
     */
    private void listWarps (CommandSender sender, String[] args)
            throws CommandException {

        int page = 1;

        if (args.length >= 2) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new CommandException("Please enter a valid number.");
            }
        }


        // Get all warps
        List<Warp> warps = Main.getInstance().getWarpManager().getAll();

        // Create a new paginated result
        new PaginatedResult<Warp>("Warps") {

            @Override
            public String format (Warp warp) {
                return warp.toString();
            }

        }.display(sender, warps, page);

    }


    /**
     * Create warp
     *
     * @param sender Command sender.
     * @param args   Command arguments.
     */
    private void createWarp (CommandSender sender, String[] args)
            throws CommandException {

        // Ensure sender is a player
        if (!(sender instanceof Player)) {
            throw new CommandException(
                    Reason.INVALID_SENDER,
                    "Only players can create warps."
            );
        }

        // Ensure enough args where entered
        if (args.length == 1) {
            throw new CommandException(
                    Reason.INVALID_USAGE,
                    "/warp create <name> [message...]"
            );
        }

        Player player = (Player) sender;
        Location location = player.getLocation();
        String name = args[1];
        String message = null;
        WarpManager manager = Main.getInstance().getWarpManager();

        // Get message
        if (args.length >= 3) {

            message = Util.argsJoiner(args, 2);

            // Truncate if necessary
            if (message.length() > 64)
                message = message.substring(0, 61) + "...";

        }

        // Ensure warp does not already exist
        if (manager.contains(name))
            throw new CommandException("A warp named '" + name + "' already exists.");

        // Create new warp
        Warp warp = new Warp(name, location);
        warp.setMessage(message);

        // Add to managers and DB
        WarpDAO.insert(warp);
        manager.add(warp);

        player.sendMessage("Created warp " + ChatColor.GREEN + warp.toString() + ChatColor.RESET + ".");

    }


    /**
     * Remove warp
     *
     * @param sender Command sender.
     * @param args   Command arguments.
     */
    private void removeWarp (CommandSender sender, String[] args)
            throws CommandException {

        // Ensure enough args where entered
        if (args.length == 1) {
            throw new CommandException(
                    Reason.INVALID_USAGE,
                    "/warp remove <name>"
            );
        }

        Warp warp;

        // Attempt to delete warp
        try {
            warp = Main.getInstance().getWarpManager().delete(args[1]);
        } catch (UnknownWarpException e) {
            throw new CommandException(e.getMessage());
        }

        sender.sendMessage("Deleted warp " + ChatColor.RED + warp.toString() + ChatColor.RESET + ".");

    }


    /**
     * Warp
     *
     * @param sender Command sender.
     * @param args   Command arguments.
     */
    private void warp (CommandSender sender, String[] args) throws CommandException {

        // Ensure sender is a player
        if (!(sender instanceof Player)) {
            throw new CommandException(
                    Reason.INVALID_SENDER,
                    "Only players can warp."
            );
        }

        // Retrieve target warp
        Warp warp = Main.getInstance().getWarpManager().get(args[0]);
        Player player = (Player) sender;

        // Ensure warp exists
        if (warp == null)
            throw new CommandException("Unknown warp '" + args[0] + "'.");

        // Attempt to warp player.
        try {
            warp.warpPlayer(player);
        } catch (UnknownWorldException e) {
            throw new CommandException(
                    Reason.WARP_FAILED,
                    "Unknown destination world. Has it been deleted?"
            );
        }

    }

}
