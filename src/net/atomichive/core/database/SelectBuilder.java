package net.atomichive.core.database;

import java.util.ArrayList;
import java.util.Arrays;
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
		columns = new ArrayList<>();
		tables  = new ArrayList<>();
		wheres  = new ArrayList<>();
		joins   = new ArrayList<>();

	}


	/**
	 * Select builder constructor.
	 * @param tables The names of all tables to be added by default.
	 */
	public SelectBuilder (String... tables) {
		this();
		this.tables.addAll(Arrays.asList(tables));
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
			sql.append(joinList(columns, ", "));
		}

		sql.append(joinList(tables, ", ", " FROM ", ""));
		sql.append(joinList(joins, " INNER JOIN ", " INNER JOIN ", ""));
		sql.append(joinList(wheres, " AND ", " WHERE ", ""));

		System.out.println(sql.toString());

		return sql.toString();

	}

}
