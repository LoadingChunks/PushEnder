package net.loadingchunks.plugins.PushEnder;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PushEnderCommandExecutor implements CommandExecutor {
	
	private PushEnder plugin;
	private Pushover messageSender;
	
	public PushEnderCommandExecutor(PushEnder plugin, Pushover messageSender) {
		this.plugin = plugin;
		this.messageSender = messageSender;
	}

	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("pushender")) {
			if(args.length == 1 && args[0].equalsIgnoreCase("reload") && (sender.isOp() || sender.hasPermission("pushender.reload"))) {
				plugin.reloadConfig();
				messageSender.ReloadTokens(plugin);
				sender.sendMessage(ChatColor.GREEN + "Reloaded Config!");
				return true;
			}
		}
		
		if(command.getName().equalsIgnoreCase("callstaff")) {
			if(sender.hasPermission("pushender.callstaff") && sender instanceof Player) {
				if(args.length > 0) {
					messageSender.SendMessages(((Player)sender).getDisplayName() + " needs help!", StringUtils.join(args), PushType.CALL_STAFF);
				}
			} else {
				if(!(sender instanceof Player))
					sender.sendMessage("You must be a player to use this command!");
				else if(!sender.hasPermission("pushender.callstaff"))
					sender.sendMessage(ChatColor.GREEN + "You do not have permission to use this command.");
			}
		}
		return false;
	}

}
