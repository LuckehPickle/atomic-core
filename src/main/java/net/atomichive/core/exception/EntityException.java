package net.atomichive.core.exception;

/**
 * Entity Exception
 */
public class EntityException extends Throwable {

    public EntityException (String message) {
        this(Reason.ENTITY_ERROR, message);
    }

    public EntityException (Reason reason, String message) {
        super(String.format(
                reason.getFormat(),
                message
        ));
    }
}
