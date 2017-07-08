package net.atomichive.core.exception;

/**
 * Missing Permission Exception
 * An exception which occurs when a player does not have
 * appropriate permissions to perform an action.
 */
public class MissingPermissionException extends Throwable {

    public MissingPermissionException (String message) {
        super(message);
    }

}
