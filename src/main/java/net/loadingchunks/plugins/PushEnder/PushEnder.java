package net.loadingchunks.plugins.PushEnder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PushEnder extends JavaPlugin {
    
	public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        getLogger().info("Loading " + pdfFile.getName() + " version " + pdfFile.getVersion() + "..." );
		getLogger().info("Loading config..");
		
		getConfig().addDefault("pushover.apptoken", "");
		getConfig().addDefault("pushover.usertokens", new ArrayList<String>());
		getConfig().options().copyDefaults(true);
		
		saveConfig();

		Pushover messageSender = new Pushover(this);
        getServer().getPluginManager().registerEvents(new EventListener(messageSender), this);
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