package net.atomichive.core.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Expiring Value
 * A value which expires to null if not updated
 * for the designated time period.
 */
public class ExpiringValue<P> {


    private P value;
    private int seconds;
    private Timer timer;
    private ExpiryTimer expiryTimer;


    /**
     * Expiring Value
     * A value which expires to null after the given
     * number of seconds elapses.
     *
     * @param seconds Time before the value expires.
     */
    public ExpiringValue (int seconds) {
        this(seconds, null);
    }

    /**
     * Expiring Value
     * A value which expires to null after the given
     * number of seconds elapses.
     *
     * @param seconds Time before the value expires.
     * @param value   Initial value.
     */
    public ExpiringValue (int seconds, P value) {
        this.seconds = seconds;
        timer = new Timer();

        if (value != null)
            update(value);
    }


    /**
     * Update
     * Updates the internal value and resets the
     * expiration timer.
     *
     * @param value New value.
     */
    public void update (P value) {

        // Cancel expiry timer
        if (expiryTimer != null) {
            expiryTimer.cancel();
            expiryTimer = null;
        }

        this.value = value;

        // Schedule new expiry
        expiryTimer = new ExpiryTimer(this);
        timer.schedule(expiryTimer, seconds * 1000);

    }


    /**
     * Expire
     * Expires the current value to null.
     */
    public void expire () {

        // Cancel expiry timer
        if (expiryTimer != null) {
            expiryTimer.cancel();
            expiryTimer = null;
        }

        this.value = null;

    }


    public boolean is (P value) {
        return this.value != null && this.value.equals(value);
    }

    public P get () {
        return this.value;
    }


    /**
     * Expiry Timer
     * Extends upon the default timer task to allow
     * modification of arbitrary instances.
     */
    class ExpiryTimer extends TimerTask {

        private ExpiringValue<P> expiringValue;

        ExpiryTimer (ExpiringValue<P> expiringValue) {
            this.expiringValue = expiringValue;
        }

        @Override
        public void run () {
            expiringValue.expire();
        }

    }

}