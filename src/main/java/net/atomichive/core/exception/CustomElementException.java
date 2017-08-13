package net.atomichive.core.exception;

/**
 * Custom Element Exception
 */
public class CustomElementException extends Throwable {

    public CustomElementException (String format, Object... items) {
        super(String.format(format, items));
    }
}
