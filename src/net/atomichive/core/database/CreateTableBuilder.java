package net.atomichive.core.database;

import java.util.StringJoiner;

/**
 * Create Table Builder
 * Builds an SQL string which creates new table.
 */
public class CreateTableBuilder extends Builder {

	// Attributes
	private Table table;
	private boolean ifNotExists = true;


	/**
	 * Create table builder constructor
	 * @param table Table to create.
	 */
	public CreateTableBuilder (Table table) {
		this.table = table;
	}


	public void setIfNotExists() {
		setIfNotExists(true);
	}


	public void setIfNotExists (boolean ifNotExists) {
		this.ifNotExists = ifNotExists;
	}


	@Override
	public String toString () {

		StringBuilder builder = new StringBuilder("CREATE TABLE ");
		StringJoiner joiner = new StringJoiner(", ", "(", ")");

		if (ifNotExists) {
			builder.append("IF NOT EXISTS ");
		}

		builder.append(table.getName());

		for (Column column : table.getColumns()) {
			joiner.add(column.getDefinition());
		}

		builder.append(joiner.toString());

		return builder.toString();

	}


}
