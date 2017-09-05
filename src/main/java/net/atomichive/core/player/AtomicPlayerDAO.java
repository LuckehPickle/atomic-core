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
 * A data access object for atomic players.
 */
public class AtomicPlayerDAO {

    // For performance improvements, create prepared statements once
    private static PreparedStatement insertStatement;
    private static PreparedStatement updateStatement;
    private static PreparedStatement findByIdStatement;


    /**
     * Defines tables and columns, and creates prepared
     * statements.
     */
    public static void init (DatabaseManager manager) throws SQLException {

        // Create prepared statements
        insertStatement = new InsertBuilder("players").addColumns(
                "player_id",
                "username",
                "display_name",
                "level",
                "experience",
                "last_seen",
                "login_count",
                "verbosity"
        ).toPrepared(manager.getConnection());

        updateStatement = new UpdateBuilder("players").addColumns(
                "username",
                "display_name",
                "level",
                "experience",
                "last_seen",
                "login_count",
                "verbosity"
        ).where("player_id = ?").toPrepared(manager.getConnection());

        findByIdStatement = new SelectBuilder("players")
                .addColumn("*")
                .where("player_id = ?")
                .toPrepared(manager.getConnection());

    }


    /**
     * Returns a list of all players which match the provided
     * identifier.
     *
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
     * Adds a new atomic player to the database.
     *
     * @param player Atomic player to add.
     */
    public static void insert (AtomicPlayer player) {

        try {

            int n = 1;

            // Set values in prepared statement
            insertStatement.clearParameters();
            insertStatement.setObject(n++, player.getIdentifier());
            insertStatement.setString(n++, player.getUsername());
            insertStatement.setString(n++, player.getDisplayName());
            insertStatement.setInt(n++, player.getLevel());
            insertStatement.setInt(n++, player.getExperience());
            insertStatement.setTimestamp(n++, player.getLastSeen());
            insertStatement.setInt(n++, player.getLoginCount());
            insertStatement.setShort(n, player.getVerbosity());

            // Execute
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Updates an existing atomic player in the database.
     *
     * @param player Player to update
     */
    public static void update (AtomicPlayer player) {

        try {

            mapPlayerToStatement(updateStatement, player);

            // Execute
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Updates a list of players in a batch.
     *
     * @param players List of players to update.
     */
    public static void update (List<AtomicPlayer> players) {

        try {

            for (AtomicPlayer player : players) {
                mapPlayerToStatement(updateStatement, player);
                updateStatement.addBatch();
            }

            updateStatement.executeBatch();
            updateStatement.clearBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Maps SQL results to atomic player objects.
     *
     * @param statement Prepared statement to map player to.
     * @param player    Player to map to statement.
     */
    private static void mapPlayerToStatement (PreparedStatement statement, AtomicPlayer player)
            throws SQLException {

        int n = 1;

        statement.clearParameters();
        statement.setString(n++, player.getUsername());
        statement.setString(n++, player.getDisplayName());
        statement.setInt(n++, player.getLevel());
        statement.setInt(n++, player.getExperience());
        statement.setTimestamp(n++, player.getLastSeen());
        statement.setInt(n++, player.getLoginCount());
        statement.setShort(n++, player.getVerbosity());
        statement.setObject(n, player.getIdentifier());

    }


    /**
     * Takes an SQL result set, and maps the returned columns
     * to Atomic Player objects.
     *
     * @param rs Result set from SQL query.
     * @return List of players from the result set.
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
            player.setDisplayName(rs.getString("display_name"));
            player.setLevel(rs.getInt("level"));
            player.setExperience(rs.getInt("experience"));
            player.setLastSeen(rs.getTimestamp("last_seen"));
            player.setLoginCount(rs.getInt("login_count"));
            player.setVerbosity(rs.getShort("verbosity"));

            // Add player to list
            players.add(player);

        }

        return players;

    }

}
