package net.atomichive.core.exception;

/**
 * Permission Exception
 * An exception which occurs when a player does not have
 * appropriate permissions to perform an action.
 */
public class PermissionException extends Throwable {

    public PermissionException () {
        super("You do not have permission to perform this command.");
    }

    public PermissionException (String message) {
        super(message);
    }

}
