package net.atomichive.core.database;

import net.atomichive.core.database.exception.NoConnectionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringJoiner;

/**
 * Table
 * WARNING: If you rename a column, you will have to manually rename it.
 * TODO Update existing
 */
public class Table {


	private final Connection connection;

	private String name;
	private Column[] columns = null;
	private boolean hasID;


	/**
	 * Table constructor
 	 * @param connection Database connection object.
	 * @param name Name of the table.
	 * @throws NoConnectionException if no database connection has been established.
	 */
	public Table(Connection connection, String name) throws NoConnectionException {
		this(connection, name, true);
	}


	/**
	 * Table constructor
	 * @param connection Database connection object.
	 * @param name Name of the table.
	 * @param hasID Whether an ID column should be automatically added.
	 * @throws NoConnectionException if no database connection has been established.
	 */
	public Table (Connection connection, String name, boolean hasID) throws NoConnectionException {

		// Ensure a connection is open
		if (connection == null) throw new NoConnectionException();

		// Init
		this.connection = connection;
		this.name = name;
		this.hasID = hasID;

	}



	/**
	 * Create
	 * Creates a table if one doesn't exist, and populates
	 * it with the defined columns.
	 */
	public void create() {

		String sqlFormat = "CREATE TABLE IF NOT EXISTS %s (%s)";

		try {

			// Create new statement
			Statement statement = connection.createStatement();

			// Execute statement
			statement.executeUpdate(String.format(
					sqlFormat,
					name.toUpperCase(),
					convertColumnsToSQL()
			));

			statement.close();

		} catch (SQLException e) {
			System.err.println("Table creation failed. Please check output.");
			e.printStackTrace();
		}

	}


	public void insert() {

	}



	/**
	 * Convert columns to SQL
	 * Converts column objects to the attributes part of
	 * an SQL statement.
	 * @return Part of an SQL statement.
	 */
	private String convertColumnsToSQL () {

		StringJoiner joiner = new StringJoiner(",");

		if (hasID) {
			// Every table will automatically be assigned with an ID column by default
			joiner.add("ID INT PRIMARY KEY NOT NULL");
		}

		// Iterate over all added columns
		for (Column column : columns) {
			joiner.add(String.format(
					"%s %s %s%s",
					column.getName().toUpperCase(),
					column.getType().toUpperCase(),
					column.isPrimaryKey() ? "PRIMARY KEY " : "",
					column.isNotNull() ? "NOT NULL" : ""
			));
		}

		return joiner.toString();

	}



	/*
		Getters and setters
	 */

	public String getName() {
		return name;
	}

	public Column[] getColumns() {
		return columns;
	}

	public void setColumns(Column... columns) {
		this.columns = columns;
		create();
	}

}
