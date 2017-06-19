package net.atomichive.core.database;

import net.atomichive.core.Main;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database Manager
 * Opens a connection with a PostgreSQL database.
 * Allows users to work with tables, rows and columns without
 * writing any actual SQL.
 */
public class DatabaseManager {


	// Attributes
	private final String database;
	private final String host;
	private final int port;
	private final String username;
	private final String password;

	private Connection connection = null;
	private Logger logger;

	public final static String DEFAULT_HOST = "localhost";
	public final static int DEFAULT_PORT = 5432;


	/**
	 * Database Manager constructor
	 * @param database Name of the database.
	 */
	public DatabaseManager(String database) {
		this(database, DEFAULT_HOST, DEFAULT_PORT, "postgres", "");
	}


	/**
	 * Database Manager constructor
	 * @param database Name of the database.
	 * @param username Username used to access database
	 * @param password Password used to access database
	 */
	public DatabaseManager(String database, String username, String password) {
		this(database, DEFAULT_HOST, DEFAULT_PORT, username, password);
	}



	/**
	 * Database Manager constructor
	 * @param database Name of the database.
	 * @param host     Host name of database server
	 * @param port     Port of database server
	 * @param username Username used to access database
	 * @param password Password used to access database
	 */
	public DatabaseManager(String database, String host, int port, String username, String password) {

		// Init
		this.database = database;
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;

		this.logger = Main.getInstance().getLogger();

		// Open connection
		openConnection();

	}


	/**
	 * Open connection
	 * Opens a new connection with the SQL server.
	 */
	private void openConnection() {

		// Attempt to open a new connection
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(getUrl(), username, password);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "PostgreSQL JDBC driver not found.");
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Database connection failed. Please check output.");
			e.printStackTrace();
		}

	}


	/**
	 * Close connection
	 * Closes the database connection.
	 */
	public void closeConnection() {

		if (isClosed()) return;

		try {
			connection.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Failed to close JDBC connection.");
			e.printStackTrace();
		}

	}


	/**
	 * Get URL
	 * Formats and returns a URL based off of the provided
	 * username and password.
	 * @return Formatted URL.
	 */
	private String getUrl () {
		return String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
	}


	/**
	 * Get connection
	 * Returns the current database connection. Opens a new one
	 * if necessary.
	 * @return Connection to SQL server.
	 */
	public Connection getConnection() {

		// Open a new connection if one has not already been established
		if (isClosed()) openConnection();

		return connection;

	}


	/**
	 * Is closed
	 * Determines whether the connection is currently closed.
	 * @return Whether the jdbc connection is closed.
	 */
	public boolean isClosed () {
		try {
			return connection.isClosed();
		} catch (SQLException e) {
			System.err.println("Failed to determine whether JDBC connection was closed.");
			e.printStackTrace();
			return true;
		}
	}


	/**
	 * Execute
	 * Executes an SQL statement. Does not return any results.
	 * @param sql SQL statement to execute.
	 */
	public void execute (String sql) {

		// Attempt to execute SQL statement
		try (Statement statement = connection.createStatement()) {
			statement.execute(sql);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An SQL exception occured. Please review output.");
			e.printStackTrace();
		}

	}

}
