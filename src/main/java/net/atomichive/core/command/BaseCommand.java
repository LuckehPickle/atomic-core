package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 * A generic class that all commands extend. This class
 * also handles command registration. Any subclasses
 * will automatically be registered when they are
 * initialised.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseCommand implements CommandExecutor {


    // Attributes
    private final String name;
    private final String description;
    private final String usage;
    private final String permission;
    private final boolean requiresPlayer;
    private final int steps;


    /**
     * Command constructor
     *
     * @param name           Name of the command.
     * @param description    Command description.
     * @param usage          Command usage.
     * @param permission     Permission node required to use the command.
     * @param requiresPlayer Whether the command can only be run by a player.
     * @param steps          The minimum number of steps arguments required from the user.
     */
    BaseCommand (
            String name,
            String description,
            String usage,
            String permission,
            boolean requiresPlayer,
            int steps) {

        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.requiresPlayer = requiresPlayer;
        this.steps = steps;

        register();

    }


    /**
     * Registers this command with Bukkit.
     */
    private void register () {
        Main.getInstance()
                .getServer()
                .getPluginCommand(name)
                .setExecutor(this);
    }


    /**
     * Function fired whenever this command is called.
     *
     * @param sender Command sender.
     * @param cmd    Command that was run.
     * @param label  The exact command label typed by the user.
     * @param args   Command arguments.
     * @return Whether the command was successfully run.
     */
    @Override
    public boolean onCommand (CommandSender sender, Command cmd, String label, String[] args) {

        try {

            // Ensure sender has appropriate permissions
            if (!sender.hasPermission(permission)) {
                throw new CommandException(
                        Reason.INSUFFICIENT_PERMISSIONS,
                        "You do not have permission to use this command."
                );
            }

            // Ensure sender is a player (if necessary)
            if (requiresPlayer && !(sender instanceof Player)) {
                throw new CommandException(
                        Reason.INVALID_SENDER,
                        "Only players can execute this command."
                );
            }

            // Ensure enough args where entered
            if (args.length < steps) {
                throw new CommandException(
                        Reason.INVALID_USAGE,
                        this.usage
                );
            }

            run(sender, label, args);

        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;

    }


    /**
     * Executes this command.
     *
     * @param sender Command sender.
     * @param label  The exact command label typed by the user.
     * @param args   Command arguments.
     * @throws CommandException if a generic error occurs.
     */
    public abstract void run (CommandSender sender, String label, String[] args)
            throws CommandException;


	/*
        Getters from here down.
	 */

    public String getName () {
        return name;
    }

    public String getDescription () {
        return description;
    }

    public String getUsage () {
        return usage;
    }

    public String getPermission () {
        return permission;
    }

}
