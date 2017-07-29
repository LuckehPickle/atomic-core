package net.atomichive.core.exception;

/**
 * Ability Exception
 * An exception related to entity abilities.
 */
public class AbilityException extends Throwable {

    public AbilityException (String message) {
        super(String.format(
                Reason.ERROR.getFormat(),
                message
        ));
    }

}
