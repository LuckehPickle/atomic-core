package net.atomichive.core.util.tasks;

import net.atomichive.core.Main;
import net.atomichive.core.item.MenuItemStack;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A task that can be run whenever a player logs in.
 */
public class LoginTask extends BukkitRunnable {

    private Player player;


    /**
     * Constructor
     *
     * @param player Bukkit player that logged in.
     */
    public LoginTask (Player player) {
        this.player = player;
    }


    /**
     * Execute the login task.
     */
    @Override
    public void run () {
        player.getInventory().setItem(8, new MenuItemStack());
        player.setScoreboard(Main.getInstance().getScoreboard());
    }

}
