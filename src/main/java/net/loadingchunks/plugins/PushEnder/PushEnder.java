package net.loadingchunks.plugins.PushEnder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PushEnder extends JavaPlugin {
	
	private PushEnderCommandExecutor commandExecutor;
    
	public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        getLogger().info("Loading " + pdfFile.getName() + " version " + pdfFile.getVersion() + "..." );
		
		getConfig().addDefault("pushover.apptoken", "");
		getConfig().addDefault("pushover.users", null);
		
		getConfig().options().copyDefaults(true);
		
		saveConfig();
		reloadConfig();

		Pushover messageSender = new Pushover(this);
        getServer().getPluginManager().registerEvents(new PushEnderEventListener(this, messageSender), this);
        commandExecutor = new PushEnderCommandExecutor(this, messageSender);
        
		getCommand("pushender").setExecutor(commandExecutor);
		getCommand("callstaff").setExecutor(commandExecutor);
	}

    public void onDisable() {
    	getLogger().info("Stopping...");
    }
    
    public String getAppToken() {
    	return getConfig().getString("pushover.apptoken");
    }
    
    public List<PushUser> getUsers() {
    	ConfigurationSection users = getConfig().getConfigurationSection("pushover.users");
    	
    	List<PushUser> returnArr = new ArrayList<PushUser>();
    	
    	for(String user : users.getKeys(false)) {
    		returnArr.add(new PushUser(user, getConfig().getConfigurationSection("pushover.users." + user)));
    	}
    	
    	return returnArr;
    }
}