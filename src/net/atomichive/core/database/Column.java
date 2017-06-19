package net.atomichive.core.database;

import java.sql.Connection;
import java.util.StringJoiner;

/**
 * Database Column
 */
public class Column {


	/**
	 * Column type enum
	 * Represents a possible type of enum.
	 */
	public enum type {
		VARCHAR,
		CHAR,
		TIMESTAMP,
		INT
	}


	// Attributes
	private String name;
	private type type;
	private Integer length; // Can be null
	private boolean primaryKey;
	private boolean notNull;


	/**
	 * Column constructor
	 * @param name Name of the column.
	 * @param type SQL data type of the column.
	 */
	public Column (String name, Column.type type) {
		this(name, type, null);
	}

	/**
	 * Column constructor
	 * @param name Name of the column.
	 * @param type SQL data type of the column.
	 * @param length Data type length, if applicable, else null.
	 */
	public Column (String name, Column.type type, Integer length) {

		// Init
		this.name = name;
		this.type = type;
		this.length = length;
		this.primaryKey = false;
		this.notNull = true;

	}


	public Column setPrimaryKey() {
		return setPrimaryKey(true);
	}

	public Column setPrimaryKey (boolean primaryKey) {
		this.primaryKey = primaryKey;
		return this;
	}

	public Column setNotNull (boolean notNull) {
		this.notNull = notNull;
		return this;
	}


	/**
	 * Get definition
	 * Constructs a string which can be used to define this
	 * column.
	 * @return Column definition.
	 */
	public String getDefinition () {

		StringJoiner joiner = new StringJoiner(" ");
		joiner.add(name);

		if (length != null) {
			joiner.add(String.format("%s(%d)", type.name(), length));
		} else {
			joiner.add(type.name());
		}

		if (primaryKey) joiner.add("PRIMARY KEY");
		if (notNull)    joiner.add("NOT NULL");

		return joiner.toString();

	}



}
