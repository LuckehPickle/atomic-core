package net.atomichive.core.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.atomichive.core.command.GlobalCommand;

/**
 * Command Ping
 * Returns a players delay in ms.
 */
public class CommandPing extends GlobalCommand {


    // Output format
    private final static String FORMAT = "%s%dms";
    
	@Override
	public void run(CommandSender sender, String[] args) {
		if(args.length == 0) {
			// Cast to player
			Player player = (Player) sender;

			// Output the players own ping
			int ping = getPing(player);
			player.sendMessage(colourise(ping));
			return;
		}	else if (args.length == 1) {
			// Ensure the user has permission
			if (!sender.hasPermission("atomic.ping.others")) {
				sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command.");
				return;
			}

			// Retrieve the target player
			Player target = Bukkit.getPlayer(args[0]);

			// Ensure target exists
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "The player " + args[0] + " could not be found.");
				return;
			}

			int ping = getPing(target);
			sender.sendMessage(target.getDisplayName() + "'s ping: " + colourise(ping));
		}
	}

	@Override
	public String getName() {
		return "ping";
	}


	@Override
	public int getSteps() {
		return 0;
	}


	@Override
	public String getDescription() {
		return "Get your or a player's ping.";
	}


	@Override
	public boolean requiresPlayer() {
		return true;
	}


	@Override
	public String getPermission() {
		return "atomic.ping";
	}


    /**
     * Get ping
     * Returns a players ping (this function does not check
     * if a player is online).
     * @param player Player whose ping should be retrieved.
     * @return Delay in ms.
     */
    private int getPing (Player player) {
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
    private String colourise (int ping) {

        ChatColor colour;

        if      (ping < 100) colour = ChatColor.GREEN;
        else if (ping < 200) colour = ChatColor.YELLOW;
        else                 colour = ChatColor.RED;

        return String.format(FORMAT, colour, ping);

    }

}
