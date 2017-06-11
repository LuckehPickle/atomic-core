package net.atomichive.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

		openConnection();

	}



	/**
	 * Open connection
	 * Opens a new connection with the PostgreSQL database.
	 */
	private void openConnection() {

		// Attempt to open a new connection
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(getUrl(), username, password);
		} catch (ClassNotFoundException e) {
			System.err.println("PostgreSQL JDBC driver not found.");
		} catch (SQLException e) {
			System.err.println("Database connection failed. Please check output.");
			e.printStackTrace();
		}

	}



	/**
	 * Close connection
	 * Closes the database connection.
	 */
	public void closeConnection() {

		if (connection == null) return;

		try {
			connection.close();
		} catch (SQLException e) {
			System.err.println("Failed to close database. Please check output.");
			e.printStackTrace();
		} finally {
			connection = null;
		}
	}



	/**
	 * Get URL
	 * Formats and returns a URL based off of the provided
	 * username and password.
	 * @return Formatted URL.
	 */
	private String getUrl() {
		return String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
	}



	/*
		Getters and setters from here down.
	 */

	public Connection getConnection() {
		return connection;
	}

}
