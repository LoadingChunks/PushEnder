package net.loadingchunks.plugins.PushEnder;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class PushEnder extends JavaPlugin {

	private FileConfiguration config;
    
	public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        getLogger().info("Loading " + pdfFile.getName() + " version " + pdfFile.getVersion() + "..." );
		getLogger().info("Loading config..");
		try {
			config = getConfig();
			File config_file = new File("plugins" + File.separator + "<variable>" + File.separator + "config.yml");
			config_file.mkdir();
			if (!config.contains("pushover.apptoken")) {
			    config.set("pushover.apptoken", "");
			}
			if (!config.contains("pushover.usertokens")) {
			    config.set("pushover.usertokens", new String[0]);
			}
			saveConfig();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		Pushover messageSender = new Pushover(this);
        getServer().getPluginManager().registerEvents(new EventListener(messageSender), this);
	}

    public void onDisable() {
    	getLogger().info("Stopping...");
    }
    
    public String getAppToken() {
    	return config.getString("pushover.apptoken");
    }
    
    public List<String> getUserTokens() {
    	return config.getStringList("pushover.usertokens");
    }
}