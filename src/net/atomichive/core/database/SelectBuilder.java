package net.atomichive.core.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Select Builder
 * Creates an SQL select query.
 */
public class SelectBuilder extends Builder {


	// Attributes
	private List<String> columns;
	private List<String> tables;
	private List<String> wheres;
	private List<String> joins;


	/**
	 * Select builder constructor
	 */
	public SelectBuilder () {

		// Init
		columns = new ArrayList<String>();
		tables  = new ArrayList<String>();
		wheres  = new ArrayList<String>();
		joins   = new ArrayList<String>();

	}


	public SelectBuilder addColumn (String column) {
		columns.add(column);
		return this;
	}

	public SelectBuilder from (String table) {
		tables.add(table);
		return this;
	}

	public SelectBuilder where (String where) {
		wheres.add(where);
		return this;
	}

	public SelectBuilder join (String join) {
		joins.add(join);
		return this;
	}

	@Override
	public String toString () {

		StringBuilder sql = new StringBuilder("SELECT ");

		if (columns.isEmpty()) {
			sql.append("*");
		} else {
			sql.append(appendList(columns, null, ", "));
		}

		sql.append(appendList(tables, " FROM ", ", "));
		sql.append(appendList(joins, " INNER JOIN ", " INNER JOIN "));
		sql.append(appendList(wheres, " WHERE ", " AND "));

		return sql.toString();

	}

}
