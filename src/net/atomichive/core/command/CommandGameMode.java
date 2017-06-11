package net.atomichive.core.command;

import net.atomichive.core.exception.MissingPermissionException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command game mode
 * Allows users to change their game mode.
 * TODO Deal with /creative, /survival etc.
 */
public class CommandGameMode extends GlobalCommand {


	public CommandGameMode() {
		super(
				"gamemode",
				"Sets a users game mode.",
				"atomic-core.gamemode",
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
	public boolean run(CommandSender sender, String label, String[] args) {

		// If the sender is not a player, ensure more than one arg is entered.
		if (args.length < 2 && !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can set their own gamemode.");
			return false;
		}


		GameMode gameMode;
		Player target;

		// Handle /gamemode and /gm
		if (label.equalsIgnoreCase("gamemode")
				|| label.equalsIgnoreCase("gm")) {
			return handleGameModeCommand(sender, args);
		}


		return true;
	}



	/**
	 * Handle game mode command
	 * This function handles simple game mode commands
	 * @return Whether the command was successfully run.
	 */
	private boolean handleGameModeCommand(CommandSender sender, String[] args) {

		GameMode gameMode;

		if (args.length == 0) return false;

		try {
			gameMode = getGameModeFromString(sender, args[0]);
		} catch (MissingPermissionException exception) {
			sender.sendMessage(ChatColor.RED + exception.getMessage());
			return true;
		}

		// Ensure game mode exists
		if (gameMode == null) {
			sender.sendMessage(ChatColor.RED + "Unknown game mode: " + args[0]);
			return true;
		}

		if (args.length == 1) {

			// User wants to alter their own game mode
			Player player = (Player) sender;

			player.setGameMode(gameMode);
			player.sendMessage("Your game mode has been set to " + ChatColor.GREEN + gameMode.name());

		} else if (args.length == 2) {

			// User wants to alter another users game mode.

			// Ensure the user has permission
			if (!sender.hasPermission("atomic-core.gamemode.others")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command.");
				return true;
			}

			Player target = Bukkit.getPlayer(args[1]);

			// Ensure the player exists
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "The player " + args[0] + " could not be found.");
				return true;
			}

			// Set the game mode and update both players.
			target.setGameMode(gameMode);
			target.sendMessage("Your game mode has been set to " + ChatColor.GREEN + gameMode.name());
			sender.sendMessage("You've set " + target.getDisplayName() + "'s game mode to " + ChatColor.GREEN + gameMode.name());

		}

		return true;
	}



	/**
	 * Get game mode from string
	 * Returns a game mode which matches the entered string.
	 * @param mode Game mode as string.
	 * @return Corresponding game mode object, or null.
	 * @throws MissingPermissionException if the sender does
	 * not permission to set specified game mode.
	 */
	private GameMode getGameModeFromString(CommandSender sender, String mode)
			throws MissingPermissionException {

		mode = mode.toLowerCase();

		switch (mode) {
			case "adventure":
			case "adv":
			case "a":
			case "2":

				if (!sender.hasPermission("atomic-core.gamemode.adventure")) {
					throw new MissingPermissionException(
							"You do not have permission to set adventure mode."
					);
				}
				return GameMode.ADVENTURE;

			case "survival":
			case "surv":
			case "s":
			case "0":

				if (!sender.hasPermission("atomic-core.gamemode.survival")) {
					throw new MissingPermissionException(
							"You do not have permission to set survival mode."
					);
				}
				return GameMode.SURVIVAL;

			case "creative":
			case "create":
			case "c":
			case "1":

				if (!sender.hasPermission("atomic-core.gamemode.creative")) {
					throw new MissingPermissionException(
							"You do not have permission to set creative mode."
					);
				}
				return GameMode.CREATIVE;

			case "spectator":
			case "spectate":
			case "sp":
			case "3":

				if (!sender.hasPermission("atomic-core.gamemode.spectator")) {
					throw new MissingPermissionException(
							"You do not have permission to set spectator mode."
					);
				}
				return GameMode.SPECTATOR;

			default:
				return null;
		}
	}

}
