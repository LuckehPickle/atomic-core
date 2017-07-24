package net.atomichive.core.exception;

/**
 * Entity Exception
 */
public class AtomicEntityException extends Throwable {

    public AtomicEntityException (String message) {
        this(Reason.ENTITY_ERROR, message);
    }

    public AtomicEntityException (Reason reason, String message) {
        super(String.format(
                reason.getFormat(),
                message
        ));
    }
}
