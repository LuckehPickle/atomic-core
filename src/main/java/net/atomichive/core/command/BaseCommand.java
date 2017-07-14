package net.atomichive.core.command;

import net.atomichive.core.Main;
import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 * Command.
 * A generic class that all commands extend. This class
 * also handles command registration. Any subclasses
 * will automatically be registered when they are
 * initialised.
 */
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
     * @param name           Name of the command.
     * @param description    Command description.
     * @param usage          Command usage.
     * @param permission     Permission node required to use the command.
     * @param requiresPlayer Whether the command can only be run by a player.
     * @param steps          The minimum number of steps arguments required from the user.
     */
    BaseCommand (String name, String description, String usage, String permission, boolean requiresPlayer, int steps) {

        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.requiresPlayer = requiresPlayer;
        this.steps = steps;

        register();

    }


    /**
     * Register
     * Registers this command with Bukkit.
     */
    private void register () {
        Main.getInstance()
                .getServer()
                .getPluginCommand(name)
                .setExecutor(this);
    }


    /**
     * On command
     * @param sender Object that sent the command.
     * @param cmd    Command that was run.
     * @param label  The exact command label typed by the user.
     * @param args   Any arguments.
     * @return Whether the command was successfully run.
     */
    @Override
    public boolean onCommand (CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {

        // Ensure sender has appropriate permissions
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        // Ensure sender is a player (if necessary)
        if (requiresPlayer && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        // Ensure enough args where entered
        if (args.length < steps) {
            sender.sendMessage(ChatColor.RED + "Error: Not enough args. " + this.getUsage());
            return true;
        }

        try {
            return run(sender, label, args);
        } catch (CommandException | PermissionException e) {
            sender.sendMessage(e.getMessage());
            return true;
        }

    }


    /**
     * Run
     * Toggles a players flight state.
     * @param sender The object that sent the command.
     * @param label  The exact command label typed by the user.
     * @param args   Any command arguments.
     * @throws CommandException if an error occurs.
     * @throws PermissionException if the user doesn't have
     *                                    appropriate permissions.
     */
    public abstract boolean run (CommandSender sender, String label, String[] args) throws CommandException, PermissionException;


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

    public boolean isRequiresPlayer () {
        return requiresPlayer;
    }

    public int getSteps () {
        return steps;
    }

}
