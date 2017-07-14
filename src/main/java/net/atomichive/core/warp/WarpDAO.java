package net.atomichive.core.warp;

import io.seanbailey.database.DatabaseManager;
import io.seanbailey.database.builders.DeleteBuilder;
import io.seanbailey.database.builders.InsertBuilder;
import io.seanbailey.database.builders.SelectBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Warp DAO
 */
public class WarpDAO {


    private static PreparedStatement insertStatement;
    private static PreparedStatement deleteStatement;

    private static DatabaseManager manager;


    public static void init (DatabaseManager manager) throws SQLException {

        WarpDAO.manager = manager;

        // Create prepared statements
        insertStatement = new InsertBuilder("warps").addColumns(
                "warp_id",
                "name",
                "message",
                "world_id",
                "x", "y", "z",
                "pitch", "yaw"
        ).toPrepared(manager.getConnection());

        deleteStatement = new DeleteBuilder("warps")
                .where("warp_id = ?")
                .toPrepared(manager.getConnection());

    }


    /**
     * Get all
     * @return all warps.
     */
    public static List<Warp> getAll () {

        try {
            // Create a new statement
            Statement statement = manager.getConnection().createStatement();

            // Execute query
            ResultSet rs = statement.executeQuery(
                    new SelectBuilder("warps")
                            .addColumn("*")
                            .toString()
            );

            // Map results to warp objects
            // Create a new list
            List<Warp> warps = new ArrayList<>();

            // Iterate over set results
            while (rs.next()) {

                // Create new atomic player
                Warp warp = new Warp();

                // Update attributes
                warp.setIdentifier((UUID) rs.getObject("warp_id"));
                warp.setName(rs.getString("name"));
                warp.setMessage(rs.getString("message"));
                warp.setWorld((UUID) rs.getObject("world_id"));
                warp.setX(rs.getInt("x"));
                warp.setY(rs.getInt("y"));
                warp.setZ(rs.getInt("z"));
                warp.setPitch(rs.getFloat("pitch"));
                warp.setYaw(rs.getFloat("yaw"));

                // Add player to list
                warps.add(warp);

            }

            return warps;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }



    /**
     * Insert
     * Add a warp to the db
     * @param warp Warp to insert.
     */
    public static void insert (Warp warp) {

        try {

            int n = 1;

            // Set values in prepared statement
            insertStatement.clearParameters();
            insertStatement.setObject(n++, warp.getIdentifier());
            insertStatement.setString(n++, warp.getName());
            insertStatement.setString(n++, warp.getMessage());
            insertStatement.setObject(n++, warp.getWorld());
            insertStatement.setInt(n++, warp.getX());
            insertStatement.setInt(n++, warp.getY());
            insertStatement.setInt(n++, warp.getZ());
            insertStatement.setFloat(n++, warp.getPitch());
            insertStatement.setFloat(n, warp.getYaw());

            // Execute
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    /**
     * Drop
     * @param warp Warp object to drop.
     */
    public static void drop (Warp warp) {
        drop(warp.getIdentifier());
    }



    /**
     * Drop
     * Drops a warp from the db.
     * @param identifier Warp identifier.
     */
    public static void drop (UUID identifier) {

        try {

            // Set params
            deleteStatement.clearParameters();
            deleteStatement.setObject(1, identifier);

            // Execute
            deleteStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
