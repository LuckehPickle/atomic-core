package net.atomichive.core.database;

import net.atomichive.core.database.columns.CharColumn;
import net.atomichive.core.database.columns.IntegerColumn;
import net.atomichive.core.database.columns.TimestampColumn;
import net.atomichive.core.database.columns.VarCharColumn;
import net.atomichive.core.database.exception.NoConnectionException;

import java.sql.Connection;

/**
 * Player table
 */
public class PlayerTable extends Table {

	// Columns
	private CharColumn UUID;
	private VarCharColumn username;
	private IntegerColumn loginCount;
	private TimestampColumn lastSeen;
	private IntegerColumn messageCount;

	public PlayerTable(Connection connection) throws NoConnectionException {

		// Create table
		super(connection, "player", false);

		// Init
		UUID         = new CharColumn("UUID", 36, true, true);
		username     = new VarCharColumn("username", 16);
		loginCount   = new IntegerColumn("login_count");
		lastSeen     = new TimestampColumn("last_seen", false);
		messageCount = new IntegerColumn("message_count");

		// Set columns
		super.setColumns(UUID, username, loginCount, lastSeen, messageCount);

	}

}
