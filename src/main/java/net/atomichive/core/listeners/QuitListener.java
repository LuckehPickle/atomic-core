package net.atomichive.core.listeners;

import net.atomichive.core.Main;
import net.atomichive.core.player.AtomicPlayer;
import net.atomichive.core.player.AtomicPlayerDAO;
import net.atomichive.core.player.PlayerManager;
import net.atomichive.core.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Quit Listener
 * Handles player quit events.
 */
public final class QuitListener extends BaseListener implements Listener {


    /**
     * On quit event
     * An event which occurs whenever a player logs out.
     *
     * @param event Player quit event object.
     */
    @EventHandler
    public void onQuit (PlayerQuitEvent event) {

        PlayerManager manager = Main.getInstance().getPlayerManager();

        // Get player
        AtomicPlayer player = manager.get(event.getPlayer());
        manager.removePlayer(player);

        // Update last seen value
        player.setLastSeen(Util.getCurrentTimestamp());

        AtomicPlayerDAO.update(player);

    }

}
