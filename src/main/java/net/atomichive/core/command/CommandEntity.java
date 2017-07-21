package net.atomichive.core.command;

import com.google.gson.stream.MalformedJsonException;
import net.atomichive.core.Main;
import net.atomichive.core.entity.CustomEntity;
import net.atomichive.core.entity.EntityManager;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.EntityException;
import net.atomichive.core.exception.PermissionException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.util.PaginatedResult;
import net.atomichive.core.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Command Entity (Atomic Entity)
 */
public class CommandEntity extends BaseCommand {


    public CommandEntity () {
        super(
                "entity",
                "Used to manage Atomic Entities.",
                "/entity [reload|list|spawn]",
                "atomic-core.atomic-entity",
                false,
                0
        );
    }



    /**
     * Run
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

        if (args.length == 0) {
            listEntities(sender, args);
        } else {

            String arg = args[0].toLowerCase();

            switch (arg) {
                case "reload":
                case "r":
                    reloadEntities(sender);
                    break;
                case "list":
                case "l":
                    listEntities(sender, args);
                    break;
                case "spawn":
                case "s":
                    spawnEntity(sender, args);
                    break;
                default:
                    throw new CommandException("Unknown option " + args[0] + ".");
            }

        }

    }


    /**
     * Reload entities
     * Reloads all custom entities from entities.json.
     * @param sender The person or thing that sent the command.
     */
    private void reloadEntities (CommandSender sender) throws CommandException {

        // Reload entities and get count
        int found = 0;

        try {
            found = EntityManager.load();
        } catch (MalformedJsonException e) {
            throw new CommandException("Malformed JSON in entities.json. View console for more information.");
        }

        // Update found for output purposes.
        if (found == -1)
            found = 0;


        String out;

        if (found == 1) {
            out = "Found " + ChatColor.GREEN + "1" + ChatColor.RESET + " custom entity.";
        } else {
            out = String.format(
                    "Found %s custom entities.",
                    ChatColor.GREEN + "" + found + ChatColor.RESET
            );
        }

        sender.sendMessage(out);

    }


    /**
     * List entities
     * @param sender Command sender.
     * @param args Any command arguments.
     */
    private void listEntities (CommandSender sender, String[] args) throws CommandException {

        int page = 1;

        if (args.length >= 2) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new CommandException("Please enter a valid number.");
            }
        }

        // Get all warps
        List<CustomEntity> entities = EntityManager.getAll();

        // Create a new paginated result
        new PaginatedResult<CustomEntity>("Custom entities") {

            @Override
            public String format (CustomEntity entity) {
                return String.format(
                        "%s [%s]",
                        entity.getName() + ChatColor.GRAY,
                        entity.getAtomicClass()
                );
            }

        }.display(sender, entities, page);

    }



    /**
     * Spawn entity
     * @param sender Command sender.
     * @param args Any command arguments.
     */
    private void spawnEntity (CommandSender sender, String[] args) throws CommandException {

        // Ensure sender is a player
        if (!(sender instanceof Player)) {
            throw new CommandException(
                    Reason.INVALID_SENDER,
                    "Only players can use this command."
            );
        }

        // Ensure enough args where entered
        if (args.length == 1) {
            throw new CommandException(
                    Reason.INVALID_USAGE,
                    "/entity spawn <entity> [count]"
            );
        }

        // Get player
        Player player = (Player) sender;
        String entity = args[1];
        int count = 1;
        Location location = null;


        // Handle entity count.
        if (args.length >= 3) {
            try {
                int max = Main.getInstance().getBukkitConfig().getInt("max_spawn_count", 100);
                count = Integer.parseInt(args[2]);

                // Ensure count is positive
                if (count < 0)
                    throw new CommandException("Please enter a positive value.");

                // Ensure count is not too high
                if (count > max)
                    throw new CommandException("Maximum spawn count exceeded. Please enter a value lower than " + max + ".");

            } catch (NumberFormatException e) {
                throw new CommandException("Please enter a valid number.");
            }
        }


        location = Util.trace(player);

        // Ensure a location was found
        if (location == null)
            throw new CommandException("No block in sight.");


        // Attempt to spawn entity
        try {
            EntityManager.spawnEntity(location, entity, count, player);
        } catch (EntityException e) {
            player.sendMessage(e.getMessage());
            return;
        }

        player.sendMessage("Spawned " + ChatColor.GREEN + count + ChatColor.RESET + " entities.");

    }


}
