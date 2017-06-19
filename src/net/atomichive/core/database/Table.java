package net.atomichive.core.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Database Table
 */
public class Table {


	// Attributes
	private String name;
	private List<Column> columns;


	/**
	 * Table constructor
	 * @param name Name of the table.
	 */
	public Table (String name) {

		// Init
		this.name = name;
		columns = new ArrayList<>();

	}


	/**
	 * Add column
	 * Adds a column to the table.
	 * @param column Column to add to the table.
	 */
	public void addColumn (Column column) {
		columns.add(column);
	}

	/**
	 * Add column
	 * Adds a new column.
	 * @param name Name of column.
	 * @param type Type of column.
	 */
	public void addColumn (String name, Column.type type) {
		addColumn(new Column(name, type));
	}

	/**
	 * Add column
	 * Adds a new column.
 	 * @param name Name of column.
	 * @param type Type of column.
 	 * @param length Optional type length.
	 */
	public void addColumn (String name, Column.type type, Integer length) {
		addColumn(new Column(name, type, length));
	}


	public String getName() {
		return name;
	}

	public List<Column> getColumns() {
		return columns;
	}
}
