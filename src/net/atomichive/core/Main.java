package net.atomichive.core;

import net.atomichive.core.command.CommandFly;
import net.atomichive.core.command.CommandGameMode;
import net.atomichive.core.database.*;
import net.atomichive.core.database.exception.NoConnectionException;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import net.atomichive.core.command.CommandPing;

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
	private PlayerTable playerTable;
	private PlayerAliasTable playerAliasTable;

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

		// Open connection with database
		createDatabase();

		registerCommands();

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

		logger.log(Level.INFO, "Opening connection to database.");

		// TODO Parse config values and ensure they are the right type

		database = new DatabaseManager(
				(String)  config.get("database", "atomic_core"),
				(String)  config.get("host", DatabaseManager.DEFAULT_HOST),
				(Integer) config.get("port", DatabaseManager.DEFAULT_PORT),
				(String)  config.get("username", "postgres"),
				(String)  config.get("password", "")
		);

		try {

			// Create tables
			playerTable      = new PlayerTable(database.getConnection());
			playerAliasTable = new PlayerAliasTable(database.getConnection());

		} catch (NoConnectionException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Register commands
	 * This function registers all commands, as the name
	 * suggests.
	 */
	private void registerCommands() {
		new CommandFly();
		new CommandGameMode();
		new CommandPing();
	}


	public static Main getInstance() {
		return instance;
	}
}
