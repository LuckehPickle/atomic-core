package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.Reason;
import net.atomichive.core.util.CommandUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Sets a users game mode.
 */
public class CommandGameMode extends BaseCommand {


    public CommandGameMode () {
        super(
                "gamemode",
                "Sets a users game mode.",
                "/gamemode <mode> [player]",
                "atomic-core.gamemode",
                false,
                1
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

        // Attempt to retrieve specified game mode
        GameMode gamemode = getGameModeFromString(sender, args[0]);

        switch (args.length) {
            case 1:
                setGamemode(sender, gamemode);
                break;
            default:
                setGamemode(sender, gamemode, args[1]);
                break;
        }

    }


    /**
     * Sets a player's own game mode.
     *
     * @param sender Player who executed the command.
     * @param mode   Desired game mode.
     */
    private void setGamemode (CommandSender sender, GameMode mode) throws CommandException {

        // Ensure sender is a player
        if (!(sender instanceof Player)) {
            throw new CommandException(
                    Reason.INVALID_SENDER,
                    "Only players can set their own game mode."
            );
        }

        // Set game mode
        ((Player) sender).setGameMode(mode);

        // Alert sender
        sender.sendMessage(String.format(
                "You're game mode is now %s.",
                ChatColor.GREEN + mode.name().toLowerCase() + ChatColor.RESET
        ));

    }


    /**
     * Sets another player's game mode.
     *
     * @param sender Command sender.
     * @param mode   Desired game mode.
     * @param target Target player's name.
     */
    private void setGamemode (CommandSender sender, GameMode mode, String target)
            throws CommandException {

        // Ensure sender has permission
        if (!sender.hasPermission("atomic-core.gamemode.others")) {
            throw new CommandException(
                    Reason.INSUFFICIENT_PERMISSIONS,
                    "You do not have permission to set other player's game modes."
            );
        }

        // Get target player
        Player player = CommandUtil.parseTarget(target);
        String name = "Console";

        if (sender instanceof Player) {
            name = ((Player) sender).getDisplayName();
        }

        player.setGameMode(mode);

        sender.sendMessage(String.format(
                "%s's game mode is now %s.",
                ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET,
                ChatColor.GREEN + mode.name().toLowerCase() + ChatColor.RESET
        ));

        player.sendMessage(String.format(
                "%s has set you're game mode to %s.",
                ChatColor.YELLOW + name + ChatColor.RESET,
                ChatColor.GREEN + mode.name().toLowerCase() + ChatColor.RESET
        ));

    }


    /**
     * Returns a game mode which matches the entered string.
     *
     * @param mode Game mode as string.
     * @return Corresponding game mode object.
     */
    @SuppressWarnings("SpellCheckingInspection")
    private GameMode getGameModeFromString (CommandSender sender, String mode)
            throws CommandException {

        mode = mode.toLowerCase();

        switch (mode) {

            case "adventure":
            case "adv":
            case "a":
            case "2":

                // Ensure sender has permission
                if (!sender.hasPermission("atomic-core.gamemode.adventure")) {
                    throw new CommandException (
                            Reason.INSUFFICIENT_PERMISSIONS,
                            "You do not have permission to set adventure mode."
                    );
                }
                return GameMode.ADVENTURE;

            case "survival":
            case "surv":
            case "s":
            case "0":

                if (!sender.hasPermission("atomic-core.gamemode.survival")) {
                    throw new CommandException (
                            Reason.INSUFFICIENT_PERMISSIONS,
                            "You do not have permission to set survival mode."
                    );
                }
                return GameMode.SURVIVAL;

            case "creative":
            case "create":
            case "c":
            case "1":

                if (!sender.hasPermission("atomic-core.gamemode.creative")) {
                    throw new CommandException (
                            Reason.INSUFFICIENT_PERMISSIONS,
                            "You do not have permission to set creative mode."
                    );
                }
                return GameMode.CREATIVE;

            case "spectator":
            case "spectate":
            case "sp":
            case "3":

                if (!sender.hasPermission("atomic-core.gamemode.spectator")) {
                    throw new CommandException (
                            Reason.INSUFFICIENT_PERMISSIONS,
                            "You do not have permission to set spectator mode."
                    );
                }
                return GameMode.SPECTATOR;

            default:
                throw new CommandException(
                        Reason.UNKNOWN_GAMEMODE,
                        mode
                );

        }
    }

}
