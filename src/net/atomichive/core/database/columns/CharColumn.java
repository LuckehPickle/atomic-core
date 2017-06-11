package net.atomichive.core.database.columns;

import net.atomichive.core.database.Column;

/**
 * Character Column
 */
public class CharColumn extends Column {

	public CharColumn(String name, int length) {
		this(name, length, true, false);
	}

	public CharColumn(String name, int length, boolean notNull, boolean primaryKey) {
		super(name, "CHAR(" + length + ")", notNull, primaryKey);
	}

}
