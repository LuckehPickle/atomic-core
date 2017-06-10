package net.atomichive.core.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Command Ping
 * Returns a players delay in ms.
 */
public class CommandPing extends GlobalCommand {

	// Output format
	private final static String FORMAT = "%s%dms";


	/**
	 * Ping Command Constructor
	 */
	public CommandPing() {
		super(
				"ping",
				"Returns a players delay in ms.",
				"atomic-core.ping",
				false,
				0
		);
	}


	/**
	 * Run
	 * @param sender The object that sent the command.
	 * @param label  The exact command label typed by the user.
	 * @param args   Any command arguments.
	 */
	@Override
	public boolean run(CommandSender sender, String label, String[] args) {

		// If the sender is not a player, ensure more than one arg is entered.
		if (args.length == 0 && !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can view their own ping.");
			return false;
		}


		if (args.length == 0) {

			// Output the players own ping
			Player player = (Player) sender;

			int ping = getPing(player);
			player.sendMessage(colourise(ping));

		} else if (args.length == 1) {

			// Ensure the user has permission
			if (!sender.hasPermission("atomic-core.ping.others")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command.");
				return true;
			}

			// Retrieve the target player
			Player target = Bukkit.getPlayer(args[0]);

			// Ensure target exists
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "The player " + args[0] + " could not be found.");
				return true;
			}

			int ping = getPing(target);
			sender.sendMessage(target.getDisplayName() + "'s ping: " + colourise(ping));

		}

		return true;

	}


	/**
	 * Get ping
	 * Returns a players ping (this function does not check
	 * if a player is online).
	 * @param player Player whose ping should be retrieved.
	 * @return Delay in ms.
	 */
	private int getPing(Player player) {
		// Cast player to craft player
		CraftPlayer craftPlayer = (CraftPlayer) player;
		return craftPlayer.getHandle().ping;
	}


	/**
	 * Colourise
	 * Adds colour to a ping depending on how high it is.
	 * @param ping Ping to colourise.
	 * @return A colourised ping.
	 */
	private String colourise(int ping) {

		ChatColor colour;

		if (ping < 100) colour = ChatColor.GREEN;
		else if (ping < 200) colour = ChatColor.YELLOW;
		else colour = ChatColor.RED;

		return String.format(FORMAT, colour, ping);

	}

}
