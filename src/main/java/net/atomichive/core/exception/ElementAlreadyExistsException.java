package net.atomichive.core.exception;

/**
 * Element Already Exists Exception
 * Thrown whenever a defined element already exists.
 */
public class ElementAlreadyExistsException extends Throwable {

    private String name;

    public ElementAlreadyExistsException (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }
}
