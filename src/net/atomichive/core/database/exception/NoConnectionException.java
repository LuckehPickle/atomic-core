package net.atomichive.core.database.exception;

/**
 * No Connection Exception
 * Thrown when the user attempts to perform an operation
 * with the database, but there is currently no open
 * connection.
 */
public class NoConnectionException extends Throwable {

	public NoConnectionException() {
		super();
	}

}
