package net.loadingchunks.plugins.PushEnder;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PushEnderCommandExecutor implements CommandExecutor {
	
	private PushEnder plugin;
	
	public PushEnderCommandExecutor(PushEnder plugin) {
		this.plugin = plugin;
	}

	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("pushender")) {
			if(args.length == 1 && args[0].equalsIgnoreCase("reload") && (sender.isOp() || sender.hasPermission("pushender.reload"))) {
				plugin.reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "Reloaded Config!");
				return true;
			}
		}
		return false;
	}

}
