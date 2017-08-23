package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.exception.UnknownPlayerException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Returns the delay between a player and the server
 * in milliseconds.
 */
public class CommandPing extends BaseCommand {


    // Output format
    private final static String FORMAT = "%s%dms";
    private final static String PING_FAILED_MESSAGE = "Your ping could not be retrieved. Note for devs: Minecraft may have changed the way it handles CraftPlayer objects.";


    public CommandPing () {
        super(
                "ping",
                "Returns the delay between a players and the server in milliseconds.",
                "/ping [player]",
                "atomic-core.ping",
                false,
                0
        );
    }


    /**
     * Executes this command.
     *
     * @param sender Command sender.
     * @param label  The exact command label typed by the user.
     * @param args   Command arguments.
     * @throws CommandException if a generic error occurs.
     */
    @Override
    public void run (CommandSender sender, String label, String[] args)
            throws CommandException {

        // If the sender is not a player, ensure more than one arg is entered.
        if (args.length == 0 && !(sender instanceof Player)) {
            throw new CommandException(
                    Reason.INVALID_SENDER,
                    "Only players can view their own ping."
            );
        }


        if (args.length == 0) {

            // Output the players own ping
            Player player = (Player) sender;

            Integer ping = getPing(player);

            if (ping == null)
                throw new CommandException(PING_FAILED_MESSAGE);

            player.sendMessage(colourise(ping));

        } else if (args.length == 1) {

            // Ensure the user has permission
            if (!sender.hasPermission("atomic-core.ping.others")) {
                throw new CommandException(
                        Reason.INSUFFICIENT_PERMISSIONS,
                        "You do not have permission to view another player's ping."
                );
            }

            // Retrieve the target player
            Player target = Bukkit.getPlayer(args[0]);

            // Ensure target exists
            if (target == null) {
                throw new UnknownPlayerException(args[0]);
            }

            Integer ping = getPing(target);

            if (ping == null)
                throw new CommandException(PING_FAILED_MESSAGE);

            sender.sendMessage(target.getDisplayName() + "'s ping: " + colourise(ping));

        }

    }


    /**
     * Returns a players ping. Reflection is used to
     * make this function version independent.
     *
     * @param player Player whose ping should be retrieved.
     * @return Delay in ms.
     */
    private Integer getPing (Player player) {

        String serverName = Bukkit
                .getServer()
                .getClass()
                .getPackage()
                .getName();

        String serverVersion = serverName
                .substring(serverName.lastIndexOf(".") + 1, serverName.length());

        try {

            Class craftClass = Class.forName("org.bukkit.craftbukkit." + serverVersion + ".entity.CraftPlayer");
            Object craftPlayer = craftClass.cast(player);

            Method getHandle = craftPlayer.getClass().getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(craftPlayer);

            Field ping = entityPlayer.getClass().getDeclaredField("ping");

            return ping.getInt(entityPlayer);

        } catch (
                ClassNotFoundException |
                        InvocationTargetException |
                        NoSuchFieldException |
                        IllegalAccessException |
                        NoSuchMethodException e
                ) {
            return null;
        }

    }


    /**
     * Colourise
     * Adds colour to a ping depending on how high it is.
     *
     * @param ping Ping to colourise.
     * @return A colourised ping.
     */
    private String colourise (int ping) {

        ChatColor colour;

        if (ping < 100) colour = ChatColor.GREEN;
        else if (ping < 200) colour = ChatColor.YELLOW;
        else colour = ChatColor.RED;

        return String.format(FORMAT, colour, ping);

    }

}
