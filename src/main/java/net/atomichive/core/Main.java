package net.atomichive.core;

import com.google.gson.stream.MalformedJsonException;
import io.seanbailey.database.DatabaseManager;
import net.atomichive.core.command.*;
import net.atomichive.core.entity.EntityManager;
import net.atomichive.core.listeners.EntityDamageListener;
import net.atomichive.core.listeners.LoginListener;
import net.atomichive.core.listeners.QuitListener;
import net.atomichive.core.listeners.SlimeSplitListener;
import net.atomichive.core.player.AtomicPlayerDAO;
import net.atomichive.core.player.PlayerManager;
import net.atomichive.core.warp.WarpDAO;
import net.atomichive.core.warp.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Main
 * Main class for the atomic-core plugin.
 */
public class Main extends JavaPlugin {

    private static Main instance;

    private Configuration config;
    private Logger logger;
    private DatabaseManager manager;


    /**
     * On enable
     * Run whenever the server enables this plugin.
     */
    @Override
    public void onEnable () {

        // Init
        instance = this;
        logger = getLogger();

        // Load configuration file
        saveDefaultConfig();
        config = this.getConfig();

        initDatabase();
        registerCommands();
        registerEvents();

        if (config.getBoolean("development_mode", false)) {
            logger.log(Level.INFO, "Loading custom entities...");
            try {
                EntityManager.load();
            } catch (MalformedJsonException e) {
                e.printStackTrace();
            }
        }

        // Add currently logged in players to player manager
        for (Player player : Bukkit.getOnlinePlayers())
            PlayerManager.addPlayer(player);

        log(Level.INFO, "Loading warps.");
        WarpManager.load();

    }


    /**
     * On disable
     * Run whenever the server disables this plugin.
     */
    @Override
    public void onDisable () {

        // Empty player manager
        PlayerManager.removeAll();
        WarpManager.removeAll();

        manager.closeConnection();

    }


    /**
     * Create database
     * Creates a new database with values from the config
     * file.
     */
    private void initDatabase () {

        logger.log(Level.INFO, "Initialising database...");


        // Init database manager
        manager = new DatabaseManager(
                config.getString("database", "atomic_core"),
                config.getString("host", DatabaseManager.DEFAULT_HOST),
                config.getInt("port", DatabaseManager.DEFAULT_PORT),
                config.getString("username", DatabaseManager.DEFAULT_USERNAME),
                config.getString("password", DatabaseManager.DEFAULT_PASSWORD)
        );


        try {
            AtomicPlayerDAO.init(manager);
            WarpDAO.init(manager);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        manager.setLogger(logger);
        manager.setMigrationsPath("migrations");

        if (config.getBoolean("auto_migrate", true))
            manager.migrate();

        logger.log(Level.INFO, "Initialised database.");

    }


    /**
     * Register commands
     * This function registers all commands with Bukkit.
     */
    private void registerCommands () {

        logger.log(Level.INFO, "Registering commands...");

        new CommandEntity();
        new CommandFly();
        new CommandGameMode();
        // new CommandHelp();
        new CommandJump();
        new CommandKill();
        new CommandKillAll();
        new CommandLevel();
        new CommandNickname();
        new CommandPing();
        new CommandSpeed();
        new CommandSudo();
        new CommandSuicide();
        new CommandTeleport();
        new CommandTeleportHere();
        new CommandWarp();

        logger.log(Level.INFO, "Commands registered.");

    }


    /**
     * Register events
     * This functions registers all events with Bukkit.
     */
    private void registerEvents () {

        logger.log(Level.INFO, "Registering events...");

        // Put all event handlers here
        new LoginListener();
        new EntityDamageListener();
        new QuitListener();
        new SlimeSplitListener();

        logger.log(Level.INFO, "Events registered.");

    }


    public void log (Level level, String out) {
        logger.log(level, out);
    }


    /*
        Getters and setters from here down.
     */

    public static Main getInstance () {
        return instance;
    }

    public Configuration getBukkitConfig () {
        return config;
    }
}
