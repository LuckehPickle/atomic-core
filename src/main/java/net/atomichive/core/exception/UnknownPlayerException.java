package net.atomichive.core.exception;

/**
 * Throw this exception when you couldn't find
 * a target player.
 */
public class UnknownPlayerException extends CommandException {

    public UnknownPlayerException (String player) {
        super (
                Reason.UNKNOWN_PLAYER,
                String.format("The player '%s' could not be found.", player)
        );
    }
}
