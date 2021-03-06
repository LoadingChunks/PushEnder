package net.loadingchunks.plugins.PushEnder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PushEnder extends JavaPlugin {
	
	private PushEnderCommandExecutor commandExecutor;
	public boolean isDebug = false;
	public List<PushUser> userArr = new ArrayList<PushUser>();
    
	public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        getLogger().info("Loading " + pdfFile.getName() + " version " + pdfFile.getVersion() + "..." );
		
		getConfig().addDefault("pushover.apptoken", "");
		getConfig().addDefault("pushover.users", null);
		getConfig().addDefault("pushover.cooldowns.callstaff", 30);
		
		getConfig().options().copyDefaults(true);
		
		saveConfig();
		reloadConfig();

		Pushover messageSender = new Pushover(this);
        getServer().getPluginManager().registerEvents(new PushEnderEventListener(this, messageSender), this);
        commandExecutor = new PushEnderCommandExecutor(this, messageSender);
        
        isDebug = getConfig().getBoolean("debug");
        
		getCommand("pushender").setExecutor(commandExecutor);
		getCommand("callstaff").setExecutor(commandExecutor);
	}

    public void onDisable() {
    	getLogger().info("Stopping...");
    }
    
    public String getAppToken() {
    	return getConfig().getString("pushover.apptoken");
    }
    
    @Override
    public void reloadConfig() {
    	super.reloadConfig();
    	getUsers(true);
    }

    public List<PushUser> getUsers(boolean clearCache) {
    	
    	if(clearCache) {
    		userArr.clear();
        	ConfigurationSection users = getConfig().getConfigurationSection("pushover.users");
	    	for(String user : users.getKeys(false)) {
	    		if(isDebug)
	    			getLogger().info("Processing " + user);
	    		
	    		PushUser tmpuser = new PushUser(user, getConfig().getConfigurationSection("pushover.users." + user));
	    		
	    		if(tmpuser.userToken.length() > 1)
	    			userArr.add(tmpuser);
	    		else {
	    			getLogger().warning("User " + user + " does not have a valid token!");
	    			continue;
	    		}
	
	    		if(isDebug)
	    			getLogger().info("Got " + userArr.get(userArr.size()-1).eventConfig.size() + " events");
	    	}
    	}
    	
    	return userArr;
    }
}