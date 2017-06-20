package net.atomichive.core;

import io.seanbailey.database.DatabaseManager;
import net.atomichive.core.command.CommandFly;
import net.atomichive.core.command.CommandGameMode;
import net.atomichive.core.listeners.PlayerListener;
import net.atomichive.core.player.AtomicPlayerDAO;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import net.atomichive.core.command.CommandPing;

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
	private DatabaseManager database;


	/**
	 * On enable
	 * Run whenever the server enables this plugin.
	 */
	@Override
	public void onEnable() {

		// Init
		instance = this;
		logger = getLogger();

		// Load configuration file
		saveDefaultConfig();
		config = this.getConfig();

		createDatabase();
		registerCommands();
		registerEvents();

	}


	/**
	 * On disable
	 * Run whenever the server disables this plugin.
	 */
	@Override
	public void onDisable() {
		database.closeConnection();
	}



	/**
	 * Create database
	 * Creates a new database with values from the config
	 * file.
	 */
	private void createDatabase() {

		logger.log(Level.INFO, "Setting up database.");

		// TODO Parse config values and ensure they are the right type

		database = new DatabaseManager(
				(String)  config.get("database", "atomic_core"),
				(String)  config.get("host", DatabaseManager.DEFAULT_HOST),
				(Integer) config.get("port", DatabaseManager.DEFAULT_PORT),
				(String)  config.get("username", "postgres"),
				(String)  config.get("password", "")
		);

		database.setLogger(logger);

		try {

			// Initialise data access objects here
			AtomicPlayerDAO.init(database);

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An SQL exception occurred whilst " +
					"initialising data access objects. Please check output.");
			e.printStackTrace();
		}

	}



	/**
	 * Register commands
	 * This function registers all commands with Bukkit.
	 */
	private void registerCommands() {

		logger.log(Level.INFO, "Registering commands.");

		new CommandFly();
		new CommandGameMode();
		new CommandPing();

	}



	/**
	 * Register events
	 * This functions registers all events with Bukkit.
	 */
	private void registerEvents() {

		logger.log(Level.INFO, "Registering events.");

		// Put all event handlers here
		new PlayerListener();

	}

	public static Main getInstance() {
		return instance;
	}

	public DatabaseManager getDatabaseManager() {
		return database;
	}
}
