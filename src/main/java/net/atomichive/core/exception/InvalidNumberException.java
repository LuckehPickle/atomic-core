package net.atomichive.core.exception;

/**
 * This exception is thrown whenever the user enters
 * an invalid number.
 */
public class InvalidNumberException extends CommandException {

    public InvalidNumberException () {
        super (
                Reason.INVALID_NUMBER,
                "Please enter a valid number."
        );
    }

}
