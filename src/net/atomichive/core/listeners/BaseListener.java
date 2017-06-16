package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import org.bukkit.event.Listener;

/**
 * Base Listener
 * Essentially just handles event registration.
 */
class BaseListener implements Listener {

	public BaseListener() {
		Main.getInstance()
				.getServer()
				.getPluginManager()
				.registerEvents(this, Main.getInstance());
	}

}
