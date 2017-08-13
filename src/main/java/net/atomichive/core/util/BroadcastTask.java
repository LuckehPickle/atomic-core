package net.atomichive.core.util;

import net.atomichive.core.Main;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Broadcast Task
 * A task which broadcasts a message to the server.
 * This task can be scheduled, and run later.
 */
public final class BroadcastTask extends BukkitRunnable {

    private final String message;

    /**
     * Broadcast Task
     *
     * @param message Message to be broadcast
     */
    public BroadcastTask (String message) {
        this.message = message;
    }

    @Override
    public void run () {
        Main.getInstance().getServer().broadcastMessage(message);
    }

}
