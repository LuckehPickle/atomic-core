package net.atomichive.core.database.columns;

import net.atomichive.core.database.Column;

/**
 * Timestamp column.
 */
public class TimestampColumn extends Column {

	public TimestampColumn(String name, boolean withTimezone) {
		this(name, withTimezone, true, false);
	}

	public TimestampColumn (String name, boolean withTimezone, boolean notNull, boolean primaryKey) {
		super(name, withTimezone ? "timestamp with timezone" : "timestamp", notNull, primaryKey);
	}
}
