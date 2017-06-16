package net.atomichive.core.database;

import java.util.ArrayList;
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
		this.columns = new ArrayList<String>();
		this.wheres  = new ArrayList<String>();

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
	 * To prepared string
	 * Converts the input to a prepared SQL statement.
	 * Setting values is not handled here.
	 * @return Prepared SQL statement.
	 */
	public String toPreparedString () {

		StringBuilder builder = new StringBuilder("UPDATE ");

		// Add table
		builder.append(table);

		builder.append(appendList(columns, " SET ", " = ?, ") + " = ? ");
		builder.append(appendList(wheres, " WHERE ", " AND "));

		return builder.toString();

	}

}
