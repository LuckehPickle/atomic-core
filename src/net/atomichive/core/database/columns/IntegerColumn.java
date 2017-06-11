package net.atomichive.core.database.columns;

import net.atomichive.core.database.Column;

/**
 * Integer Column
 */
public class IntegerColumn extends Column {

	public IntegerColumn(String name) {
		this(name, true, false);
	}

	public IntegerColumn(String name, boolean notNull, boolean primaryKey) {
		super(name, "INT", notNull, primaryKey);
	}

}
