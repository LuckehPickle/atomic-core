package net.atomichive.core.database;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Insert builder
 * Used to create SQL INSERT queries.
 */
public class InsertBuilder extends Builder {


	// Parallel lists
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
		this.table = table;
		this.columns = new ArrayList<String>();
	}


	/**
	 * Add column
	 * Adds a column.
	 * @param column Name of the column
	 * @return InsertBuilder for chainging.
	 */
	public InsertBuilder addColumn (String column) {
		columns.add(column);
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
	 * To prepared string
	 * Converts the input to a prepared SQL statement.
	 * Setting values is not handled here.
	 * @return Prepared SQL statement.
	 */
	public String toPreparedString () {

		StringBuilder builder = new StringBuilder("INSERT INTO ");

		// Add table
		builder.append(table);

		StringJoiner columnsJoiner = new StringJoiner(",");
		StringJoiner valuesJoiner  = new StringJoiner(",");

		for (String column : columns) {
			columnsJoiner.add(column);
			valuesJoiner.add("?");
		}

		builder.append(" (" + columnsJoiner.toString() + ")");
		builder.append(" VALUES (" + valuesJoiner.toString() + ")");

		return builder.toString();

	}

}
