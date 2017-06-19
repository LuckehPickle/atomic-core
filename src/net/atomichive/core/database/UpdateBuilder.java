package net.atomichive.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Update Builder
 */
public class UpdateBuilder extends Builder {


	// Attributes
	private String table;
	private List<String> columns;
	private List<String> wheres;


	/**
	 * Update builder constructor
	 */
	public UpdateBuilder () {
		this("");
	}


	/**
	 * Update builder constructor
	 * @param table Name of the table.
	 */
	public UpdateBuilder (String table) {

		// Init
		this.table = table;
		this.columns = new ArrayList<>();
		this.wheres  = new ArrayList<>();

	}


	/**
	 * Add column
	 * Adds a column to the builder.
	 * @param column Name of the column.
	 * @return UpdateBuilder for chaining.
	 */
	public UpdateBuilder addColumn (String column) {
		columns.add(column);
		return this;
	}


	/**
	 * Add columns
	 * Adds multiple columns with values for a prepared statement.
	 * @param columns Varargs of all columns to add.
	 * @return UpdateBuilder for chaining.
	 */
	public UpdateBuilder addColumns (String... columns) {
		this.columns.addAll(Arrays.asList(columns));
		return this;
	}



	/**
	 * Where
	 * Adds a where expression
	 * @param expression Where expression to add.
	 * @return UpdateBuilder for chaining.
	 */
	public UpdateBuilder where (String expression) {
		wheres.add(expression);
		return this;
	}


	/**
	 * To string
	 * Converts the input to a prepared SQL statement.
	 * Setting values is not handled here.
	 * @return Prepared SQL statement.
	 */
	@Override
	public String toString () {

		StringJoiner joiner = new StringJoiner(" ","UPDATE ", "");

		// Add table
		joiner.add(table);

		StringJoiner columnsJoiner = new StringJoiner(", ", "SET ", "");

		for (String column : columns) {
			columnsJoiner.add(column + " = ?");
		}

		joiner.add(columnsJoiner.toString());
		joiner.add(joinList(wheres, " AND ", " WHERE ", ""));

		System.out.println(joiner.toString());

		return joiner.toString();

	}

}
