package net.atomichive.core.player;

import net.atomichive.core.database.DatabaseManager;
import net.atomichive.core.database.InsertBuilder;
import net.atomichive.core.database.SelectBuilder;
import net.atomichive.core.database.UpdateBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Atomic Player Data Access Object
 * Used to complete CRUD operations. However, players
 * should never be deleted from the database.
 */
public class AtomicPlayerDAO {


	private static DatabaseManager manager;


	/**
	 * Find all (READ)
	 * Returns a list of all players.
	 * @return All players stored in the db.
	 */
	public static List<AtomicPlayer> findAll () {

		String sql = new SelectBuilder()
				.addColumn("*")
				.from("player")
				.toString();

		Statement statement;
		List<AtomicPlayer> players = null;

		try {
			// Create statement
			statement = manager.getConnection().createStatement();
			players = mapResultsToPlayers(statement.executeQuery(sql));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return players;

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

		String sql = new SelectBuilder()
				.addColumn("*")
				.from("player")
				.where("UUID = ?")
				.toString();

		PreparedStatement statement;
		List<AtomicPlayer> players = null;

		try {
			// Create statement
			statement = manager.getConnection().prepareStatement(sql);

			statement.setString(1, identifier);

			players = mapResultsToPlayers(statement.executeQuery());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return players;

	}



	/**
	 * Insert Atomic Player
	 * Adds a new atomic player to the database.
	 * @param player Atomic Player to add.
	 * @return Whether the operation was successfully completed.
	 */
	public static boolean insertAtomicPlayer (AtomicPlayer player) {

		String sql = new InsertBuilder()
				.into("player")
				.addColumn("UUID")
				.addColumn("username")
				.addColumn("logins")
				.addColumn("last_seen")
				.addColumn("messages")
				.addColumn("warnings")
				.toPreparedString();

		PreparedStatement statement;

		try {

			// Create a new prepared statement
			statement = manager.getConnection().prepareStatement(sql);

			// Populate the statement with values
			statement.setString(1, player.getIdentifier().toString());
			statement.setString(2, player.getUsername());
			statement.setInt(3, player.getLogins());
			statement.setTimestamp(4, player.getLastSeen());
			statement.setInt(5, player.getMessages());
			statement.setInt(6, player.getWarnings());

			// Execute
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}



	/**
	 * Update Atomic Player
	 * Updates an existing atomic player in the database.
	 * @param player Player to update
	 * @return Whether the statement was completed successfully.
	 */
	public static boolean updateAtomicPlayer (AtomicPlayer player) {

		String sql = new UpdateBuilder("player")
				.addColumn("username")
				.addColumn("logins")
				.addColumn("last_seen")
				.addColumn("messages")
				.addColumn("warnings")
				.where("UUID = ?")
				.toPreparedString();

		PreparedStatement statement;

		try {

			// Create a new prepared statement
			statement = manager.getConnection().prepareStatement(sql);

			// Populate the statement with values
			statement.setString(1, player.getUsername());
			statement.setInt(2, player.getLogins());
			statement.setTimestamp(3, player.getLastSeen());
			statement.setInt(4, player.getMessages());
			statement.setInt(5, player.getWarnings());
			statement.setString(6, player.getIdentifier().toString());

			// Execute
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}


	/*
		We shouldn't ever need to delete a player.
		public boolean deleteAtomicPlayer (AtomicPlayer player) {

		}
	 */


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
		List<AtomicPlayer> players = new ArrayList<AtomicPlayer>();

		// Iterate over set results
		while (set.next()) {

			// Create new atomic player
			AtomicPlayer player = new AtomicPlayer(
					UUID.fromString(set.getString("uuid")),
					set.getString("username")
			);

			// Update attributes
			player.setLogins(set.getInt("logins"));
			player.setLastSeen(set.getTimestamp("last_seen"));
			player.setMessages(set.getInt("messages"));
			player.setWarnings(set.getInt("warnings"));

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
