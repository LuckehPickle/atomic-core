package net.atomichive.core.exception;

/**
 * Throw one of these bad boys if you need the user to see
 * an error message on their command.
 */
public class CommandException extends Throwable {

    public CommandException (String message) {
        this(Reason.GENERIC_ERROR, message);
    }

    public CommandException (Reason reason, String message) {
        super(String.format(
                reason.getFormat(),
                message
        ));
    }
}
