package net.atomichive.core.exception;

/**
 * An exception that is thrown from abilities.
 */
public class AbilityException extends Throwable {

    public AbilityException (String message) {
        super(String.format(
                "An exception occurred whilst executing an ability: %s",
                message
        ));
    }

}
