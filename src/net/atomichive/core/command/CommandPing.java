package net.atomichive.core.command;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Command Ping
 * Returns a players delay in ms.
 */
public class CommandPing implements CommandExecutor {


    // Output format
    private final static String FORMAT = "%s%dms";
    private Server server;


    /**
     * Ping constructor
     * @param server A reference to the Bukkit server.
     */
    public CommandPing (Server server) {
        this.server = server;
    }


    /**
     * On command
     * Fires whenever this command is run.
     * @param sender Whoever, or whatever, sent the command.
     * @param command The command being run.
     * @param label The exact user input (could be an alias, or have different case).
     * @param args Command line arguments.
     * @return Whether the command was completed successfully.
     */
    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {

        // Ensure player has permission.
        if (!sender.hasPermission("atomic-core.ping")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command.");
            return true;
        }


        if (args.length == 0) {

            // Ensure sender is a player
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "This command can only be run by players.");
                return true;
            }

            // Cast to player
            Player player = (Player) sender;

            // Output the players own ping
            int ping = getPing(player);
            player.sendMessage(colourise(ping));
            return true;

        }


        if (args.length == 1) {

            // Ensure the user has permission
            if (!sender.hasPermission("atomic-core.ping.others")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command.");
                return true;
            }

            // Retrieve the target player
            Player target = server.getPlayer(args[0]);

            // Ensure target exists
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "The player " + args[0] + " could not be found.");
                return true;
            }

            int ping = getPing(target);
            sender.sendMessage(target.getDisplayName() + "'s ping: " + colourise(ping));
            return true;
        }

        return false;

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
