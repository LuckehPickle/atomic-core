package net.atomichive.core.player;

import io.seanbailey.database.DatabaseManager;
import io.seanbailey.database.builders.InsertBuilder;
import io.seanbailey.database.builders.SelectBuilder;
import io.seanbailey.database.builders.UpdateBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Atomic Player DAO
 * A data access object for the AtomicPlayer class.
 * You can use this DAO to insert and update players,
 * as well as find any stored players.
 */
public class AtomicPlayerDAO {


    private static DatabaseManager manager;


    // For performance improvements, create prepared statements once
    private static PreparedStatement insertStatement;
    private static PreparedStatement updateStatement;
    private static PreparedStatement findAllStatement;
    private static PreparedStatement findByIdStatement;


    /**
     * Init
     * Defines tables and columns, and creates prepared statements.
     */
    public static void init (DatabaseManager manager) throws SQLException {

        AtomicPlayerDAO.manager = manager;

        // Create prepared statements
        insertStatement = new InsertBuilder("player")
                .addColumn("player_id")
                .addColumn("username")
                .addColumn("last_seen")
                .addColumns("login_count", "message_count", "warning_count")
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
     * Find all
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

        try {

            // Set values in prepared statement
            findByIdStatement.setObject(1, identifier);

            ResultSet set = findByIdStatement.executeQuery();
            return mapResultsToPlayers(set);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * Insert
     * Adds a new atomic player to the database.
     * @param player Atomic Player to add.
     *               //@return Whether the operation was successfully completed.
     */
    public static void insert (AtomicPlayer player) {

        try {

            int n = 1;

            // Set values in prepared statement
            insertStatement.clearParameters();
            insertStatement.setObject(n++, player.getIdentifier());
            insertStatement.setString(n++, player.getUsername());
            insertStatement.setTimestamp(n++, player.getLastSeen());
            insertStatement.setInt(n++, player.getLoginCount());
            insertStatement.setInt(n++, player.getMessageCount());
            insertStatement.setInt(n++, player.getWarningCount());

            // Execute
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Update
     * Updates an existing atomic player in the database.
     * @param player Player to update
     */
    public static void update (AtomicPlayer player) {

        try {

            int n = 1;

            // Set values in prepared statement
            updateStatement.clearParameters();
            updateStatement.setString(n++, player.getUsername());
            updateStatement.setTimestamp(n++, player.getLastSeen());
            updateStatement.setInt(n++, player.getLoginCount());
            updateStatement.setInt(n++, player.getMessageCount());
            updateStatement.setInt(n++, player.getWarningCount());
            updateStatement.setObject(n++, player.getIdentifier());

            // Execute
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void update (List<AtomicPlayer> players) {

        try {

            updateStatement.clearBatch();

            for (AtomicPlayer player : players) {

                int n = 1;

                // Set values in prepared statement
                updateStatement.clearParameters();
                updateStatement.setString(n++, player.getUsername());
                updateStatement.setTimestamp(n++, player.getLastSeen());
                updateStatement.setInt(n++, player.getLoginCount());
                updateStatement.setInt(n++, player.getMessageCount());
                updateStatement.setInt(n++, player.getWarningCount());
                updateStatement.setObject(n++, player.getIdentifier());

                // Add batch
                updateStatement.addBatch();

            }

            updateStatement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Map Results to Players
     * Takes an SQL result set, and maps the returned columns
     * to Atomic Player objects.
     * @param rs Result set from SQL query.
     * @return List of players from the result set.
     * @throws SQLException If an exception is encountered whilst handling SQL.
     */
    private static List<AtomicPlayer> mapResultsToPlayers (ResultSet rs) throws SQLException {

        // Create a new list
        List<AtomicPlayer> players = new ArrayList<>();

        // Iterate over set results
        while (rs.next()) {

            // Create new atomic player
            AtomicPlayer player = new AtomicPlayer();

            // Update attributes
            player.setIdentifier((UUID) rs.getObject("player_id"));
            player.setUsername(rs.getString("username"));
            player.setLastSeen(rs.getTimestamp("last_seen"));
            player.setLoginCount(rs.getInt("login_count"));
            player.setMessageCount(rs.getInt("message_count"));
            player.setWarningCount(rs.getInt("warning_count"));

            // Add player to list
            players.add(player);

        }

        return players;

    }

}
