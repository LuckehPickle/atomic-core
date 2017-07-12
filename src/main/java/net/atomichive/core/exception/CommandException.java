package net.atomichive.core.exception;

/**
 * Command Exception
 * Throw one of these bad boys if you need the user to see
 * an error message on their command.
 */
public class CommandException extends Throwable {

    public CommandException (String message) {
        super(message);
    }

}
