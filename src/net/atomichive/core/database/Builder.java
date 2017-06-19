package net.atomichive.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

/**
 * Parent builder class
 */
public abstract class Builder {


	/**
	 * Join list
	 * Joins list components together so that they can be used
	 * within an SQL statement.
	 * @param list List of items to join.
	 * @param delimiter Characters to delimit each item.
	 * @return List components joined together.
	 */
	String joinList(List<?> list, String delimiter) {
		return joinList(list, delimiter, null, null);
	}

	/**
	 * Join list
	 * Joins list components together so that they can be used
	 * within an SQL statement.
	 * @param list List of items to join.
	 * @param delimiter Characters to delimit each item.
	 * @param prefix Characters to prefix the final output.
	 * @param suffix Characters to suffix the final output.
	 * @return List components joined together.
	 */
	String joinList(List<?> list, String delimiter, String prefix, String suffix) {

		// Ensure item list is not empty
		if (list.isEmpty()) return "";

		StringJoiner joiner;

		// Create string joiner as necessary.
		if ((prefix != null) && (suffix != null))
			joiner = new StringJoiner(delimiter, prefix, suffix);
		else
			joiner = new StringJoiner(delimiter);

		// Iterate over all objects in the list
		for (Object obj : list)
			joiner.add(obj.toString());

		return joiner.toString();

	}



	/**
	 * To prepared
	 * @param connection SQL connection.
	 * @return A prepared statement with the previous columns.
	 * @throws SQLException if SQL is incorrectly formatted.
	 */
	public PreparedStatement toPrepared (Connection connection) throws SQLException {
		return connection.prepareStatement(toString());
	}

}
