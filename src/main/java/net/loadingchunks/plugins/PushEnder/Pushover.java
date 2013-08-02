package net.loadingchunks.plugins.PushEnder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.bukkit.ChatColor;

public class Pushover {
	
	private String mAppToken;
	private PushEnder plugin;
	private PushoverMessage messenger;
	
	public Pushover(PushEnder host) {
		this.plugin = host;
    	ReloadTokens(host);
	}
	
	public void ReloadTokens(PushEnder host) {
		mAppToken = host.getAppToken();
	}
	
	public void SendMessages(String message) {
    	for (PushUser user : plugin.getUsers(false)) {
    		new PushoverMessage(mAppToken, user.userToken, message)
    		.push();
    	}
	}
	
	public void SendMessages(String title, String message) {
    	for (PushUser user : plugin.getUsers(false)) {
			new PushoverMessage(mAppToken, user.userToken, message)
			.setTitle(title)
			.push();
    	}
	}
	
	public void SendMessages(String title, String message, PushType type) {
    	for (PushUser user : plugin.getUsers(false)) {
    		if(user.eventConfig.containsKey(type.toString()) && user.eventConfig.get(type.toString())) {
    			new PushoverMessage(mAppToken, user.userToken, message)
    			.setTitle(title)
    			.push();
    		}
    	}		
	}
}
