package net.atomichive.core.database;

/**
 * Column
 * Represents a column within a table.
 */
public class Column {

	private String name;
	private String type;
	private boolean notNull;
	private boolean primaryKey;



	/**
	 * Column constructor
 	 * @param name Column name/title
	 * @param type Type (e.g. INT, VARCHAR etc.)
	 */
	public Column(String name, String type) {
		this(name, type, true, false);
	}



	/**
	 * Column constructor
	 * @param name Column name/title
	 * @param type Type (e.g. INT, VARCHAR etc.)
	 * @param notNull Whether the value in this column can be null.
	 */
	public Column(String name, String type, boolean notNull) {
		this(name, type, notNull, false);
	}


	/**
	 * Column constructor
	 * @param name Column name/title
	 * @param type Type (e.g. INT, VARCHAR etc.)
	 * @param notNull Whether the value in this column can be null.
	 * @param primaryKey Whether this column represents a primary key.
	 */
	public Column (String name, String type, boolean notNull, boolean primaryKey) {

		// Init
		this.name = name;
		this.type = type;
		this.notNull = notNull;
		this.primaryKey = primaryKey;

	}


	/*
		Getters and setters from here down
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
}
