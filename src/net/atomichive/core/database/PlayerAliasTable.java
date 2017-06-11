package net.atomichive.core.database;

import net.atomichive.core.database.columns.CharColumn;
import net.atomichive.core.database.columns.VarCharColumn;
import net.atomichive.core.database.exception.NoConnectionException;

import java.sql.Connection;

/**
 * Player alias table
 * Uses UUID's as foreign keys.
 */
public class PlayerAliasTable extends Table {

	private CharColumn UUID;
	private VarCharColumn alias1;
	private VarCharColumn alias2;
	private VarCharColumn alias3;

	public PlayerAliasTable(Connection connection) throws NoConnectionException {

		// Create table
		super(connection, "player_aliases", false);

		// Init
		UUID = new CharColumn("UUID", 36);
		alias1 = new VarCharColumn("alias_1", 16);
		alias2 = new VarCharColumn("alias_2", 16);
		alias3 = new VarCharColumn("alias_3", 16);

		super.setColumns(UUID, alias1, alias2, alias3);

	}
}
