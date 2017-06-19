package net.atomichive.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Insert builder
 * Used to create SQL INSERT queries.
 */
public class InsertBuilder extends Builder {


	// Attributes
	private List<String> columns;
	private String table;


	/**
	 * Insert Builder constructor
	 * Creates a new insert builder instance.
	 */
	public InsertBuilder () {
		this("");
	}


	/**
	 * Insert builder constructor
	 * @param table Table for output purposes.
	 */
	public InsertBuilder (String table) {

		// Init
		this.table   = table;
		this.columns = new ArrayList<>();

	}


	/**
	 * Add column
	 * Adds a column with a generic value.
	 * @param column Name of the column
	 * @return InsertBuilder for chaining.
	 */
	public InsertBuilder addColumn (String column) {
		columns.add(column);
		return this;
	}


	/**
	 * Add columns
	 * Adds multiple columns with values for a prepared statement.
	 * @param columns Varargs of all columns to add.
	 * @return InsertBuilder for chaining.
	 */
	public InsertBuilder addColumns (String... columns) {
		this.columns.addAll(Arrays.asList(columns));
		return this;
	}


	/**
	 * Into
	 * Specifies the table that the data should be inserted into.
	 * @param table Name of the table.
	 * @return InsertBuilder for chaining.
	 */
	public InsertBuilder into (String table) {
		this.table = table;
		return this;
	}


	/**
	 * To string
	 * Converts the input to an SQL statement.
	 * @return Insert SQL statement.
	 */
	@Override
	public String toString () {

		StringJoiner joiner = new StringJoiner(" ","INSERT INTO ", "");

		// Add table
		joiner.add(table);

		StringJoiner columnsJoiner = new StringJoiner(", ", "(", ")");
		StringJoiner valuesJoiner  = new StringJoiner(", ", "VALUES (", ")");

		for (String column : columns) {
			columnsJoiner.add(column);
			valuesJoiner.add("?");
		}

		joiner.add(columnsJoiner.toString());
		joiner.add(valuesJoiner.toString());

		return joiner.toString();

	}
}
