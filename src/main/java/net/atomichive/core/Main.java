package net.atomichive.core;

import io.seanbailey.database.DatabaseManager;
import net.atomichive.core.command.*;
import net.atomichive.core.listeners.LoginListener;
import net.atomichive.core.listeners.QuitListener;
import net.atomichive.core.player.AtomicPlayerDAO;
import net.atomichive.core.player.PlayerManager;
import net.atomichive.core.warp.WarpDAO;
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

        // Add currently logged in players to player manager
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerManager.addPlayer(player);
        }

    }


    /**
     * On disable
     * Run whenever the server disables this plugin.
     */
    @Override
    public void onDisable () {

        // Empty player manager
        PlayerManager.removeAll();

        manager.closeConnection();

    }


    /**
     * Create database
     * Creates a new database with values from the config
     * file.
     */
    private void initDatabase () {

        logger.log(Level.INFO, "Setting up database.");


        // Init database manager
        manager = new DatabaseManager(
                (String) config.get("database", "atomic_core"),
                (String) config.get("host", DatabaseManager.DEFAULT_HOST),
                (int)    config.get("port", DatabaseManager.DEFAULT_PORT),
                (String) config.get("username", DatabaseManager.DEFAULT_USERNAME),
                (String) config.get("password", DatabaseManager.DEFAULT_PASSWORD)
        );


        try {
            AtomicPlayerDAO.init(manager);
            WarpDAO.init(manager);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        manager.setLogger(logger);
        manager.setMigrationsPath("migrations");

        if ((boolean) config.get("auto_migrate", true))
            manager.migrate();

    }


    /**
     * Register commands
     * This function registers all commands with Bukkit.
     */
    private void registerCommands () {

        logger.log(Level.INFO, "Registering commands.");

        new CommandFly();
        new CommandGameMode();
        new CommandKill();
        new CommandLevel();
        new CommandPing();
        new CommandSpeed();
        new CommandSuicide();
        new CommandWarp();

    }


    /**
     * Register events
     * This functions registers all events with Bukkit.
     */
    private void registerEvents () {

        logger.log(Level.INFO, "Registering events.");

        // Put all event handlers here
        new LoginListener();
        new QuitListener();

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