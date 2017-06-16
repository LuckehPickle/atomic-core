package net.atomichive.core.database;

import java.util.List;
import java.util.StringJoiner;

/**
 * Parent builder class
 */
public abstract class Builder {


	/**
	 * Append list
	 * Concatenates all values in a list, so that they can
	 * be inserted into SQL.
	 * Note: In some contexts, it may be better to simply use
	 * String.join(", ", list);
	 * @param list List to concat
	 * @param init A string to insert at the beginning
	 * @param delimiter Symbol used to separate list values.
	 * @return A list formatted for SQL output.
	 */
	public String appendList (List<?> list, String init, String delimiter) {

		if (list.size() == 0)
			return "";

		// Create a new string joiner
		StringJoiner joiner = new StringJoiner(delimiter);

		// Iterate over values, adding them to the final string.
		for (Object string : list) {
			joiner.add(string.toString());
		}

		return (init != null ? init : "") + joiner.toString();

	}


}
