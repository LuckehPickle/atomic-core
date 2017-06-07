package net.atomichive.core;

import org.bukkit.plugin.java.JavaPlugin;

import net.atomichive.core.command.CommandPing;


/**
 * Main
 * Main class for the atomic-core plugin.
 */
public class Main extends JavaPlugin {


	private static Main instance;

	/**
	 * On enable
	 * Run whenever the server enables this plugin.
	 */
	@Override
	public void onEnable() {

		instance = this;

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
		new CommandPing();
	}


	public static Main getInstance() {
		return instance;
	}
}
