package net.atomichive.core;

import net.atomichive.core.command.CommandPing;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main
 * Main class for the atomic-core plugin.
 */
public class Main extends JavaPlugin {


    /**
     * On enable
     * Run whenever the server enables this plugin.
     */
    @Override
    public void onEnable() {
        registerCommands();
    }


    /**
     * On disable
     * Run whenever the server disables this plugin.
     */
    @Override
    public void onDisable() {

    }



    /**
     * Register commands
     * This function registers all commands, as the name
     * suggests.
     */
    private void registerCommands() {
        this.getCommand("ping").setExecutor(new CommandPing(this.getServer()));
    }

}
