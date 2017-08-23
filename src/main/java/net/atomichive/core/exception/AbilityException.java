package net.atomichive.core.exception;

/**
 * An exception related to entity abilities.
 */
public class AbilityException extends Throwable {

    public AbilityException (String message) {
        super(String.format(
                Reason.GENERIC_ERROR.getFormat(),
                message
        ));
    }

}
