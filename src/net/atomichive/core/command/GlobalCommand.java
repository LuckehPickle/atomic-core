package net.atomichive.core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.atomichive.core.Main;


public abstract class GlobalCommand implements CommandExecutor {
	
	public abstract void run(CommandSender sender, String[] args);
	
	public abstract String getName();
	
	public abstract int getSteps();
	
	public abstract String getDescription();
	
	public abstract boolean requiresPlayer();
	
	public abstract String getPermission();
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)  {
		if(!cmd.getName().equalsIgnoreCase(getName())) return false;
		
		if(!sender.hasPermission(getPermission())) return false;
		
		if(requiresPlayer()) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
				return false;
			}
		}
		
		if(args.length < getSteps()) {
			sender.sendMessage(ChatColor.RED + "You need to enter atleast " + ChatColor.YELLOW + getSteps() + ChatColor.RED + " arguments for this command.");
			sender.sendMessage(ChatColor.AQUA + getDescription());
			return false;
		}
		
		run(sender, args);		
		return true;
	}
	
	public void registerCommand() {
		Main.getInstance().getServer().getPluginCommand(getName()).setExecutor(this);
	}
}
