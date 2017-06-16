package net.atomichive.core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.atomichive.core.Main;


/**
 * Command.
 * A generic class that all commands extend.
 */
public abstract class CommandBase implements CommandExecutor {


	// Attributes
	private final String name;
	private final String description;
	private final String permission;
	private final boolean requiresPlayer;
	private final int steps;


	/**
	 * Command constructor
	 * @param name           Name of the command.
	 * @param description    Command description.
	 * @param permission     Permission node required to use the command.
	 * @param requiresPlayer Whether the command can only be run by a player.
	 * @param steps          The minimum number of steps arguments required from the user.
	 */
	CommandBase(String name, String description, String permission, boolean requiresPlayer, int steps) {

		this.name = name;
		this.description = description;
		this.permission = permission;
		this.requiresPlayer = requiresPlayer;
		this.steps = steps;

		register();

	}


	/**
	 * Register
	 * Registers this command with Bukkit.
	 */
	private void register() {
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
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {

		if (!cmd.getName().equalsIgnoreCase(name)) return false;

		// Ensure sender has appropriate permissions
		if (!sender.hasPermission(permission)) return false;

		// Ensure sender is a player (if necessary)
		if (requiresPlayer && !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
			return true;
		}

		// Ensure enough args where entered
		if (args.length < steps) return false;

		return run(sender, label, args);

	}


	/**
	 * Run
	 * The main logic for the command is handled here.
	 * @param sender The object that sent the command.
	 * @param label  The exact command label typed by the user.
	 * @param args   Any command arguments.
	 */
	public abstract boolean run(CommandSender sender, String label, String[] args);


	/*
		Getters from here down.
	 */

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPermission() {
		return permission;
	}

	public boolean isRequiresPlayer() {
		return requiresPlayer;
	}

	public int getSteps() {
		return steps;
	}

}
