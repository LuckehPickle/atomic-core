package net.atomichive.core.player;

import io.seanbailey.database.Column;
import io.seanbailey.database.DatabaseManager;
import io.seanbailey.database.Table;
import io.seanbailey.database.builders.CreateTableBuilder;
import io.seanbailey.database.builders.InsertBuilder;
import io.seanbailey.database.builders.SelectBuilder;
import io.seanbailey.database.builders.UpdateBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Atomic Player Data Access Object
 * Used to complete CRUD operations.
 */
public class AtomicPlayerDAO {

	private static DatabaseManager manager;

	// For performance improvements
	private static PreparedStatement insertStatement;
	private static PreparedStatement updateStatement;
	private static PreparedStatement findAllStatement;
	private static PreparedStatement findByIdStatement;


	/**
	 * Init
	 * Defines tables and columns, creates prepared statements.
	 * for performance improvements.
	 */
	public static void init (DatabaseManager manager) throws SQLException {

		// Add player table
		Table player = new Table("player");
		player.addColumn(new Column("player_id", Column.type.CHAR, 36).setPrimaryKey());
		player.addColumn("username",  Column.type.VARCHAR, 16);
		player.addColumn("last_seen", Column.type.TIMESTAMP);
		player.addColumn("login_count",    Column.type.INT);
		player.addColumn("message_count",  Column.type.INT);
		player.addColumn("warning_count",  Column.type.INT);

		// Create tables
		manager.execute(new CreateTableBuilder(player));


		// Create prepared statements
		insertStatement = new InsertBuilder("player")
				.addColumns("player_id", "username", "last_seen", "login_count", "message_count", "warning_count")
				.toPrepared(manager.getConnection());

		updateStatement = new UpdateBuilder("player")
				.addColumns("username", "last_seen", "login_count", "message_count", "warning_count")
				.where("player_id = ?")
				.toPrepared(manager.getConnection());

		findAllStatement = new SelectBuilder("player")
				.addColumn("*")
				.toPrepared(manager.getConnection());

		findByIdStatement = new SelectBuilder("player")
				.addColumn("*")
				.where("player_id = ?")
				.toPrepared(manager.getConnection());

	}



	/**
	 * Find all (READ)
	 * Returns a list of all players stored in the db.
	 * @return All players stored in the db.
	 */
	public static List<AtomicPlayer> findAll () {

		try {
			ResultSet set = findAllStatement.executeQuery();
			return mapResultsToPlayers(set);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}



	/**
	 * Find by identifier (READ)
	 * Returns a list of all players which match
	 * the provided identifier.
	 * @param identifier UUID of player.
	 * @return List of all matching players. Should theoretically
	 * never be more than one player long.
	 */
	public static List<AtomicPlayer> findByIdentifier (UUID identifier) {
		return findByIdentifier(identifier.toString());
	}



	/**
	 * Find by identifier (READ)
	 * Returns a list of all players which match
	 * the provided identifier.
	 * @param identifier UUID of player as a string.
	 * @return List of all matching players. Should theoretically
	 * never be more than one player long.
	 */
	public static List<AtomicPlayer> findByIdentifier (String identifier) {

		try {

			// Set values in prepared statement
			findByIdStatement.setString(1, identifier);

			ResultSet set = findByIdStatement.executeQuery();
			return mapResultsToPlayers(set);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}



	/**
	 * Insert Atomic Player
	 * Adds a new atomic player to the database.
	 * @param player Atomic Player to add.
	 * //@return Whether the operation was successfully completed.
	 */
	public static void insertAtomicPlayer (AtomicPlayer player) {

		try {

			// Set values in prepared statement
			insertStatement.clearParameters();
			insertStatement.setString(1, player.getIdentifier().toString());
			insertStatement.setString(2, player.getUsername());
			insertStatement.setTimestamp(3, player.getLastSeen());
			insertStatement.setInt(4, player.getLoginCount());
			insertStatement.setInt(5, player.getMessageCount());
			insertStatement.setInt(6, player.getWarningCount());

			// Execute
			insertStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}



	/**
	 * Update Atomic Player
	 * Updates an existing atomic player in the database.
	 * @param player Player to update
	 * // @return Whether the statement was completed successfully.
	 */
	public static void updateAtomicPlayer (AtomicPlayer player) {

		try {

			// Set values in prepared statement
			updateStatement.clearParameters();
			updateStatement.setString(1, player.getUsername());
			updateStatement.setTimestamp(2, player.getLastSeen());
			updateStatement.setInt(3, player.getLoginCount());
			updateStatement.setInt(4, player.getMessageCount());
			updateStatement.setInt(5, player.getWarningCount());
			updateStatement.setString(6, player.getIdentifier().toString());

			// Execute
			updateStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}



	/**
	 * Map Results to Players
	 * Takes an SQL result set, and maps the returned columns
	 * to Atomic Player objects.
	 * @param set Result set from SQL query.
	 * @return List of players from the result set.
	 * @throws SQLException If an exception is encountered whilst handling SQL.
	 */
	private static List<AtomicPlayer> mapResultsToPlayers (ResultSet set) throws SQLException {

		// Create a new list
		List<AtomicPlayer> players = new ArrayList<>();

		// Iterate over set results
		while (set.next()) {

			// Create new atomic player
			AtomicPlayer player = new AtomicPlayer(
					UUID.fromString(set.getString("player_id")),
					set.getString("username")
			);

			// Update attributes
			player.setLastSeen(set.getTimestamp("last_seen"));
			player.setLoginCount(set.getInt("login_count"));
			player.setMessageCount(set.getInt("message_count"));
			player.setWarningCount(set.getInt("warning_count"));

			// Add player to list
			players.add(player);

		}

		return players;

	}



	/*
		Getters and setters
	 */

	public static void setManager (DatabaseManager manager) {
		AtomicPlayerDAO.manager = manager;
	}
}
