package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.util.PaginatedResult;
import net.atomichive.core.warp.Warp;
import net.atomichive.core.warp.WarpManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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
                "/warp [list|create|remove] [warp]",
                "atomic-core.warp",
                false,
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
    public boolean run (CommandSender sender, String label, String[] args) throws CommandException {

        // If no arguments are entered
        if (args.length == 0 || label.equalsIgnoreCase("warps")) {
            listWarps(sender, 1);
            return true;
        }

        if (args.length == 1 || args.length == 2) {

            String arg = args[0].toLowerCase();

            switch (arg) {
                case "list":
                case "l":
                    int page = 1;

                    if (args.length == 2) {
                        try {
                            page = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            throw new CommandException("Please enter a valid number.");
                        }
                    }

                    listWarps(sender, page);
                    break;
                case "add":
                case "a":
                case "create":
                case "+":
                    createWarp();
                    break;
                case "remove":
                case "rm":
                case "-":
                case "delete":
                    removeWarp();
                    break;
                default:
                    warp();
            }
        }

        return true;
    }


    /**
     * List warps
     * Sends a list of all warps to the sender.
     * @param sender Command sender.
     */
    private void listWarps (CommandSender sender, int page) {

        // Get all warps
        List<Warp> warps = WarpManager.getAll();

        // Create a new paginated result
        new PaginatedResult<Warp>("Warps") {

            @Override
            public String format (Warp warp) {

                String name = ChatColor.YELLOW + warp.getName() + ChatColor.RESET;

                if (warp.getMessage() != null) {
                    return String.format(
                            "%s: %s",
                            name,
                            warp.getMessage().substring(0, 25)
                    );
                }

                return name;
            }

        }.display(sender, warps, page);

    }



    /**
     * Create warp
     */
    private void createWarp () {

    }


    /**
     * Remove warp
     */
    private void removeWarp () {

    }


    /**
     * Warp
     */
    private void warp () {

    }

}
