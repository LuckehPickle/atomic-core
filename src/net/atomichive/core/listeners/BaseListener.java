package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import org.bukkit.event.Listener;

/**
 * Base Listener
 * A super class to all listeners. Essentially just simplifies
 * the registration process.
 */
class BaseListener implements Listener {


    /**
     * Base listener constructor
     */
    BaseListener () {

        // Register event
        Main.getInstance()
                .getServer()
                .getPluginManager()
                .registerEvents(this, Main.getInstance());

    }

}
