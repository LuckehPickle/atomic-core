package net.atomichive.core.command;

import net.atomichive.core.exception.CommandException;
import net.atomichive.core.exception.PermissionException;
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
public class CommandGameMode extends BaseCommand {


    public CommandGameMode () {
        super(
                "gamemode",
                "Sets a users game mode.",
                "/gamemode [player] <mode>",
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
    public boolean run (CommandSender sender, String label, String[] args) throws CommandException, PermissionException {

        // If the sender is not a player, ensure more than one arg is entered.
        if (args.length < 2 && !(sender instanceof Player)) {
            throw new CommandException("Only players can set their own gamemode.");
        }


        GameMode gameMode;

        if (args.length == 0) return false;

        try {
            gameMode = getGameModeFromString(sender, args[0]);
        } catch (PermissionException exception) {
            sender.sendMessage(ChatColor.RED + exception.getMessage());
            return true;
        }

        // Ensure game mode exists
        if (gameMode == null) {
            throw new CommandException("Unknown game mode: " + args[0]);
        }

        if (args.length == 1) {

            // User wants to alter their own game mode
            Player player = (Player) sender;

            player.setGameMode(gameMode);
            player.sendMessage("Your game mode has been set to " + ChatColor.GREEN + gameMode.name() + ChatColor.RESET + ".");

        } else if (args.length == 2) {

            // User wants to alter another users game mode.

            // Ensure the user has permission
            if (!sender.hasPermission("atomic-core.gamemode.others")) {
                throw new PermissionException("You do not have permission to change another player's game mode.");
            }

            Player target = Bukkit.getPlayer(args[1]);

            // Ensure the player exists
            if (target == null)
                throw new CommandException("The player " + args[0] + " could not be found.");

            // Set the game mode and update both players.
            target.setGameMode(gameMode);
            target.sendMessage("Your game mode has been set to " + ChatColor.GREEN + gameMode.name() + ChatColor.RESET + ".");
            sender.sendMessage("You've set " + ChatColor.YELLOW + target.getDisplayName() + ChatColor.RESET + "'s game mode to " + ChatColor.GREEN + gameMode.name() + ChatColor.RESET + ".");

        }

        return true;
    }



    /**
     * Get game mode from string
     * Returns a game mode which matches the entered string.
     * @param mode Game mode as string.
     * @return Corresponding game mode object, or null.
     * @throws PermissionException if the sender does
     *                                    not permission to set specified game mode.
     */
    private GameMode getGameModeFromString (CommandSender sender, String mode)
            throws PermissionException {

        mode = mode.toLowerCase();

        switch (mode) {
            case "adventure":
            case "adv":
            case "a":
            case "2":

                if (!sender.hasPermission("atomic-core.gamemode.adventure")) {
                    throw new PermissionException(
                            "You do not have permission to set adventure mode."
                    );
                }
                return GameMode.ADVENTURE;

            case "survival":
            case "surv":
            case "s":
            case "0":

                if (!sender.hasPermission("atomic-core.gamemode.survival")) {
                    throw new PermissionException(
                            "You do not have permission to set survival mode."
                    );
                }
                return GameMode.SURVIVAL;

            case "creative":
            case "create":
            case "c":
            case "1":

                if (!sender.hasPermission("atomic-core.gamemode.creative")) {
                    throw new PermissionException(
                            "You do not have permission to set creative mode."
                    );
                }
                return GameMode.CREATIVE;

            case "spectator":
            case "spectate":
            case "sp":
            case "3":

                if (!sender.hasPermission("atomic-core.gamemode.spectator")) {
                    throw new PermissionException(
                            "You do not have permission to set spectator mode."
                    );
                }
                return GameMode.SPECTATOR;

            default:
                return null;
        }
    }

}
