package net.atomichive.core.database.columns;

import net.atomichive.core.database.Column;

/**
 * Variable character column
 */
public class VarCharColumn extends Column {

	public VarCharColumn(String name, int length) {
		this(name, length, true, false);
	}

	public VarCharColumn (String name, int length, boolean notNull, boolean primaryKey) {
		super(name, "varchar(" + length + ")", notNull, primaryKey);
	}

}
