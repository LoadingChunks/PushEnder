package net.loadingchunks.plugins.PushEnder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PushEnder extends JavaPlugin {
	
	private final PushEnderCommandExecutor commandExecutor = new PushEnderCommandExecutor(this);
    
	public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        getLogger().info("Loading " + pdfFile.getName() + " version " + pdfFile.getVersion() + "..." );
		
		getConfig().addDefault("pushover.apptoken", "");
		getConfig().addDefault("pushover.usertokens", new ArrayList<String>());
		getConfig().addDefault("events.witherKill", true);
		getConfig().addDefault("events.dragonKill", true);
		getConfig().addDefault("events.join", false);
		getConfig().addDefault("events.quit", false);
		getConfig().addDefault("events.ban", true);
		getConfig().addDefault("events.kick", true);
		getConfig().addDefault("events.callAdmin", true); // Coming Soon :V
		
		getConfig().options().copyDefaults(true);
		
		saveConfig();

		Pushover messageSender = new Pushover(this);
        getServer().getPluginManager().registerEvents(new PushEnderEventListener(this, messageSender), this);
        
		getCommand("pushender").setExecutor(commandExecutor);
	}

    public void onDisable() {
    	getLogger().info("Stopping...");
    }
    
    public String getAppToken() {
    	return getConfig().getString("pushover.apptoken");
    }
    
    public List<String> getUserTokens() {
    	return getConfig().getStringList("pushover.usertokens");
    }
}